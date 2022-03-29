package be.uantwerpen.fti.ei.game;

import be.uantwerpen.fti.ei.Input;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.abs;

public class Game {
    private boolean running;
    private boolean paused;
    private final AFact af;
    private AObstacle obstacle;
    private ACollectable collectable;
    private APlayer player;
    private Input input;
    private int cellsX = 25;
    private int cellsY = 15;


    // TESTING if the draw function works
    private final Map<Integer, int[]> blocks = new HashMap<>(Map.of(
            1, new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
            2, new int[] {10,11},
            3, new int[] {10,11},
            4, new int[] {10,11}
    ));

    private final Map<Integer, int[]> collectables = new HashMap<>(Map.of(
            2, new int[] {9}, 5, new int[] {8}
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
            if (!paused) {
                obstacle.vis();
                collectable.vis();
                player.vis();
                player.update();
                af.getGctx().render();
            }
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
}
