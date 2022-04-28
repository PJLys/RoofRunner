package be.uantwerpen.fti.ei.visuals;

import be.uantwerpen.fti.ei.game.Input;
import be.uantwerpen.fti.ei.components.Cmovement;
import be.uantwerpen.fti.ei.game.CollisionDetection;
import be.uantwerpen.fti.ei.game.*;

import java.util.AbstractMap;
import java.util.Map;
import java.util.LinkedList;

public class J2DFact extends AFact {
    private final GraphicsCTX gctx = new GraphicsCTX();

    public GraphicsCTX getGctx() {
        return gctx;
    }
    public J2DFact(){}

    @Override
    public APlayer createPlayer(float x, float y, int lives) {
        APlayer player = new J2DPlayer(gctx);
        player.setC_mov(new Cmovement(x, y));
        return player;
    }
    public AEnemy createEnemy(int[] x0, int[] y0, int[] d, char[] type) {
        LinkedList<AbstractMap.SimpleEntry<Map.Entry<Cmovement, Character>, Map.Entry<Integer,Integer>>> enemyarr =
                new LinkedList<>();
        for (int it=0; it<x0.length; it++){
            //make a movement component
            Cmovement c_mov = new Cmovement(x0[it]*gctx.getSize(), y0[it]* gctx.getSize());
            //make the first entry
            Map.Entry<Cmovement, Character> entry1 = new AbstractMap.SimpleEntry<Cmovement, Character>
                    (c_mov, type[it]);
            if (type[it]=='=') {
                //System.out.println(type[it]);
                c_mov.setDx(1);
                c_mov.setDy(0);
                enemyBounds(x0, d, enemyarr, it, entry1);
            } else if (type[it]=='|'){
                c_mov.setDy(1);
                c_mov.setDx(0);
                enemyBounds(y0, d, enemyarr, it, entry1);
            }

        }
        return new J2DEnemy(enemyarr, gctx);
    }

    private void enemyBounds(int[] coord, int[] d, LinkedList<AbstractMap.SimpleEntry<Map.Entry<Cmovement, Character>, Map.Entry<Integer, Integer>>> enemyarr, int it, Map.Entry<Cmovement, Character> entry1) {
        Map.Entry<Integer,Integer> entry2 = new AbstractMap.SimpleEntry<Integer,Integer>(coord[it],coord[it]+d[it]);
        enemyarr.add(new AbstractMap.SimpleEntry<Map.Entry<Cmovement, Character>, Map.Entry<Integer,Integer>>(entry1, entry2));
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
    public CollisionDetection createCD(AFact af, APlayer player, ACollectable collectable, AObstacle obstacle) {
        return new CollisionDetection(af, player, collectable, obstacle);
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
