package be.uantwerpen.fti.ei.game;

import be.uantwerpen.fti.ei.GraphicsCTX;

/**
 * The ACollectable class contains the position of all collectable elements.
 */
public abstract class ACollectable extends AEntity{
    private GraphicsCTX gctx;
    private int[] x;
    private int[] y;
    public abstract void vis();
}
