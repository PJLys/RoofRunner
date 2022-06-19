package be.uantwerpen.fti.ei.visuals;

import be.uantwerpen.fti.ei.components.Cmovement;
import be.uantwerpen.fti.ei.game.ABullet;

import java.awt.*;
import java.util.LinkedList;

public class J2DBullet extends ABullet {
    private LinkedList<Cmovement> cmovList;
    private GraphicsCTX gctx;

    public J2DBullet(GraphicsCTX gctx){
        this.gctx = gctx;
        this.cmovList = new LinkedList<Cmovement>();
    }

    @Override
    public void vis(int displacement) {
        Graphics2D g2d = gctx.getG2d();
        g2d.setColor(new Color(255, 255, 255));
        for (Cmovement cmov :cmovList){
            g2d.fillRect((int) cmov.getX()-displacement, (int)cmov.getY(),5,5);
        }
    }

    @Override
    public LinkedList<Cmovement> getCmov() {
        return cmovList;
    }

    @Override
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

    @Override
    public void update(){
        for (Cmovement cmov:cmovList){
            cmov.update();
        }
    }
}
