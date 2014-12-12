import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class LoboClient {
	Socket socket;
	//BufferedReader in;
	PrintWriter out;
	LoboReceveur in;
	Scanner scanner;

	public static void main(String[] args) {// A enlever lors de l'intégration
		new LoboClient();
	}

	public LoboClient() {
		try {
			socket = new Socket(InetAddress.getLocalHost(), 9001); //Changer si serveur <> client
			//socket.setSoTimeout(1000);
			//in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			in = new LoboReceveur(socket);
			//Scanner inputscan = new Scanner(socket.getInputStream());
			out = new PrintWriter(socket.getOutputStream(),true);
			System.out.println(recevoir()); //Message de connexion "connexion etablie"
			String s;
			String r;
			scanner = new Scanner(System.in);
			s = scanner.nextLine();
			if (s.equals("")) s = "Anonymous";
			out.println(s); //Envoi du nom
			s = ""; //Au cas ou le nom est "Quitter"
			//r = "Jouer";
			while (!s.equalsIgnoreCase("Quitter")){
				//while (!r.equalsIgnoreCase("Jouer")){
				s = scanner.nextLine();
				envoyer(s);

				//}
				/*s = scanner.nextLine();
				envoyer(s);*/
			}
			quitter();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void quitter(){
		try {
			if (!socket.isClosed()) {
				out.println("Quitter");
				in.exit();
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void envoyer(String s){
		//out.println(s);
		if (s.equalsIgnoreCase("Quitter"))//Non sensible a la casse
			quitter();
		if (s.equalsIgnoreCase("Commencer") || s.equalsIgnoreCase("Recommencer")){
			out.println("Commencer");
			//s = "Commencer";
			//recevoirCarte(5);
		}
		if (s.equalsIgnoreCase("Jouer")){
			out.println(s);
			jouerCarte(scanner.nextInt());
		}
		//out.println(s);

	}

	public String recevoir() throws IOException {
		return in.readLine();
	}

	public Carte recevoirCarte(){ //TODO: A traiter
		try {
			return new Carte(in.read());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}


	public void jouerCarte(String s){ //Utiliser l'autre méthode au possible
		envoyer("Jouer");
		envoyer(s);
		recevoirCarte();
	}

	public void jouerCarte(int i){
		jouerCarte("" + i);
	}
}










class LoboReceveur extends Thread{
	Socket socket;
	BufferedReader in;
	String lastmessage;
	boolean exec;
	int[] cartes;

	public LoboReceveur(Socket s){
		socket = s;
		exec = true;
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		start();
	}

	public void run(){
		while (exec){
			try {
				lastmessage = in.readLine();
				if (in != null) System.out.println(lastmessage);
				if (lastmessage == "Jouer"){
					wait();
				}
				if (lastmessage == "Commencer"){
					cartes = new int[5];
					for (int i = 0; i < 5; i++) {
						cartes[i] = read();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public String readLine(){
		return lastmessage;
	}

	public int read() throws IOException {
		return in.read();
	}

	public void exit(){
		try {
			in.close();
			exec = false;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int[] getCartes(){
		return cartes;
	}
}
