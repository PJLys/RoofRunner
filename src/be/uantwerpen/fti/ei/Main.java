package be.uantwerpen.fti.ei;
import be.uantwerpen.fti.ei.game.Game;
import be.uantwerpen.fti.ei.visuals.J2D.J2DFact;

public class Main {

    public static void main(String[] args) {
	    J2DFact jf = new J2DFact();
        Game game = new Game(jf);
        game.run();
    }
}
