package be.uantwerpen.fti.ei.visuals.city;

import be.uantwerpen.fti.ei.components.Cmovement;
import be.uantwerpen.fti.ei.game.ABullet;
import be.uantwerpen.fti.ei.visuals.GraphicsCTX;

import java.awt.*;
import java.util.LinkedList;

public class CityBullets extends ABullet {
    /**
     * Bullet data type
     * Linked list
     * - Easy add/remove
     * - No iteration problems when out of order remove (instead of FIFO)
     *
     * @return
     */
    private LinkedList<Cmovement> cmovList;
    private CityGCTX gctx;

    public CityBullets(GraphicsCTX gctx){
        this.gctx = (CityGCTX) gctx;
        this.cmovList = new LinkedList<Cmovement>();
    }

    /**
     * Visualise the projectiles with throwing knives.
     * @param displacement
     */
    @Override
    public void vis(int displacement) {
        Graphics2D g2d = gctx.getG2d();
        for (Cmovement cmov :cmovList){
            if (cmov.getDx()>0)
                g2d.drawImage(gctx.projectile_image_right, (int) cmov.getX()-displacement, (int) cmov.getY(),null);
            else
                g2d.drawImage(gctx.projectile_image_left, (int) cmov.getX()-displacement, (int) cmov.getY(),null);

        }
    }
    public LinkedList<Cmovement> getCmov() {
        return this.cmovList;
    }

    /**
     * Fires the bullet from the middle of the player in the right direction with the correct speed
     * @param playerx
     * @param playery
     * @param direction
     * @param framerate
     */
    public void fire(int playerx, int playery, boolean direction, float framerate) {
        int playersize = (int) (this.gctx.getSize() * .8);
        if (cmovList.size() < 2) { //Max number of bullets to avoid rapidfire & concurrency exceptions
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

    /**
     * Call the position update function on the movement component
     */
    public void update() {
        for (Cmovement cmov:cmovList){
            cmov.update();
        }
    }
}
