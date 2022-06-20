package be.uantwerpen.fti.ei.visuals.city;

import be.uantwerpen.fti.ei.components.Cmovement;
import be.uantwerpen.fti.ei.game.APlayer;
import be.uantwerpen.fti.ei.visuals.GraphicsCTX;

public class CityPlayer extends APlayer {
    private GraphicsCTX gctx;
    private Cmovement c_mov;
    private boolean lookingRight;
    private int lives;

    @Override
    public void vis() {

    }
    public void setC_mov(Cmovement c_mov) {
        this.c_mov=c_mov;
    }
    public Cmovement getC_mov() {
        return this.c_mov;
    }
    public void update() {
        this.c_mov.update();
    }
    public void setX(float newx) {
        this.c_mov.setX(newx);
    }
    public void setY(float newy) {
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
    public boolean isLookingRight() {
        return this.lookingRight;
    }
    public void setLookingRight(boolean b) {
        this.lookingRight=b;
    }
    public void setStanding(boolean b) {
        this.c_mov.setStanding(b);
    }
    public int getPlayerSize() {
        return (int) (gctx.getSize()*.8);
    }
    public void decreaseLives() {
        this.lives--;
    }
    public int getLives() {
        return this.lives;
    }
    public void setLives(int lives){
        this.lives=lives;
    }

    public void SetGctx(CityGCTX gctx){
        this.gctx=gctx;
    }
    public GraphicsCTX getGctx(){
        return this.gctx;
    }
    public CityPlayer(GraphicsCTX gctx){
        this.gctx=gctx;
        this.lives=3;
    }
}
