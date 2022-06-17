package be.uantwerpen.fti.ei.game;

import be.uantwerpen.fti.ei.visuals.GraphicsCTX;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

public abstract class AFact {

    public abstract APlayer createPlayer(float x, float y, int lives);
    public abstract AEnemy createEnemy(ArrayList<Integer> x, ArrayList<Integer> y, ArrayList<Integer> d, ArrayList<Character> type, float framerate);
    public abstract AObstacle createObstacle(Map<Integer, ArrayList<Integer>> pos);
    public abstract ACollectable createCollectable(Map<Integer, LinkedList<Integer>> pos);
    public abstract Input createInput();
    public abstract GraphicsCTX getGctx();
    public abstract CollisionDetection createCD(AFact af, APlayer player, ACollectable collectable, AObstacle obstacle,AEnemy enemy);
}
