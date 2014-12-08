import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by jon on 07/12/14.
 */
public class LoboConnexion extends Thread{
	private Socket socket;
	private LoboServeur serveur;
	private PrintWriter out;
	private BufferedReader in;
	private boolean open;

	public LoboConnexion(LoboServeur loboServeur, Socket s){
		super("Connexion");
		serveur = loboServeur;
		socket = s;
		start();
	}
	public void run(){ //Script principal
		String s;
		open = true;
		try {
			out = new PrintWriter(socket.getOutputStream(),true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out.println("Connexion etablie");
			while (open){
				s = in.readLine();
				System.out.println(s);
				if (s.equalsIgnoreCase("Commencer")){
					serveur.commencer();
				}
				if (s.equalsIgnoreCase("Piocher")){
					distribueCarte(serveur.getPioche().piocher());
				}
				if (s.toLowerCase().startsWith("jouer")){
					int val = in.read();
					Carte c = new Carte(val);
					serveur.getDefausse().ajouterCarte(c);
					distribueCarte(serveur.getPioche().piocher());
				}
				if (s.equalsIgnoreCase("Quitter")){
					serveur.deconnecte(this);
					//A modifier si les cartes doivent etre défaussées
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

	public void distribueCarte (Carte carte) {
		out.print(carte); //Input à gérer coté client
	}
}
