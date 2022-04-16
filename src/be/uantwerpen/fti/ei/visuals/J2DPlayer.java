package be.uantwerpen.fti.ei.visuals;

import be.uantwerpen.fti.ei.GraphicsCTX;
import be.uantwerpen.fti.ei.components.Cmovement;
import be.uantwerpen.fti.ei.game.APlayer;

import java.awt.*;

public class J2DPlayer extends APlayer {
    private GraphicsCTX gctx;
    private Cmovement c_mov;

    @Override
    public void vis() {
        Graphics2D g2d = gctx.getG2d();
        int playersize = (int) (gctx.getSize()*.8);
        g2d.setColor(new Color(0,255,0));
        g2d.fillRect((int) this.c_mov.getX(), (int) this.c_mov.getY(), playersize, 2*playersize);
    }
    public void setC_mov(Cmovement c_mov) {
        this.c_mov = c_mov;
    }
    public Cmovement getC_mov() {
        return this.c_mov;
    }
    public void update() {
        c_mov.update();
    }

    public void setX(float newx){
        this.c_mov.setX(newx);
    }
    public void setY(float newy){
        this.c_mov.setY(newy);
    }
    public void setDx(float newdx) {
        this.c_mov.setDx(newdx);
    }
    public void setDy(float newdy) {
        this.c_mov.setDy(newdy);
    }
    public float getDy() {
        return this.c_mov.getDy();
    }
    public float getDx() {
        return this.c_mov.getDx();
    }
    public boolean isStanding() {
        return this.c_mov.isStanding();
    }
    public void setStanding(boolean b) {
        this.c_mov.setStanding(b);
    }
    public int getPlayerSize(){
        return (int) (gctx.getSize()*.8);
    }

    public void setGctx(GraphicsCTX gctx){
        this.gctx = gctx;
    }
    public GraphicsCTX getGctx(){
        return this.gctx;
    }
    public J2DPlayer(GraphicsCTX gctx){
        this.gctx = gctx;
    }

}
