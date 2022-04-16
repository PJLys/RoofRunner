package be.uantwerpen.fti.ei.visuals;

import be.uantwerpen.fti.ei.GraphicsCTX;
import be.uantwerpen.fti.ei.Input;
import be.uantwerpen.fti.ei.components.Cmovement;
import be.uantwerpen.fti.ei.game.*;

import java.util.HashMap;
import java.util.Map;
import java.util.LinkedList;
import java.util.Map.Entry;

public class J2DFact extends AFact {
    private final GraphicsCTX gctx = new GraphicsCTX();

    public GraphicsCTX getGctx() {
        return gctx;
    }
    public J2DFact(){}

    @Override
    public APlayer createPlayer(int x, int y, int lives) {
        float realx = (float) x* gctx.getSize();
        float realy = (float) y* gctx.getSize();
        APlayer player = new J2DPlayer(gctx);
        player.setC_mov(new Cmovement(realx, realy));
        return player;
    }
    public APlayer createPlayer(float x, float y, int lives) {
        APlayer player = new J2DPlayer(gctx);
        player.setC_mov(new Cmovement(x, y));
        return player;
    }
    public AEnemy createEnemy(int[] x, int[] y) {
        AEnemy enemy = new J2DEnemy();
        enemy.setC_move(createMovables(x,y, gctx.getSize()));
        return enemy;
    }
    public AObstacle createObstacle(Map<Integer, int[]> pos) {
        return new J2DObstacle(pos, gctx);
    }
    public ACollectable createCollectable(Map<Integer, LinkedList<Integer>> pos) {
        return new J2DCollectable(pos, gctx);
    }
    public Input createInput() {
        return new Input(gctx);
    }

    private static LinkedList<Cmovement> createMovables(int[] x, int[] y, int resolution) {
        float[] realx = multiplytofloat(x,resolution);
        float[] realy = multiplytofloat(y, resolution);
        LinkedList<Cmovement> cmovements = new LinkedList<Cmovement>();
        for (int i=0; i< realx.length; i++)
            cmovements.set(i, new Cmovement(realx[i], realy[i]));
        return cmovements;
    }
    private static float[] multiplytofloat(int[] array, int resolution){
        final float[] flarray = new float[array.length];
        int index = 0;
        for (final int val:array){
            flarray[index++] = (float) val*resolution;
        }
        return flarray;
    }
}
