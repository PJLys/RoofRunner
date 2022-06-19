package be.uantwerpen.fti.ei.game;
import be.uantwerpen.fti.ei.components.Cmovement;
import be.uantwerpen.fti.ei.visuals.GraphicsCTX;

import java.util.AbstractMap;
import java.util.LinkedList;
import java.util.Map;

public abstract class AEnemy {

    /**
    AEnemy is a linked list of all the enemy positions types and movement distances
     */
    public abstract void vis(int displacement);
    public abstract void update();
    public abstract GraphicsCTX getGctx();
    public abstract LinkedList<AbstractMap.SimpleEntry<Map.Entry<Cmovement, Character>, Map.Entry<Integer,Integer>>> getEnemyList();
}
