package be.uantwerpen.fti.ei.game;

import be.uantwerpen.fti.ei.Input;

import java.awt.*;
import java.util.*;
import java.util.List;

import static java.lang.Math.*;

public class Game {
    private boolean running;
    private boolean paused;
    private final AFact af;
    private AObstacle obstacle;
    private ACollectable collectable;
    private APlayer player;
    private Input input;
    private int score = 0;
    private int cellsX = 25;
    private int cellsY = 15;


    // TESTING if the draw function works
    private final Map<Integer, int[]> blocks = new HashMap<>(Map.of(
            1, new int[] {1, 2, 3, 4, 5, 7, 8, 9, 10},
            2, new int[] {2,8},
            3, new int[] {3,8},
            4, new int[] {3,8},
            5, new int[] {3,8},
            6, new int[] {8},
            7, new int[] {7,8},
            8, new int[] {6,7},
            9, new int[] {6,7}
    ));

    private final Map<Integer, LinkedList<Integer>> collectables = new HashMap<>(Map.of(
            2, new LinkedList<Integer>(List.of(4,5)), 5, new LinkedList<Integer>(List.of(5))
    ));



    public Game(AFact af){
        this.af = af;
    }
    public void run(){
        af.getGctx().setGameDimensions(cellsX, cellsY);
        running = true;
        paused = false;
        build();
        while(running){
            // INPUT
            if (input.inputAvailable()) {
                Input.Inputs movement = input.getInput();
                if (movement == Input.Inputs.SPACE) {
                    paused = !paused;
                    player.getC_mov().setDy(2);
                }
                else {
                    switch (movement) {
                        case UP -> jump(player.isStanding());
                        case LEFT -> player.getC_mov().setDx(-15);
                        case RIGHT -> player.getC_mov().setDx(15);
                        }
                    }
                }
            else {
                if (!player.isStanding())
                    player.setDy(player.getDy()+2);
                else {
                    player.setDy(0);
                }
                if (abs(player.getC_mov().getDx()*.6f)>.1)
                    player.setDx(player.getDx()*.6f);
                else
                    player.getC_mov().setDx(0);
            }
            int x0 = (int) player.getC_mov().getX();
            int y0 = (int) player.getC_mov().getY();
            // VISUALISATION
            if (!paused) {
                int x1 = (int) player.getC_mov().getDx()+x0;
                int y1 = (int) player.getC_mov().getDy()+y0;
                detectCollisions(new int[]{x0, y0, x1, y1});
                player.update();
                obstacle.vis();
                collectable.vis();
                player.vis();
                af.getGctx().render();
            } else {
                System.out.println(realToRel2(x0+player.getPlayerSize()));
                System.out.println(realToRel2(y0+player.getPlayerSize()*2));
            }

            // SLEEP
            try{
                Thread.sleep(20);
            } catch (InterruptedException e){
                System.out.println(Arrays.toString(e.getStackTrace()));
            }
        }
    }
    private void build(){
        this.input = af.createInput();
        this.obstacle = af.createObstacle(blocks);
        this.collectable = af.createCollectable(collectables);
        this.player = af.createPlayer(54*3f,54*5f,5);
    }

    //COLLISIONS
    private void detectCollisions(int[] positions){
        //Firstly, i will import the x and y coordinates of the player
        int x0 = positions[0];
        int y0 = positions[1];
        int x1 = positions[2];
        int y1 = positions[3];

        //Collision detection can start, for this I use separate functions to keep everything clean.
        collectableCollisions(realToRel(x0), realToRel(x1), realToRel(y0), realToRel(y1));
        obstacleCollisions(realToRel2(x0), realToRel(y0), realToRel2(x1),realToRel(y1),x0,x1,y0,y1);
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
                            if (ycoords.isEmpty()){
                                this.collectable.getPos().remove(xcoordinate);
                            }
                            score += 1;
                            System.out.println("Score: " + score);
                        }
                    }
                }
            }
        }

    }
    private void obstacleCollisions(int x0, int y0, int x1, int y1, int realx0, int realx1, int realy0, int realy1){
        int blocksize = af.getGctx().getSize();
        int playersize = player.getPlayerSize();
        boolean truecondition;
        int xupperbound=1;

        if (x1 == realToRel(realx1 + playersize)) {
            xupperbound = 0;
        }

        // Is there an obstacle with this x coordinate?
        for (int xcoordinate = x1; xcoordinate<=x1+xupperbound; xcoordinate++){
            player.setStanding(false);
            if (this.obstacle.getPos().containsKey(xcoordinate)) {
                // get the y_coordinates of every obstacle at x
                int[] y_coordinates = this.obstacle.getPos().get(xcoordinate);
                if (y_coordinates != null) {
                    for (int ycoordinate : y_coordinates) {

                        /*
                        We have a  collision on the top when:
                        - our basepoint is in the block or the block to the right
                        - We moved up
                         */
                        if (xupperbound==0){
                            truecondition = ycoordinate==y1
                                    && (xcoordinate==x1) && y0>y1;
                        } else {
                            truecondition = ycoordinate==y1
                                    && ((xcoordinate==x1) || xcoordinate==x1+1) && y0>y1;
                        }
                        if (truecondition) {
                            player.setDy(abs(player.getC_mov().getDy()) / 2);
                        }
                        /*
                        We have a collision on the bottom when:
                        - our lowerbound is lower then the block
                        - we moved down
                         */
                        if (xupperbound==0){
                            truecondition = (xcoordinate==x1)
                                    && ycoordinate==realToRel(y1*blocksize+2*playersize)
                                    && ycoordinate-1==realToRel(y1*blocksize+playersize);
                        } else {
                            truecondition = ((xcoordinate==x1) || xcoordinate==x1+1)
                                    && ycoordinate==realToRel(y1*blocksize+2*playersize)
                                    && ycoordinate-1==realToRel(y1*blocksize+playersize);
                        }

                        if (truecondition && !player.isStanding()) {
                            player.setY(ycoordinate*blocksize-2*playersize);
                            player.setDy(0);
                            player.setStanding(true);
                            System.out.println("B");
                        }


                        /*
                        We have a collision on the left when
                         - The block is in the same relative position
                         - We previously moved to the left
                         */

                        truecondition = realx0>realx1
                                && xcoordinate == x1
                                && ycoordinate >= y1 && ycoordinate < realToRel(realy1+2*playersize);

                        if (truecondition) {
                            float newdx = 0;
                            if (!player.isStanding())
                                newdx = -player.getDx()/5;
                            player.setDx(newdx);
                            player.setX((xcoordinate+1)*blocksize);
                            System.out.println("L");
                        }

                        /*
                        We have a collision on the right when
                         - The block is in the next relative position
                         - We previously moved to the right
                         */

                        truecondition = realx0<realx1
                                && xcoordinate == realToRel2(realx1+playersize)
                                && ycoordinate >= y1 && ycoordinate < realToRel(realy1+2*playersize);

                        if (truecondition) {
                            float newdx = 0;
                            if (!player.isStanding())
                                newdx = -player.getDx()/5;
                            player.setDx(newdx);
                            player.setX((xcoordinate-1)*blocksize+(blocksize-playersize));
                            System.out.println("R");
                        }
                    }
                }
            }
        }
    }
    private void enemyCollisions(int realx, int relx, int realy, int rely){

    }

    //JUMP
    private void jump(boolean standing){
        if (standing) {
            player.setDy(-25);
        }
        else
            player.setDy(player.getDy()+2);
    }


    //Helper functions
    private int realToRel(float realcoordinate){
        return (int) (realcoordinate/af.getGctx().getSize()+.5);
    }
    private int realToRel2(float realcoordinate){
        return (int) (realcoordinate/af.getGctx().getSize());
    }

}
