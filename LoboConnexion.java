import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class LoboConnexion extends Thread{
	private Socket socket;
	private LoboServeur serveur;
	private PrintWriter out;
	private BufferedReader in;
	private boolean open;
	private boolean estMaitre;
	//TODO: enregistrer cartes pour verifier

	public LoboConnexion(LoboServeur loboServeur, Socket s){
		super("Connexion");
		serveur = loboServeur;
		socket = s;
		estMaitre = false;
		start();
	}
	public void run(){ //Script principal
		String s;
		open = true;
		try {
			out = new PrintWriter(socket.getOutputStream(),true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out.println("Connexion établie, entrez votre nom svp");
			setName(in.readLine()); // Nom du joueur
			System.out.println(getName() + " est connecte");
			while (open){
				s = recevoir();
				//System.out.println(s);
				if (s.equalsIgnoreCase("Commencer") || s.equalsIgnoreCase("Recommencer")){
					serveur.commencer();
				}
				if (s.equalsIgnoreCase("Piocher")){
					distribueCarte(serveur.getPioche().piocher());
				}
				if (s.toLowerCase().startsWith("jouer")){
					jouer();
				}
				if (s.equalsIgnoreCase("Quitter")){
					serveur.deconnecte(this);
					//A modifier si les cartes doivent etre défaussées
				}
				if (s.equalsIgnoreCase("Quitter serveur")){
					if (estMaitre) serveur.quitter();
				}
				//System.out.println(s);
			}

		} catch (IOException e) {
			e.printStackTrace();
			quitter();
		}
		//interrupt();
	}

	public void quitter() {
		try {
			open = false;
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void jouer (){
		if (!serveur.peutJouer(this)) return;
		int val = 0;
		try {
			val = in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//System.out.print(Integer.parseInt(s.substring(5)));
		if (!serveur.doitJouer2fois() && val == Carte.fois2) return;
		Carte c = new Carte(val);
		serveur.jouer(c);
		//serveur.getDefausse().ajouterCarte(c);
		distribueCarte(serveur.getPioche().piocher());
	}

	public void distribueCarte (Carte carte) {
		out.println(carte.getValeur()); //Input à gérer coté client
	}

	public String recevoir() throws IOException {
		String s = in.readLine();
		System.out.println(getName() + ": " + s);
		return s;
	}

	public void envoyer(String message) {
		out.println(message);
	}

	public void estMaitre() {
		estMaitre = true;
	}
}
