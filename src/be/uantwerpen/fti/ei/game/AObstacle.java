package be.uantwerpen.fti.ei.game;

import be.uantwerpen.fti.ei.GraphicsCTX;

public abstract class AObstacle extends AEntity {
    private GraphicsCTX gctx;
    private int[] x;
    private int[] y;
    public abstract void vis();

}
