package be.uantwerpen.fti.ei.game;

import be.uantwerpen.fti.ei.components.Cmovement;

import java.util.ArrayList;
import java.util.LinkedList;

import static be.uantwerpen.fti.ei.game.Helperfunctions.realtoRel;
import static java.lang.Math.*;
import static java.lang.Math.abs;

public class CollisionDetection {
    public CollisionDetection(AFact af, APlayer player, ACollectable collectable, AObstacle obstacle, AEnemy enemy){
        this.af = af;
        this.collectable = collectable;
        this.player=player;
        this.obstacle = obstacle;
        this.enemy=enemy;
    }

    private final APlayer player;
    private final ACollectable collectable;
    private final AObstacle obstacle;
    private final AEnemy enemy;
    private final AFact af;

    public void detectCollisions(float x0, float x1, float y0, float y1){
        int blocksize = af.getGctx().getSize();


        //Collision detection can start, for this I use separate functions to keep everything clean.
        collectableCollisions(realtoRel(x0, blocksize), realtoRel(x1, blocksize), realtoRel(y0,blocksize), realtoRel(y1,blocksize));
        obstacleCollisions(x0,x1,y0,y1);
        //enemyCollisions(x_coordinate, relx, y_coordinate, rely);
    }
    private void collectableCollisions(int x0, int x1, int y0, int y1){
        // The collectablecollision has to delete a collectable and update the score.

        // if we moved a block
        if (x0!=x1 || y0!=y1){
            // check if there are any collectables at this or the following vertical line
            for (Integer xcoordinate=x1; xcoordinate<=x1+1; xcoordinate++) {
                if (this.collectable.getPos().containsKey(xcoordinate)) {
                    LinkedList<Integer> ycoords = this.collectable.getPos().get(xcoordinate);
                    // check if we hit any blocks with our head, body or feet
                    for (Integer ycoordinate : ycoords) {
                        // if we have a hit, remove the corresponding block
                        if (ycoordinate == y1 || ycoordinate == y1 + 1 || ycoordinate == y1 + 2) {
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

    }
    private void obstacleCollisions(float x0, float x1, float y0, float y1){
        int blocksize = af.getGctx().getSize();
        int playersize = player.getPlayerSize();

        boolean pureleft = false;
        boolean left = false;
        boolean pureright = false;
        boolean right = false;
        boolean pureup = false;
        boolean up = false;
        boolean puredown = false;
        boolean down = false;

        int relx0 = realtoRel(x0,blocksize);
        int relx1 = realtoRel(x1,blocksize);
        int rely0 = realtoRel(y0,blocksize);
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

                        if (xcoordinate == relx1 &&
                                ycoordinate*blocksize<y0+2*playersize &&
                                (1+ycoordinate)*blocksize>y0) {
                            pureleft = true;
                            System.out.println("Pureleft");
                        }

                        if (xcoordinate == relx1 &&
                                ycoordinate*blocksize<y1+2*playersize &&
                                (1+ycoordinate)*blocksize>y1) {
                            left = true;
                        }

                        if (xcoordinate == realtoRel(x1+playersize-1,blocksize) &&
                                ycoordinate*blocksize<y0+2*playersize &&
                                (1+ycoordinate)*blocksize>y0) {
                            pureright = true;
                            System.out.println("Pureright");
                        }

                        if (xcoordinate == realtoRel(x1+playersize-1,blocksize) &&
                                ycoordinate*blocksize<y1+2*playersize &&
                                (1+ycoordinate)*blocksize>y1) {
                            right = true;
                        }

                        if (ycoordinate == rely1 &&
                                (xcoordinate+1)*blocksize > x0 &&
                                xcoordinate*blocksize < x0+playersize) {
                            pureup = true;
                        }
                        if (ycoordinate == rely1 &&
                                (xcoordinate+1)*blocksize > x1 &&
                                xcoordinate*blocksize < x1+playersize) {
                            up = true;
                        }

                        if (    xcoordinate*blocksize<x0+playersize && (xcoordinate+1) * blocksize> x0
                                && ycoordinate*blocksize<=y1+2*playersize && (ycoordinate+1)*blocksize>y1+playersize){
                            puredown = true;
                        }

                        if (xcoordinate*blocksize<x1+playersize && (xcoordinate+1)* blocksize> x1
                                && ycoordinate*blocksize<=y1+2*playersize
                                && (ycoordinate+1)*blocksize>y1+2*playersize){
                            down = true;
                        }
                    }
                }
            }
        }
        
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

    }
    private void enemyCollisions(int realx, int relx, int realy, int rely){
        for (var enemyInstance:this.enemy.getEnemyList()){
            int blocksize = af.getGctx().getSize();
            int playersize = player.getPlayerSize();
            Cmovement movementcomponent = enemyInstance.getKey().getKey();
            float enemyx = movementcomponent.getX();
            float enemyy = movementcomponent.getY();
            if (realx<enemyx+blocksize && realx+playersize > enemyx &&
                    realy < enemyy+blocksize && realy+2*playersize > enemyy){
                System.out.println("Enemy Collision!");
            }
        }
    }
}
