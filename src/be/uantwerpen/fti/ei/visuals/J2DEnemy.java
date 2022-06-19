package be.uantwerpen.fti.ei.visuals;

import be.uantwerpen.fti.ei.components.Cmovement;
import be.uantwerpen.fti.ei.game.AEnemy;

import java.awt.*;
import java.util.AbstractMap;
import java.util.LinkedList;
import java.util.Map;



public class J2DEnemy extends AEnemy {
    private GraphicsCTX gctx;
    private float framerate;
    private LinkedList<AbstractMap.SimpleEntry<Map.Entry<Cmovement, Character>, Map.Entry<Integer,Integer>>> enemylist;


    public GraphicsCTX getGctx() {
        return gctx;
    }
    public J2DEnemy(LinkedList<AbstractMap.SimpleEntry<Map.Entry<Cmovement, Character>, Map.Entry<Integer,Integer>>> enemylist, GraphicsCTX gctx, float framerate){
        this.enemylist = enemylist;
        this.gctx = gctx;
        this.framerate = framerate;
    }

    @Override
    public void update(){
        for (var enemy : enemylist) {

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
    public void vis(int displacement) {
        Graphics2D g2d = gctx.getG2d();
        int blocksize = getGctx().getSize();
        g2d.setColor(new Color(247, 0, 255));
        for (var enemy : enemylist){
            Cmovement mov = enemy.getKey().getKey();
            g2d.fillRect(
                    (int) mov.getX()-displacement,
                    (int) mov.getY(),
                    blocksize,
                    blocksize);
        }
    }
    public LinkedList<AbstractMap.SimpleEntry<Map.Entry<Cmovement, Character>, Map.Entry<Integer,Integer>>> getEnemyList(){
        return this.enemylist;
    }
}
