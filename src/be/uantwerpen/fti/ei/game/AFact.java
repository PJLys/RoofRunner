package be.uantwerpen.fti.ei.game;

import be.uantwerpen.fti.ei.GraphicsCTX;
import be.uantwerpen.fti.ei.Input;

public abstract class AFact {
    public abstract APlayer createPlayer(int x, int y, int lives, GraphicsCTX gctx);
    public abstract AEnemy createEnemy(int[] x, int[] y, GraphicsCTX gctx);
    public abstract AObstacle createObstacle(int[] x, int[] y, GraphicsCTX gctx);
    public abstract ACollectable createCollectable(int[] x, int[] y, GraphicsCTX gctx);
    public abstract Input createInput();
}
