package be.uantwerpen.fti.ei.visuals.city;

import be.uantwerpen.fti.ei.components.Cmovement;
import be.uantwerpen.fti.ei.game.APlayer;
import be.uantwerpen.fti.ei.visuals.GraphicsCTX;

import java.awt.*;

/**
 * Visualizes the player with an image of an assassin character
 */
public class CityPlayer extends APlayer {
    private CityGCTX gctx;
    private Cmovement c_mov;
    private boolean lookingRight;
    private int lives;

    /**
     * The player image will always be visualized at the same horizontal position on the screen and
     */
    @Override
    public void vis() {
        Graphics2D g2d = gctx.getG2d();
        if (isLookingRight())
            g2d.drawImage(gctx.player_image_right, 4*gctx.getSize(), (int) this.c_mov.getY(),null);
        else {
            g2d.drawImage(gctx.player_image_left, 4*gctx.getSize(), (int) this.c_mov.getY(),null);
        }
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

    public GraphicsCTX getGctx(){
        return this.gctx;
    }

    /**
     * Constructor which sets the amount of lives at 3 (standard)
     * @param gctx
     */
    public CityPlayer(GraphicsCTX gctx){
        this.gctx= (CityGCTX) gctx;
        this.lives=3;
    }
}
