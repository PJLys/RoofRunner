package be.uantwerpen.fti.ei.game;
import be.uantwerpen.fti.ei.visuals.GraphicsCTX;

public abstract class AEnemy {

    /**
    AEnemy is a linked list of all the enemy positions types and movement distances
     */
    public abstract void vis(int displacement);
    public abstract void update();
    public abstract GraphicsCTX getGctx();

}
