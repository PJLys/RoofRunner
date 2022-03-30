package be.uantwerpen.fti.ei.visuals;

import be.uantwerpen.fti.ei.GraphicsCTX;
import be.uantwerpen.fti.ei.components.Cmovement;
import be.uantwerpen.fti.ei.game.APlayer;
import jdk.jfr.BooleanFlag;

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
    public int[] update() {
        int[] positions = new int[4];
        positions[0] = (int) this.c_mov.getX();
        positions[1] = (int) this.c_mov.getY();
        this.c_mov.update();
        positions[2] = (int) this.c_mov.getX();
        positions[3] = (int) this.c_mov.getY();
        return positions;
    }
    public void resetflags(){
        this.c_mov.setUpFlag(false);
        this.c_mov.setRightFlag(false);
        this.c_mov.setLeftFlag(false);
        this.c_mov.setLowFlag(false);
    }
    @BooleanFlag
    public void setLowerFlag(boolean b){
        this.c_mov.setLowFlag(b);
    }
    public void setUpperFlag(boolean b) {
        this.c_mov.setUpFlag(b);
    }
    public void setLeftFlag(boolean b) {
        this.c_mov.setLeftFlag(b);
    }
    public void setRightFlag(boolean b) {
        this.c_mov.setRightFlag(b);
    }
    public boolean getLowerFlag() {
        return this.c_mov.getLowFlag();
    }
    public boolean getUpperFlag() {
        return this.c_mov.getUpFlag();
    }
    public boolean getLeftFlag() {
        return this.c_mov.getLeftFlag();
    }
    public boolean getRightFlag() {
        return this.c_mov.getRightFlag();
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
