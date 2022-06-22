package be.uantwerpen.fti.ei.visuals.city;

import be.uantwerpen.fti.ei.components.Cmovement;
import be.uantwerpen.fti.ei.game.*;
import be.uantwerpen.fti.ei.visuals.GraphicsCTX;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

/**
 * Creates all objects needed for the City style visualization of the game
 */
public class CityFact extends AFact {
    private final GraphicsCTX gctx = new CityGCTX();

    public CityFact() {}

    /**
     * Adds bounds for the movement of the enemy object and adds it to the enemy array
     * @param coord
     * @param d
     * @param enemyarr
     * @param it
     * @param entry1
     */
    private void enemyBounds(ArrayList<Integer> coord, ArrayList<Integer> d, LinkedList<AbstractMap.SimpleEntry<Map.Entry<Cmovement, Character>, Map.Entry<Integer, Integer>>> enemyarr, int it, Map.Entry<Cmovement, Character> entry1) {
        Map.Entry<Integer,Integer> entry2 = new AbstractMap.SimpleEntry<Integer,Integer>(coord.get(it),coord.get(it)+d.get(it));
        enemyarr.add(new AbstractMap.SimpleEntry<Map.Entry<Cmovement, Character>, Map.Entry<Integer,Integer>>(entry1, entry2));
    }

    /**
     * Creates a new player and add a movement component to it. Set lives to 3 (standard).
     * @param x
     * @param y
     * @param lives
     * @return
     */
    @Override
    public APlayer createPlayer(float x, float y, int lives) {
        APlayer player = new CityPlayer(gctx);
        player.setC_mov(new Cmovement(x,y));
        player.setLives((char) 3);
        return player;
    }

    /**
     * Makes a movement component and reads in type. Depending on the type it will call the enemybounds() function that calls the actual constructor.
     * @param x
     * @param y
     * @param d
     * @param type
     * @param framerate
     * @return
     */
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

    /**
     * Call for obstacle constructor and adds Graphics context
     * @param pos
     * @return
     */
    public AObstacle createObstacle(Map<Integer, ArrayList<Integer>> pos) {
        return new CityObstacles(pos,gctx);
    }
    /**
     * Call for collectable constructor and adds Graphics context
     * @param pos
     * @return
     */
    public ACollectable createCollectable(Map<Integer, LinkedList<Integer>> pos) {
        return new CityCollectables(pos,gctx);
    }

    /**
     * Call for bullet constructor and adds Graphics context
     * @return
     */
    public ABullet createBullet(){
        return new CityBullets(gctx);
    }

    /**
     * Call for input constructor and adds Graphics context
     * @return
     */
    public Input createInput() {
        return new Input(gctx);
    }
    public GraphicsCTX getGctx() {
        return this.gctx;
    }

    /**
     * Creates a collision detection object in order not to overcrowd the game class
     * @param af
     * @param player
     * @param collectable
     * @param obstacle
     * @param enemy
     * @param bullet
     * @param framerate
     * @return
     */
    public CollisionDetection createCD(AFact af, APlayer player, ACollectable collectable, AObstacle obstacle, AEnemy enemy, ABullet bullet, float framerate) {
        return new CollisionDetection(af, player, collectable, obstacle, enemy, bullet, framerate);
    }
}
