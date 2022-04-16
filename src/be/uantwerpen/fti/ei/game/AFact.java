package be.uantwerpen.fti.ei.game;

import be.uantwerpen.fti.ei.GraphicsCTX;
import be.uantwerpen.fti.ei.Input;

import java.util.LinkedList;
import java.util.Map;

public abstract class AFact {
    public abstract APlayer createPlayer(int x, int y, int lives);
    public abstract APlayer createPlayer(float x, float y, int lives);
    public abstract AEnemy createEnemy(int[] x, int[] y);
    public abstract AObstacle createObstacle(Map<Integer, int[]> pos);
    public abstract ACollectable createCollectable(Map<Integer, LinkedList<Integer>> pos);
    public abstract Input createInput();
    public abstract GraphicsCTX getGctx();
}
