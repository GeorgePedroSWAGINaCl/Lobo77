import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class LoboServeur{
	private Pioche pioche;
	private Defausse defausse;
	private List<LoboConnexion> connexions;

	public static void main(String[] args) {
		new LoboServeur();
	}

	public LoboServeur(){
		pioche = new Pioche();
		defausse = new Defausse();
		connexions = new ArrayList<LoboConnexion>();
		try {
			ServerSocket server = new ServerSocket(9001,8); // 8 connexions max pour 8 joueurs
			while (connexions.size() < 8){//TODO: Changer la condition plus tard
				Socket socket = server.accept();
				LoboConnexion connexion = new LoboConnexion(this,socket);
				connexions.add(connexion);
				//connexion.start(); // Chaque connexion créé un nouveau thread LoboConnexion
			}



		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void commencer() {
		pioche = new Pioche();
		defausse = new Defausse();
		for (LoboConnexion c : connexions) {
			for (int i = 0; i < 5; i++) {
				c.distribueCarte(pioche.piocher());
			}
		}
	}

	public Pioche getPioche() {
		return pioche;
	}

	public Defausse getDefausse() {
		return defausse;
	}

	public void deconnecte(LoboConnexion connexion) {
		connexion.quitter();
		connexions.remove(connexion);
	}
}
