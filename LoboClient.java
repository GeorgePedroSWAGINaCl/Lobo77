import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
/**
 * Created by jon on 08/12/14.
 */
public class LoboClient {
	Socket socket;
	BufferedReader in;
	PrintWriter out;

	public static void main(String[] args) {
		new LoboClient();
	}
	public LoboClient() {
		try {
			socket = new Socket(InetAddress.getLocalHost(), 9001);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(),true);
			System.out.println(recevoir()); //Message de connexion
			String s = "";
			Scanner scanner = new Scanner(System.in);
			while (!s.equalsIgnoreCase("Quitter")){
				s = scanner.nextLine();
				envoyer(s);
			}
			quitter();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void quitter(){
		out.println("Quitter");
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void envoyer(String s){
		if(s.equalsIgnoreCase("Quitter"))//Non sensible a la casse
			quitter();
		else
			out.println(s);
	}

	public String recevoir() throws IOException {
		return in.readLine();
	}

	public Carte recevoirCarte(){
		try {
			return new Carte(in.read());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void recevoirCarte(int n){
		while (n>0){
			recevoirCarte(); //TODO: A traiter
			n--;
		}
	}
	public void jouerCarte(String s){
		envoyer("Jouer");
		envoyer(s);
	}

	public void jouerCarte(int i){
		jouerCarte("" + i);
	}
}
