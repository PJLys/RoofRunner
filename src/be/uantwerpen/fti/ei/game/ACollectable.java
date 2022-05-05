package be.uantwerpen.fti.ei.game;

import java.util.LinkedList;
import java.util.Map;

/**
 * The ACollectable class contains the position of all collectable elements.
 */
public abstract class ACollectable extends AEntity{

    @Override
    public abstract void vis(int displacement);

    public abstract Map<Integer, LinkedList<Integer>> getPos();
}
