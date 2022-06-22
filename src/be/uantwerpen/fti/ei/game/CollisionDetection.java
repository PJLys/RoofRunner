package be.uantwerpen.fti.ei.game;

import be.uantwerpen.fti.ei.components.Cmovement;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.LinkedList;
import java.util.Vector;

import static be.uantwerpen.fti.ei.game.Helperfunctions.realtoRel;
import static java.lang.Math.*;
import static java.lang.Math.abs;

/**
 * Separate collision detection class for both detection and correction
 */
public class CollisionDetection {
    public CollisionDetection(AFact af, APlayer player, ACollectable collectable, AObstacle obstacle, AEnemy enemy, ABullet bullet, float framerate){
        this.af = af;
        this.collectable = collectable;
        this.player=player;
        this.obstacle = obstacle;
        this.enemy=enemy;
        this.bullet=bullet;
        this.framerate=framerate;
    }
    private final APlayer player;
    private final ACollectable collectable;
    private final AObstacle obstacle;
    private final AEnemy enemy;
    private final AFact af;
    private final ABullet bullet;
    private final float framerate;
    // Collision Flags
    private boolean pureleft;
    private boolean left;
    private boolean pureright;
    private boolean right;
    private boolean pureup;
    private boolean up;
    private boolean puredown;
    private boolean down;
    private boolean enemycollision;
    // Const
    private int blocksize;
    private int playersize;

    /**
     * Function called by game class taking present and future as inputs.
     *      - setting playersize and blocksize
     *      - initialize the collision flags to false
     *      - detect collisions (set flags)
     *      - Adjust position
     * @param x0
     * @param x1
     * @param y0
     * @param y1
     */
    public void detectCollisions(float x0, float x1, float y0, float y1){
        blocksize = af.getGctx().getSize();
        playersize = player.getPlayerSize();
        //Initialize flags
        initFlags();
        //Actual collision detection
        obstacleCollisions(x0,x1,y0,y1);
        collectableCollisions(x0, y0);
        enemyCollisions(x1,y1);
        bulletCollisions();

        //Adjusting positions for immutables
        positionUpdate(realtoRel(x1, blocksize), realtoRel(y1, blocksize));
    }

    /**
     * iterates through the relevant relative collectable positions and detects collisions.
     * Checks for collisions on the current (actual) position
     * @param x0
     * @param y0
     */
    private void collectableCollisions(float x0, float y0){
        // check if there are any collectables at this or the following vertical line
        for (Integer xcoordinate=realtoRel(x0,blocksize); xcoordinate<=realtoRel(x0,blocksize)+1; xcoordinate++) {
            if (this.collectable.getPos().containsKey(xcoordinate)) {
                LinkedList<Integer> ycoords = this.collectable.getPos().get(xcoordinate);
                // check if we hit any blocks with our head, body or feet
                for (Integer ycoordinate : ycoords) {
                    // if we have a hit, remove the corresponding block and increase the score
                    if (x0<(xcoordinate+1)*blocksize  && x0+playersize>xcoordinate*blocksize &&
                            y0+2*playersize>ycoordinate*blocksize && y0<(ycoordinate+1)*blocksize) {
                        ycoords.remove(ycoordinate);
                        Game.incScore();
                        if (ycoords.isEmpty()){
                            collectable.getPos().remove(xcoordinate);
                        }
                    }
                }
            }
        }

    }

    /**
     * Iterates through the relevant object columns and sets different flags depending on current and future position
     * @param x0
     * @param x1
     * @param y0
     * @param y1
     */
    private void obstacleCollisions(float x0, float x1, float y0, float y1){
        int relx0 = realtoRel(x0,blocksize);
        int relx1 = realtoRel(x1,blocksize);
        int rely1 = realtoRel(y1,blocksize);
        // Is there an obstacle with this x coordinate?
        for (int xcoordinate = min(relx1,relx0);
             xcoordinate<=max(realtoRel(x1+2*playersize, blocksize), realtoRel(x0+2*playersize, blocksize));
             xcoordinate++){
            if (this.obstacle.getPos().containsKey(xcoordinate)) {
                // get the y_coordinates of every obstacle at x
                ArrayList<Integer> ycoordinates = this.obstacle.getPos().get(xcoordinate);
                if (ycoordinates != null) {
                    for (int ycoordinate : ycoordinates) {

                        // Check for left collision if we only move horizontally
                        if (xcoordinate == relx1 &&
                                ycoordinate*blocksize<y0+2*playersize &&
                                (1+ycoordinate)*blocksize>y0) {
                            pureleft = true;
                        }

                        // Check for left collision in the projected position
                        if (xcoordinate == relx1 &&
                                ycoordinate*blocksize<y1+2*playersize &&
                                (1+ycoordinate)*blocksize>y1) {
                            left = true;
                        }

                        // Check for right collision if we only move horizontally
                        if (xcoordinate == realtoRel(x1+playersize-1,blocksize) &&
                                ycoordinate*blocksize<y0+2*playersize &&
                                (1+ycoordinate)*blocksize>y0) {
                            pureright = true;
                        }

                        // Check for right collision in the projected position
                        if (xcoordinate == realtoRel(x1+playersize-1,blocksize) &&
                                ycoordinate*blocksize<y1+2*playersize &&
                                (1+ycoordinate)*blocksize>y1) {
                            right = true;
                        }

                        // Check for top collision if we only move vertically
                        if (ycoordinate == rely1 &&
                                (xcoordinate+1)*blocksize > x0 &&
                                xcoordinate*blocksize < x0+playersize) {
                            pureup = true;
                        }

                        // Check for top collision in the projected position
                        if (ycoordinate == rely1 &&
                                (xcoordinate+1)*blocksize > x1 &&
                                xcoordinate*blocksize < x1+playersize) {
                            up = true;
                        }

                        // Check for bottom collision if we only move vertically
                        if (    xcoordinate*blocksize<x0+playersize && (xcoordinate+1) * blocksize> x0
                                && ycoordinate*blocksize<=y1+2*playersize && (ycoordinate+1)*blocksize>y1+playersize){
                            puredown = true;
                        }

                        // Check for bottom collision in the projected position
                        if (xcoordinate*blocksize<x1+playersize && (xcoordinate+1)* blocksize> x1
                                && ycoordinate*blocksize<=y1+2*playersize
                                && (ycoordinate+1)*blocksize>y1+2*playersize){
                            down = true;
                        }
                    }
                }
            }
        }
    }

    /**
     * Iterates through all enemy objects and tests for collision
     * @param realx
     * @param realy
     */
    private void enemyCollisions(float realx, float realy){
        for (var enemyInstance:this.enemy.getEnemyList()){
            int blocksize = af.getGctx().getSize();
            int playersize = player.getPlayerSize();
            Cmovement movementcomponent = enemyInstance.getKey().getKey();
            float enemyx = movementcomponent.getX();
            float enemyy = movementcomponent.getY();
            if (realx<enemyx+blocksize && realx+playersize > enemyx &&
                    realy < enemyy+blocksize && realy+2*playersize > enemyy){
                enemycollision=true;
            }
        }
    }

    /**
     * Resets all collision flags
     */
    private void initFlags(){
        down = false;
        puredown = false;
        up = false;
        pureup = false;
        right = false;
        pureright = false;
        left = false;
        pureleft = false;
        enemycollision = false;
    }

    /**
     * Looks at the flags and edits the position accordingly
     * @param relx1
     * @param rely1
     */
    private void positionUpdate(int relx1, int rely1){
        boolean collisiontrue= false;
        int newy = rely1 * blocksize + 2 * (blocksize - playersize);
        if (pureleft && left){
            player.setDx(0);
            player.setX((relx1+1)*blocksize);
            collisiontrue = true;
        }
        if (pureright && right){
            player.setDx(0);
            player.setX((relx1+1)*blocksize-playersize);
            collisiontrue=true;
        }
        if (puredown && down) {
            if (!player.isStanding()) {
                player.setY(newy);
            }
            player.setDy(0);
            player.setStanding(true);
            collisiontrue = true;
        } else
            player.setStanding(false);
        if (pureup && up) {
            player.setDy(0);
            if (!player.isStanding())
                player.setY((rely1+1)*blocksize);
            collisiontrue=true;
        }
        if (!collisiontrue){
            if (left){
                if (player.isStanding()){
                    player.setDx(0);
                    player.setX((relx1)*blocksize);
                } else{
                    player.setDx(abs(player.getDx()/2));
                }
            }
            if (right){
                if (player.isStanding()){
                    player.setDx(0);
                    player.setX((relx1-1)*blocksize-blocksize+playersize);
                } else
                    player.setDx(abs(player.getDx()/2));
            }

            if (down) {
                if (!player.isStanding()) {
                    player.setY(newy);
                }
                player.setDy(0);
                player.setStanding(true);
            } else
                player.setStanding(false);

            if (up) {
                player.setDy(0);
                if (!player.isStanding())
                    player.setY((rely1+1)*blocksize);
            }
        }

        if (enemycollision) {
            player.setX(4*af.getGctx().getSize());
            player.setY(4*af.getGctx().getSize());
            player.decreaseLives();
        }
    }

    /**
     * Check for a collision between a bullet and obstacles or enemies
     *      - removes bullet when there's a collision
     *      - Removes an enemy when there's a collision + increases score
     */
    private void bulletCollisions(){
        LinkedList<Cmovement> cmovlist = bullet.getCmov();
        for (Cmovement cmov:cmovlist) {
            float bulletx = cmov.getX();
            float bullety = cmov.getY();
            //Obstacle collisions
            var obstacles = obstacle.getPos();
            try {
                ArrayList<Integer> ycoords = obstacles.get(realtoRel(bulletx, blocksize));
                for (int ycoord : ycoords) {
                    if (ycoord == realtoRel(bullety, blocksize))
                        cmovlist.remove(cmov);
                }
            } catch (NullPointerException ignored){}

            //Enemy Collisions
            //Geeft ConcurrentModificationException
            //Oplossing: Probeer de code uit te voeren uitgezonderd Exception
            //Resultaat: voor een bepaalde iteratie zal er geen collision detection uitgevoerd worden
            //Werkt omdat: Bij de volgende iteratie deze wel zal werken.
            try {
                for (var enemyInstance : this.enemy.getEnemyList()) {
                    float enemyx = enemyInstance.getKey().getKey().getX();
                    float enemyy = enemyInstance.getKey().getKey().getY();
                    if (bulletx > enemyx & bulletx < enemyx + blocksize &
                            bullety > enemyy & bullety < enemyy + blocksize) {
                        this.enemy.getEnemyList().remove(enemyInstance);
                        cmovlist.remove(cmov);
                        Game.incScore();
                    }
                }
            } catch (ConcurrentModificationException e){
                System.out.println(e);
            }


        }
    }
}
