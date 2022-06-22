package be.uantwerpen.fti.ei.visuals.city;

import be.uantwerpen.fti.ei.components.Cmovement;
import be.uantwerpen.fti.ei.game.AEnemy;
import be.uantwerpen.fti.ei.visuals.GraphicsCTX;

import java.awt.*;
import java.util.AbstractMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Visualizes the enemies with enemy images
 */
public class CityEnemies extends AEnemy {
    /**
     * AEnemy is a linked list which stores following data:
     *      - Movement component
     *      - Enemy Type
     *      - Min position
     *      - Max position
     * @param displacement
     */
    private LinkedList<AbstractMap.SimpleEntry<Map.Entry<Cmovement, Character>, Map.Entry<Integer,Integer>>> enemylist;
    private final CityGCTX gctx;
    private final float framerate;

    public CityEnemies(LinkedList<AbstractMap.SimpleEntry<Map.Entry<Cmovement, Character>, Map.Entry<Integer,Integer>>> enemylist, GraphicsCTX gctx, float framerate){
        this.enemylist = enemylist;
        this.gctx = (CityGCTX) gctx;
        this.framerate = framerate;
    }

    /**
     * Iterate through enemies and draw the enemy image in the corresponding place
     * @param displacement
     */
    @Override
    public void vis(int displacement) {
        Graphics2D g2d = gctx.getG2d();
        for (var enemy : enemylist){
            Cmovement mov = enemy.getKey().getKey();
            g2d.drawImage(gctx.enemy_image, (int) mov.getX()-displacement, (int) mov.getY(), null);
        }
    }

    /**
     * Iterate through enemies and check type, update position according to type and bounds
     */
    public void update() {
        for (var enemy:enemylist){
            Character type = enemy.getKey().getValue();
            Cmovement cmovement = enemy.getKey().getKey();
            Integer pos0 = enemy.getValue().getKey();
            Integer pos1 = enemy.getValue().getValue();

            switch (type) {
                case ('-') -> {
                    //System.out.println(type);
                    cmovement.setDy(0);
                    if (cmovement.getX() > pos1 * gctx.getSize()) {
                        cmovement.setDx(-50/framerate);
                    } else if (cmovement.getX() < pos0 * gctx.getSize()) {
                        cmovement.setDx(50/framerate);
                    }
                }
                case ('|') -> {
                    //System.out.println(type);
                    cmovement.setDx(0);
                    if (cmovement.getY() > pos1 * gctx.getSize()) {
                        cmovement.setDy(-50/framerate);

                    } else if (cmovement.getY() < pos0 * gctx.getSize()) {
                        cmovement.setDy(50/framerate);
                    }
                }
            }

            cmovement.update();
        }
    }
    public LinkedList<AbstractMap.SimpleEntry<Map.Entry<Cmovement, Character>, Map.Entry<Integer, Integer>>> getEnemyList() {
        return this.enemylist;
    }
    public GraphicsCTX getGctx() {
        return this.gctx;
    }

}
