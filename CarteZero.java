package Lobo77;

/**
 * Created by Laurine on 28/11/2014.
 */
public class CarteZero extends Carte {
    public CarteZero() {
        super(0);
    }
    /* règle : La carte «0» doit plutôt être conservée pour
    les situations critiques : le dernier total est
    répété et le problème est transmis au joueur
    suivant !*/
}
