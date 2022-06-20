package be.uantwerpen.fti.ei;
import be.uantwerpen.fti.ei.game.AFact;
import be.uantwerpen.fti.ei.game.Game;
import be.uantwerpen.fti.ei.visuals.J2D.J2DFact;
import be.uantwerpen.fti.ei.visuals.city.CityFact;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
	    AFact jf = new CityFact();
        Game game = new Game(jf);
        game.run();
    }
}
