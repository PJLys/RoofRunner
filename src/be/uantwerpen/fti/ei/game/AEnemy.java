package be.uantwerpen.fti.ei.game;
import be.uantwerpen.fti.ei.components.Cmovement;
import be.uantwerpen.fti.ei.visuals.GraphicsCTX;

import java.util.LinkedList;
import java.util.Map;

public abstract class AEnemy {

    /**
    AEnemy is a linked list of all the enemy positions types and movement distances
     */
    public abstract void vis();
    public abstract void update();
    public abstract GraphicsCTX getGctx();

}
