package Lobo77;

/**
 * Created by Laurine on 09/11/2014.
 */
public class Carte {
    public int valeur;

    public Carte(int val)
    {
        valeur = val;
    }
    public int getValeur() {
        return valeur;
    }

    public void print() {
        System.out.println("[" + valeur + "]");
    }
}
