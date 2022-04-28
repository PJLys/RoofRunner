package be.uantwerpen.fti.ei.game;

import java.util.*;

import static java.lang.Math.*;

public class Game {
    private boolean running;
    private boolean paused;
    private final AFact af;
    private AObstacle obstacle;
    private ACollectable collectable;
    private AEnemy enemies;
    private APlayer player;
    private Input input;
    private CollisionDetection cd;
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
                player.setDy(player.getDy()+2);
                int x1 = (int) player.getDx()+x0;
                int y1 = (int) player.getDy()+y0;
                cd.detectCollisions(x0, x1, y0, y1);
                //if (jumpflag)
                //    jump(player.isStanding());
                player.update();
                enemies.update();
                obstacle.vis();
                collectable.vis();
                enemies.vis();
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
        this.enemies = af.createEnemy(new int[] {5,7}, new int[] {8,6}, new int[] {4,2}, new char[] {'|', '='});
        this.player = af.createPlayer(54*3f,54*5f,5);
        this.cd = af.createCD(af,player,collectable,obstacle);
    }



    //JUMP
    private void jump(boolean standing){
        if (standing) {
            player.setDy(-25);
            player.setStanding(false);
        }
    }


}
