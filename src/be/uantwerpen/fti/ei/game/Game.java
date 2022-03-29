package be.uantwerpen.fti.ei.game;

import be.uantwerpen.fti.ei.Input;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
    private final Map<Integer, Integer> blocks = new HashMap<>(Map.of(
            1,12,2,12,3,12,4,12,5,12
    ));

    private final Map<Integer, Integer> collectables = new HashMap<>(Map.of(
            2,10,4,11
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
                if (movement == Input.Inputs.SPACE)
                    paused = !paused;
                else {
                    switch (movement) {
                        case UP -> player.getC_mov().setDy(-5);
                        case LEFT -> player.getC_mov().setDx(-5);
                        case RIGHT -> player.getC_mov().setDx(5);
                        }
                    }
                }
            else {
                player.getC_mov().setDy(0);
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
