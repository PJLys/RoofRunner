package be.uantwerpen.fti.ei.visuals;

import be.uantwerpen.fti.ei.GraphicsCTX;
import be.uantwerpen.fti.ei.Input;
import be.uantwerpen.fti.ei.components.Cmovement;
import be.uantwerpen.fti.ei.game.*;

import java.util.LinkedList;

public class J2DFact extends AFact {
    private int resolution;
    private final GraphicsCTX gctx = new GraphicsCTX();

    public J2DFact(){}

    @Override
    public APlayer createPlayer(int x, int y, int lives, GraphicsCTX gctx) {
        APlayer player = new J2DPlayer();
        float realx = (float) x*resolution;
        float realy = (float) y*resolution;
        player.setC_mov(new Cmovement(realx, realy));
        return player;
    }

    @Override
    public AEnemy createEnemy(int[] x, int[] y, GraphicsCTX gctx) {
        AEnemy enemy = new J2DEnemy();
        enemy.setC_move(createMovables(x,y, resolution));
        return enemy;
    }

    @Override
    public AObstacle createObstacle(int[] x, int[] y, GraphicsCTX gctx) {
        return new J2DObstacle();
    }

    @Override
    public ACollectable createCollectable(int[] x, int[] y, GraphicsCTX gctx) {
        return new J2DCollectable();
    }

    @Override
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
