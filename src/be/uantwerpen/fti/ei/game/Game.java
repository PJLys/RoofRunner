package be.uantwerpen.fti.ei.game;

import be.uantwerpen.fti.ei.Input;

import java.util.*;

import static java.lang.Math.abs;

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
            2, new int[] {10,11},
            3, new int[] {10,11},
            4, new int[] {10,11}
    ));

    private final Map<Integer, LinkedList<Integer>> collectables = new HashMap<>(Map.of(
            2, new LinkedList<Integer>(List.of(9)), 5, new LinkedList<Integer>(List.of(8))
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
                        case UP -> player.getC_mov().setDy(-25);
                        case LEFT -> player.getC_mov().setDx(-15);
                        case RIGHT -> player.getC_mov().setDx(15);
                        }
                    }
                }
            else {
                player.getC_mov().setDy(player.getC_mov().getDy()+2);
                if (abs(player.getC_mov().getDx()*.9f)>.1)
                    player.getC_mov().setDx(player.getC_mov().getDx()*.9f);
                else
                    player.getC_mov().setDx(0);
            }
            // VISUALISATION
            if (!paused) {
                obstacle.vis();
                collectable.vis();
                player.vis();
                int[] positions = player.update();
                detectCollisions(positions);
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
        this.player = af.createPlayer(10,6,5);
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
        //obstacleCollisions(x_coordinate, relx, y_coordinate, rely);
        //enemyCollisions(x_coordinate, relx, y_coordinate, rely);
    }
    private void collectableCollisions(int x0, int x1, int y0, int y1){
        // The collectablecollision has to delete a collectable and update the score.

        // if we moved a block
        if (x0!=x1 || y0!=y1){
            System.out.println("We moved a block");
            // check if there are any collectables at this or the following vertical line
            for (Integer xcoordinate=x1; xcoordinate<=x1+1; xcoordinate++) {
                if (collectables.containsKey(xcoordinate)) {
                    System.out.println("There is a collectable on this line");
                    LinkedList<Integer> ycoords = collectables.get(xcoordinate);
                    // check if we hit any blocks with our head, body or feet
                    for (Integer ycoordinate : ycoords) {
                        // if we have a hit, remove the corresponding block
                        if (ycoordinate == y1 || ycoordinate == y1 + 1 || ycoordinate == y1 + 2) {
                            ycoords.remove(ycoordinate);
                            if (ycoords.isEmpty()){
                                collectables.remove(xcoordinate);
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
        // check for collisions if we moved horizontally
        if (x0!=x1){
            System.out.println("We moved horizontally");
            // Is there a collectable with this x coordinate?
            if (collectables.containsKey(x1)) {
                // get the y_coordinates of every collectable at x
                LinkedList<Integer> y_coordinates = collectables.get(x1);
                // use streams to check if there is an overlap
                // if there's an overlap, set a collision flag
            }
        }
        if (y0!=y1){
            System.out.println("We moved vertically");
            // Is there a collectable with this x coordinate?
            if (collectables.containsKey(x1)) {
                // get the y_coordinates of every collectable at x
                LinkedList<Integer> y_coordinates = collectables.get(x1);
                // use streams to check if there is an overlap
                // if there's an overlap, remove block and
            }
        }

    }
    private void enemyCollisions(int realx, int relx, int realy, int rely){

    }
}
