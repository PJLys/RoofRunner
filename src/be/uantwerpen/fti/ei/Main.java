package be.uantwerpen.fti.ei;
import be.uantwerpen.fti.ei.game.Game;

public class Main {

    public static void main(String[] args) {
	    GraphicsCTX gctx = new GraphicsCTX();
        Game game = new Game(gctx);
        game.run();
    }
}
