package Lobo77;

import java.util.ArrayList;
import java.util.Collections;




public class Pioche {
    private ArrayList<Carte> pioche;



    public Pioche(){
        pioche = new ArrayList <Carte> ();
        initialiserPioche();
    }

    private void initialiserPioche() {
        //faire toutes les cartes
        /*
        4 * 0
        4 * -10
        4 * double
        4 * changement
        11 à 66
        8 * 10
        3 * 2 à 9
        1 * 76
        */
        for (int i=0; i <4;i++ ) {
            pioche.add(new CarteZero());
            pioche.add(new CarteMoins10());
            pioche.add(new CarteX2());
            pioche.add(new CarteChgmtSens());
        }
        for (int i = 0; i < 4; i++){
        	pioche.add(new Carte(0));
        	pioche.add(new Carte(-10));
        	pioche.add(new Carte(10));
        	pioche.add(new Carte(10));
    	}
    	pioche.add(new Carte(11));
    	pioche.add(new Carte(22));
    	pioche.add(new Carte(33));
    	pioche.add(new Carte(44));
    	pioche.add(new Carte(55));
    	pioche.add(new Carte(66));
    	for(int i = 0; i < 3; i++){
    		for(int n = 2; n <= 9; n++){
    			pioche.add(new Carte(n));
    		}
    	}
    	pioche.add(new Carte(76));
        melanger();
    }

    public boolean estVide(){
        return pioche.isEmpty();

    }

    public Carte piocher(){
        return pioche.remove(0);
    }

    public int getTaille(){
        return pioche.size();
    }

    public void print(){
        System.out.println();
    }

    public  ArrayList<Carte> getListCartes() {
        return pioche;
    }

    public void setListCartes(ArrayList<Carte> listCartes) {
        this.pioche = listCartes;
    }

    public void melanger(){
    	Collections.shuffle(pioche);
    }
}
