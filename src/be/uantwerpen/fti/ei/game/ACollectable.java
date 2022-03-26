package be.uantwerpen.fti.ei.game;

/**
 * The ACollectable class contains the position of all collectable elements.
 */
public abstract class ACollectable extends AEntity{
    private int[] x;
    private int[] y;
    public abstract void vis();
}
