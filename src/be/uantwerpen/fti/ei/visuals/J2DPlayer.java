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
        int blocksize = gctx.getSize();
        g2d.setColor(new Color(0,255,0));
        g2d.fillRect((int) this.c_mov.getX(), (int) this.c_mov.getY(), blocksize, 2*blocksize);
    }
    public void setC_mov(Cmovement c_mov) {
        this.c_mov = c_mov;
    }
    public Cmovement getC_mov() {
        return this.c_mov;
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
