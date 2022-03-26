package be.uantwerpen.fti.ei.game;

/**
 * An entity consists of x coördinates and y coördinates
 * these coördinates are relative positions of non movable elements.
 */

public abstract class AEntity {
    private int[] x;
    private int[] y;
    public abstract void vis();
}
