package be.uantwerpen.fti.ei.visuals.J2D;

import be.uantwerpen.fti.ei.components.Cmovement;
import be.uantwerpen.fti.ei.game.ABullet;
import be.uantwerpen.fti.ei.visuals.GraphicsCTX;

import java.awt.*;
import java.util.LinkedList;

/**
 * Visualizes the projectiles as white squares with J2D
 */
public class J2DBullet extends ABullet {
    /**
     * The bullets will be kept in a Linked List for easy addition and deletion
     */
    private LinkedList<Cmovement> cmovList;
    private GraphicsCTX gctx;

    public J2DBullet(GraphicsCTX gctx){
        this.gctx = gctx;
        this.cmovList = new LinkedList<Cmovement>();
    }

    /**
     * Iterate through the bullets and colour it white
     * @param displacement
     */
    @Override
    public void vis(int displacement) {
        Graphics2D g2d = gctx.getG2d();
        g2d.setColor(new Color(255, 255, 255));
        for (Cmovement cmov :cmovList){
            g2d.fillRect((int) cmov.getX()-displacement, (int)cmov.getY(),5,5);
        }
    }
    public LinkedList<Cmovement> getCmov() {
        return cmovList;
    }

    /**
     * Creates a bullet in the middle of the player and gives it a certain direction and speed
     * @param playerx
     * @param playery
     * @param direction
     * @param framerate
     */
    public void fire(int playerx, int playery, boolean direction, float framerate){
        int playersize = (int) (this.gctx.getSize()*.8);
        if (cmovList.size()<2) { //Max number of bullets to avoid rapidfire & concurrency exceptions
            if (direction) {
                Cmovement cmov = new Cmovement(playerx + playersize, playery + playersize);
                cmov.setDx(1000 / framerate);
                cmovList.add(cmov);
                return;
            }
            Cmovement cmov = new Cmovement(playerx, playery + playersize);
            cmov.setDx(-1000 / framerate);
            cmovList.add(cmov);
        }
    }
    public void update(){
        for (Cmovement cmov:cmovList){
            cmov.update();
        }
    }
}
