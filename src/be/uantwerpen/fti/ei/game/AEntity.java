package be.uantwerpen.fti.ei.game;

import be.uantwerpen.fti.ei.GraphicsCTX;

/**
 * An entity consists of x coördinates and y coördinates
 * these coördinates are relative positions of non movable elements.
 */

public abstract class AEntity {
    private GraphicsCTX gctx;
    private int[] x;
    private int[] y;
    public abstract void vis();
}
