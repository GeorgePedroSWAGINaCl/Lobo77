package Lobo77;

import java.util.*;


public class Joueur {
    public String nom;
    public int nbJeton;
    public ArrayList<Carte> main;

    public Joueur(String nom) {
        this.nom = nom;
        main = new ArrayList<Carte>();
        nbJeton = 5;
    }

    public ArrayList<Carte> getMain() {
        return main;
    }

    public Carte defausserCarte(int index)
    {
        return main.get(index);
    }

    public int getTailleMain() {
        return main.size();
    }

    public int getNbJeton() {
        return nbJeton;
    }

    public String getNom() {
        return nom;
    }

    public void print() {
        System.out.println("nom : " + nom + ", jetons : " + nbJeton);
    }
}
