import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class LoboServeur{
	private boolean partiecommencee;
	private Pioche pioche;
	private Defausse defausse;
	private List<LoboConnexion> connexions;
	private int joueur;
	private int sens;
	private boolean joue2fois;
	private int score;

	public static void main(String[] args) {
		new LoboServeur();
	}

	public LoboServeur(){
		pioche = new Pioche();
		defausse = new Defausse();
		connexions = new ArrayList<LoboConnexion>();
		partiecommencee = false;
		joueur = 0;
		sens = 1;
		joue2fois = false;
		score = 0;
		int port = 9001;
		try {
			ServerSocket server = new ServerSocket(port,8); // 8 connexions max pour 8 joueurs
			System.out.println("Serveur ouvert sur " + InetAddress.getLocalHost().getHostAddress() + ":" + port);
			while (true){//TODO: Changer la condition plus tard
				/*while (partiecommencee) try { //Refuser les connexions en cours de jeu
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}*/
				Socket socket = server.accept();
				LoboConnexion connexion = new LoboConnexion(this,socket); //Chaque connexion créé un nouveau thread LoboConnexion
				connexions.add(connexion);
				//connexion.start();
				if (connexions.size() == 1){
					connexion.estMaitre();
					//connexion.envoyer("Vous etes le maitre, " +
						//"vous pouvez fermer le serveur avec \"Quitter serveur\"");
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void commencer() {
		pioche = new Pioche();
		defausse = new Defausse();
		joueur = new Random().nextInt(connexions.size()); // entre 0 et le nombre de joueurs
		sens = 1;
		partiecommencee = true;
		broadcast("Commencer");
		for (LoboConnexion c : connexions) {
			for (int i = 0; i < 5; i++) {
				c.distribueCarte(pioche.piocher());
			}
		}

		getJoueurActuel().envoyer("Jouer");
	}

	public Pioche getPioche() {
		return pioche;
	}

	public void deconnecte(LoboConnexion connexion) {
		connexion.quitter();
		connexions.remove(connexion);
	}

	public boolean peutJouer(LoboConnexion l){
		if (!connexions.contains(l)) return false;
		int index = connexions.indexOf(l);
		return (index == joueur);
	}

	public void quitter(){
		System.out.println("Fermeture des connextions");
		for (LoboConnexion l : connexions) {
			deconnecte(l);
		}
		//System.out.println("Fermeture de l'application");
	}

	public void jouer(Carte carte) {

		if (!joue2fois) joueur += sens;
		if (joueur == connexions.size()) joueur = 0;
		if (joueur == -1) joueur = connexions.size()-1;
		defausse.ajouterCarte(carte);
		joue2fois = false;
		if (carte.getValeur() == Carte.changement){
			sens = sens*-1;
		} else
		if (carte.getValeur() == Carte.fois2){
			joue2fois = true;
		} else {
			score += carte.getValeur();
			verifiescore();
		}
		broadcast("" + score);
		getJoueurActuel().envoyer("Jouer");
		verifiescore();
	}

	private void verifiescore() {
		if (score > 77){
			score = 0;
			commencer();
		}
	}

	private void broadcast(String message) {
		for (LoboConnexion l : connexions){
			l.envoyer(message);
		}
	}

	public boolean doitJouer2fois() {
		return joue2fois;
	}

	public LoboConnexion getJoueurActuel(){
		return connexions.get(joueur);
	}
}
