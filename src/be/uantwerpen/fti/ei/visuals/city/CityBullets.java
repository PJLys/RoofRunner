package be.uantwerpen.fti.ei.visuals.city;

import be.uantwerpen.fti.ei.components.Cmovement;
import be.uantwerpen.fti.ei.game.ABullet;
import be.uantwerpen.fti.ei.visuals.GraphicsCTX;

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
    private GraphicsCTX gctx;

    public CityBullets(GraphicsCTX gctx){
        this.gctx = gctx;
        this.cmovList = new LinkedList<Cmovement>();
    }

    @Override
    public void vis(int displacement) {

    }
    public LinkedList<Cmovement> getCmov() {
        return this.cmovList;
    }
    public void fire(int playerx, int playery, boolean direction, float framerate) {
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
    public void update() {
        for (Cmovement cmov:cmovList){
            cmov.update();
        }
    }
}
