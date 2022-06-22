package be.uantwerpen.fti.ei.game;
import be.uantwerpen.fti.ei.components.Cmovement;

import java.util.AbstractMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Blueprint for enemy objects with following functionalities: position update and visualization
 * Enemies will be stored in a Linked List
 */
public abstract class AEnemy {

    public abstract void vis(int displacement);
    public abstract void update();
    public abstract LinkedList<AbstractMap.SimpleEntry<Map.Entry<Cmovement, Character>, Map.Entry<Integer,Integer>>> getEnemyList();
}
