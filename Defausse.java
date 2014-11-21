package Lobo77;

import java.util.ArrayList;

public class Defausse {

    private ArrayList<Carte> defausse;

    public Defausse()
    {
         defausse = new ArrayList<Carte>();
    }

    public void ajouterCarte(Carte c) {
        defausse.add(c);
    }

    public int getTaille()
    {
        return defausse.size();
    }

    public ArrayList<Carte> recuperer() throws NullPointerException
    {
        ArrayList<Carte> recupere = new ArrayList<Carte>(this.defausse);
        this.defausse.clear();

        return recupere;
    }

    public void print() {

        int taille = this.getTaille();
        System.out.print(String.valueOf(taille));
    }


}
