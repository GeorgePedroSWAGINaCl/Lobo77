package Lobo77;

/**
 * Created by Laurine on 28/11/2014.
 */
public class CarteX2 extends Carte {
    public CarteX2() {
        super(0);
    }   
public void print() {
        System.out.println("[X2]");
    }

    /* règle : Si un joueur joue la carte «2x», le compte
    reste inchangé et le joueur suivant doit poser
    successivement 2 cartes, annoncer les 2
    totaux successifs, puis piocher 2 fois.*/
}
