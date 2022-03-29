package be.uantwerpen.fti.ei.game;

import be.uantwerpen.fti.ei.GraphicsCTX;

import java.util.LinkedList;
import java.util.Map;

/**
 * The ACollectable class contains the position of all collectable elements.
 */
public abstract class ACollectable extends AEntity{

    @Override
    public abstract void vis();

    public abstract Map<Integer, LinkedList<Integer>> getPos();
}
