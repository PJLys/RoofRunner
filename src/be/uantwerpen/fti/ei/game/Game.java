package be.uantwerpen.fti.ei.game;

import be.uantwerpen.fti.ei.Input;

import java.util.*;

import static java.lang.Math.abs;
import static java.lang.Math.round;

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
            1, new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
            2, new int[] {8},
            3, new int[] {8},
            4, new int[] {8},
            5, new int[] {8},
            6, new int[] {8}
    ));

    private final Map<Integer, LinkedList<Integer>> collectables = new HashMap<>(Map.of(
            2, new LinkedList<Integer>(List.of(8,9)), 5, new LinkedList<Integer>(List.of(8))
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
                if (abs(player.getC_mov().getDx()*.9f)>.1)
                    player.setDx(player.getDx()*.9f);
                else
                    player.getC_mov().setDx(0);
            }

            int x0 = (int) player.getC_mov().getX();
            int y0 = (int) player.getC_mov().getY();
            int x1 = (int) player.getC_mov().getDx()+x0;
            int y1 = (int) player.getC_mov().getDy()+y0;
            detectCollisions(new int[]{x0, y0, x1, y1});

            // VISUALISATION
            if (!paused) {
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
        this.player = af.createPlayer(3,4,5);
    }

    //COLLISIONS
    private void detectCollisions(int[] positions){
        //Firstly, i will import the x and y coordinates of the player
        int x0 = positions[0];
        int y0 = positions[1];
        int x1 = positions[2];
        int y1 = positions[3];
        //To use minimal collision detections, I will cast the player coordinates to the object coordinates
        int relx0 = (int) (x0/af.getGctx().getSize());
        int rely0 = (int) (y0/af.getGctx().getSize());
        int relx1 = (int) (x1/af.getGctx().getSize());
        int rely1 = (int) (y1/af.getGctx().getSize());
        //Collision detection can start, for this I use separate functions to keep everything clean.
        collectableCollisions(relx0, relx1, rely0, rely1);
        obstacleCollisions(relx0, relx1, rely0, rely1);
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
    private void obstacleCollisions(int x0, int x1, int y0, int y1){
        if (y0!=y1){
            // Is there a collectable with this x coordinate?
            for (int xcoordinate = x1; xcoordinate<=x1+1; xcoordinate++){
                if (this.obstacle.getPos().containsKey(xcoordinate)) {
                    // get the y_coordinates of every collectable at x
                    int[] y_coordinates = this.obstacle.getPos().get(x1);
                    if (y_coordinates != null) {
                        for (int ycoordinate : y_coordinates) {
                            if (ycoordinate == y1) {
                                player.setDy(abs(player.getC_mov().getDy())/2);
                                System.out.println("Collision top");
                            }
                            if (ycoordinate==y1+2){
                                player.setY((y1)* af.getGctx().getSize());
                                player.setDy(0);
                                player.setStanding(true);
                                System.out.println("Collision bottom");
                            }
                        }
                        return;
                    }

                }
            }
        }
        // check for collisions if we moved horizontally
        if (x0!=x1){
            // Is there a collectable with this x coordinate?
            if (this.obstacle.getPos().containsKey(x1)) {
                int[] y_coordinates = this.obstacle.getPos().get(x1);
                for (int ycoordinate : y_coordinates) {
                    // if we have a hit, set a collision flag
                    if (ycoordinate == y1 || ycoordinate == y1 + 1 || ycoordinate == y1 + 2) {
                        if (x0<x1){
                            player.setDx(-abs(player.getC_mov().getDx()));
                            System.out.println("Collision right");
                        } else {
                            player.setDx(abs(player.getC_mov().getDx()));
                            System.out.println("Collision left");
                        }
                        return;
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
            player.setStanding(false);
        }
        else
            player.setDy(player.getDy()+2);
    }

}
