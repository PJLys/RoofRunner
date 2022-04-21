package be.uantwerpen.fti.ei.game;

import be.uantwerpen.fti.ei.Input;

import java.util.*;

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
            2, new int[] {4,8},
            3, new int[] {4,7,8},
            4, new int[] {4,8},
            5, new int[] {8},
            6, new int[] {8},
            7, new int[] {6,8},
            8, new int[] {6,7},
            9, new int[] {6,7}
    ));

    private final Map<Integer, LinkedList<Integer>> collectables = new HashMap<>(Map.of(
            2, new LinkedList<>(List.of(4, 5)), 5, new LinkedList<>(List.of(5))
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
                if (abs(player.getDx()*.6f)>.1)
                    player.setDx(player.getDx()*.6f);
                else
                    player.getC_mov().setDx(0);
            }
            int x0 = (int) player.getC_mov().getX();
            int y0 = (int) player.getC_mov().getY();
            // VISUALISATION
            if (!paused) {
                player.setDy(player.getDy()+3);
                int x1 = (int) player.getDx()+x0;
                int y1 = (int) player.getDy()+y0;
                detectCollisions(x0, x1, y0, y1);
                //if (jumpflag)
                //    jump(player.isStanding());
                player.update();
                obstacle.vis();
                collectable.vis();
                player.vis();
                af.getGctx().render();
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
    private void detectCollisions(float x0, float x1,float y0,float y1){
        //Collision detection can start, for this I use separate functions to keep everything clean.
        collectableCollisions(realtoRel(x0), realtoRel(x1), realtoRel(y0), realtoRel(y1));
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

        int relx0 = realtoRel(x0);
        int relx1 = realtoRel(x1);
        int rely0 = realtoRel(y0);
        int rely1 = realtoRel(y1);
        // Is there an obstacle with this x coordinate?
        for (int xcoordinate = min(relx1,relx0);
                                xcoordinate<=max(realtoRel(x1+playersize), realtoRel(x0+playersize));
                                xcoordinate++){
            if (this.obstacle.getPos().containsKey(xcoordinate)) {
                // get the y_coordinates of every obstacle at x
                int[] y_coordinates = this.obstacle.getPos().get(xcoordinate);
                if (y_coordinates != null) {
                    for (int ycoordinate : y_coordinates) {

                        /*
                        We have a collision on the left when
                         - The block is in the same relative position
                         - We previously moved to the left
                         - If we just move left, we have a collision
                         */

                        if (x0>x1 && xcoordinate == relx1 && ycoordinate*blocksize<y0+2*playersize && (1+ycoordinate)*blocksize>y0){
                            System.out.println("Pureleft");
                            pureleft = true;
                        }
                        if (x0>x1 && xcoordinate == relx1 && ycoordinate*blocksize<y1+2*playersize && (1+ycoordinate)*blocksize>y1){
                            System.out.println("Left");
                            left = true;
                        }

                        /*
                        We have a collision on the right when
                         - The block is in the next relative position
                         - We previously moved to the right
                         */

                        if (x0<x1 && xcoordinate == realtoRel(x1+playersize) && ycoordinate*blocksize<y0+2*playersize && (1+ycoordinate)*blocksize>y0){
                            System.out.println("Pureright");
                            pureright = true;
                        }
                        if (x0<x1 && xcoordinate == realtoRel(x1+playersize) && ycoordinate*blocksize<y1+2*playersize && (1+ycoordinate)*blocksize>y1){
                            System.out.println("Right");
                            right = true;
                        }

                        /*
                        We have a  collision on the top when:
                        - our basepoint is in the block or the block to the right
                        - We moved up
                         */

                        if (y0>y1 && ycoordinate == rely1 && (xcoordinate+1)*blocksize >= x0 && xcoordinate*blocksize <= x0+playersize){
                            System.out.println("Pureup");
                            pureup = true;
                        }
                        if (y0>y1 && ycoordinate == rely1 && (xcoordinate+1)*blocksize >= x1 && xcoordinate*blocksize <= x1+playersize){
                            System.out.println("Up");
                            up = true;
                        }

                        /*
                        We have a collision on the bottom when:
                        - our lower bound is lower than the block
                        - we moved down
                         */

                        if (y0<=y1
                                && xcoordinate*blocksize<=x0+playersize && (xcoordinate+1) * blocksize>= x0
                                && ycoordinate*blocksize<=y1+2*playersize && (ycoordinate+1)*blocksize>y1+playersize){
                            System.out.println("Puredown");
                            puredown = true;
                        }



                        if (y0<=y1
                                && xcoordinate*blocksize<=x1+playersize && (xcoordinate+1)* blocksize>= x1
                                && ycoordinate*blocksize<=y1+2*playersize
                                && (ycoordinate+1)*blocksize>y1+2*playersize){
                            System.out.println("Down");
                            down = true;
                        }
                        System.out.println("________");
                    }
                }
            }
        }
        boolean collisiontrue= false;
        if (left&&pureleft){
            if (player.isStanding()){
                player.setDx(0);
                player.setX(relx0*blocksize);
            } else{
                player.setDx(abs(player.getDx()/2));
            }
            collisiontrue = true;
        }
        if (right&&pureright){
            if (player.isStanding()){
                player.setDx(0);
                player.setX((relx1)*blocksize+blocksize-playersize);
            } else{
                player.setDx(abs(player.getDx()/2));
            }
            collisiontrue=true;
        }
        if (down&&puredown) {
            if (!player.isStanding())
                player.setY(rely0*blocksize+2*(blocksize-playersize));
            player.setDy(0);
            player.setStanding(true);
            System.out.println("B");
            collisiontrue = true;
        } else
            player.setStanding(false);
        if (up&&pureup) {
            player.setDy(abs(player.getC_mov().getDy()) / 2);
            System.out.println("T");
            collisiontrue=true;
        }
        if (!collisiontrue){
            if (left){
                if (player.isStanding()){
                    player.setDx(0);
                    player.setX(relx0*blocksize);
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
                if (!player.isStanding())
                    player.setY(relx1*blocksize-2*playersize);
                player.setDy(0);
                player.setStanding(true);
                System.out.println("B");
            } else
                player.setStanding(false);


            if (up) {
                player.setDy(abs(player.getC_mov().getDy()) / 2);
                System.out.println("T");
            }
        }

    }
    private void enemyCollisions(int realx, int relx, int realy, int rely){

    }

    //JUMP
    private void jump(boolean standing){
        if (standing) {
            player.setDy(-25);
            player.setStanding(false);
        }
    }


    //Helper functions
    private int realtoRel(float realcoordinate){
        return (int) (realcoordinate/af.getGctx().getSize());
    }

}
