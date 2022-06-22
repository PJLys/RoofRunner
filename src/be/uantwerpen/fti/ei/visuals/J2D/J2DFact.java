package be.uantwerpen.fti.ei.visuals.J2D;

import be.uantwerpen.fti.ei.game.Input;
import be.uantwerpen.fti.ei.components.Cmovement;
import be.uantwerpen.fti.ei.game.CollisionDetection;
import be.uantwerpen.fti.ei.game.*;
import be.uantwerpen.fti.ei.visuals.GraphicsCTX;

import java.util.*;

/**
 * Called when all objects are needed to be built
 */
public class J2DFact extends AFact {
    private final GraphicsCTX gctx = new GraphicsCTX();

    public GraphicsCTX getGctx() {
        return gctx;
    }
    public J2DFact(){}

    /**
     * Takes in the necessary data from the createEnemy() function in order to calculate the min and max postions and
     * call the constructor
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
     * Calls player instructor and adds a movement component.
     * @param x
     * @param y
     * @param lives
     * @return
     */
    @Override
    public APlayer createPlayer(float x, float y, int lives) {
        APlayer player = new J2DPlayer(gctx);
        player.setC_mov(new Cmovement(x, y));
        player.setLives(lives);
        return player;
    }

    /**
     * Creates the
     * @param x0
     * @param y0
     * @param d
     * @param type
     * @param framerate
     * @return
     */
    @Override
    public AEnemy createEnemy(ArrayList<Integer> x0, ArrayList<Integer> y0, ArrayList<Integer> d, ArrayList<Character> type, float framerate) {
        LinkedList<AbstractMap.SimpleEntry<Map.Entry<Cmovement, Character>, Map.Entry<Integer,Integer>>> enemyarr =
                new LinkedList<>();
        for (int it=0; it<x0.size(); it++){
            //make a movement component
            Cmovement c_mov = new Cmovement(x0.get(it)*gctx.getSize(), y0.get(it)* gctx.getSize());
            //make the first entry
            Map.Entry<Cmovement, Character> entry1 = new AbstractMap.SimpleEntry<Cmovement, Character>
                    (c_mov, type.get(it));
            if (type.get(it)=='-') {
                //System.out.println(type[it]);
                c_mov.setDx(50/framerate);
                c_mov.setDy(0);
                enemyBounds(x0, d, enemyarr, it, entry1);
            } else if (type.get(it)=='|'){
                c_mov.setDy(50/framerate);
                c_mov.setDx(0);
                enemyBounds(y0, d, enemyarr, it, entry1);
            }

        }
        return new J2DEnemy(enemyarr, gctx, framerate);
    }

    /**
     * Creates the obstacles by calling the constructor
     * @param pos
     * @return
     */
    @Override
    public AObstacle createObstacle(Map<Integer, ArrayList<Integer>> pos) {
        return new J2DObstacle(pos, gctx);
    }

    /**
     * Creates the Collectables by calling the constructor
     * @param pos
     * @return
     */
    @Override
    public ACollectable createCollectable(Map<Integer, LinkedList<Integer>> pos) {
        return new J2DCollectable(pos, gctx);
    }

    /**
     * Creates the bullet object by calling the constructor
     * @return
     */
    @Override
    public ABullet createBullet(){
        return new J2DBullet(gctx);
    }

    /**
     * Creates the bullet objects
     * @return
     */
    @Override
    public Input createInput() {
        return new Input(gctx);
    }

    /**
     * Creates the collision detection object to not overcrowd the Game class
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
