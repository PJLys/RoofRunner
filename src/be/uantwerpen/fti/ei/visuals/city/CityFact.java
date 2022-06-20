package be.uantwerpen.fti.ei.visuals.city;

import be.uantwerpen.fti.ei.components.Cmovement;
import be.uantwerpen.fti.ei.game.*;
import be.uantwerpen.fti.ei.visuals.GraphicsCTX;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

public class CityFact extends AFact {
    private final GraphicsCTX gctx = new CityGCTX();

    public CityFact() throws IOException {
    }

    private void enemyBounds(ArrayList<Integer> coord, ArrayList<Integer> d, LinkedList<AbstractMap.SimpleEntry<Map.Entry<Cmovement, Character>, Map.Entry<Integer, Integer>>> enemyarr, int it, Map.Entry<Cmovement, Character> entry1) {
        Map.Entry<Integer,Integer> entry2 = new AbstractMap.SimpleEntry<Integer,Integer>(coord.get(it),coord.get(it)+d.get(it));
        enemyarr.add(new AbstractMap.SimpleEntry<Map.Entry<Cmovement, Character>, Map.Entry<Integer,Integer>>(entry1, entry2));
    }

    @Override
    public APlayer createPlayer(float x, float y, int lives) {
        APlayer player = new CityPlayer(gctx);
        player.setC_mov(new Cmovement(x,y));
        player.setLives((char) 3);
        return player;
    }
    public AEnemy createEnemy(ArrayList<Integer> x, ArrayList<Integer> y, ArrayList<Integer> d, ArrayList<Character> type, float framerate) {
        LinkedList<AbstractMap.SimpleEntry<Map.Entry<Cmovement, Character>, Map.Entry<Integer,Integer>>> enemyarr = new LinkedList<>();
        for (int it=0; it<x.size(); it++){
            //make a movement component
            Cmovement c_mov = new Cmovement(x.get(it)*gctx.getSize(), y.get(it)* gctx.getSize());
            //make the first entry
            Map.Entry<Cmovement, Character> entry1 = new AbstractMap.SimpleEntry<Cmovement, Character>
                    (c_mov, type.get(it));
            if (type.get(it)=='-') {
                //System.out.println(type[it]);
                c_mov.setDx(1);
                c_mov.setDy(0);
                enemyBounds(x, d, enemyarr, it, entry1);
            } else if (type.get(it)=='|'){
                c_mov.setDy(1);
                c_mov.setDx(0);
                enemyBounds(y, d, enemyarr, it, entry1);
            }
        }
        return new CityEnemies(enemyarr, gctx,framerate);
    }
    public AObstacle createObstacle(Map<Integer, ArrayList<Integer>> pos) {
        return new CityObstacles(pos,gctx);
    }
    public ACollectable createCollectable(Map<Integer, LinkedList<Integer>> pos) {
        return new CityCollectables(pos,gctx);
    }
    public ABullet createBullet(){
        return new CityBullets(gctx);
    }
    public Input createInput() {
        return new Input(gctx);
    }
    public GraphicsCTX getGctx() {
        return this.gctx;
    }
    public CollisionDetection createCD(AFact af, APlayer player, ACollectable collectable, AObstacle obstacle, AEnemy enemy, ABullet bullet, float framerate) {
        return new CollisionDetection(af, player, collectable, obstacle, enemy, bullet, framerate);
    }
}
