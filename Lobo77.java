package Lobo77;


import java.util.Scanner;

public class Lobo77 {
    Pioche pioche;
    Defausse defausse;
    Joueur joueur;
    public static void main(String[] args) {
        Lobo77 l = new Lobo77();
    }
    /****************************
     * Constructeur**************
     ***************************/
    public Lobo77()
    {
        pioche = new Pioche();
        defausse = new Defausse();
        Scanner s = new Scanner(System.in);
        String input = s.nextLine();
        joueur = new Joueur(input);
        for (int i = 0; i < 5; i++) {
           joueur.piocheCarte(pioche.piocher());
        }
        joueur.afficheCartes();
        do {
            input = s.nextLine();
        } while (input!="" && Integer.parseInt(input)>=1 && Integer.parseInt(input)<=5);
        joueur.defausserCarte(Integer.parseInt(input));
    }

    /****************************
     ******** Methodes************
     ***************************/
    public void defausserCarte()
    {
        //this.main.
    }
    public boolean piocheEstVide()
    {
        return pioche.estVide();
    }


}
