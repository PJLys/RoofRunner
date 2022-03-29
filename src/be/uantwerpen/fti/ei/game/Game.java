package be.uantwerpen.fti.ei.game;

import be.uantwerpen.fti.ei.Input;
import be.uantwerpen.fti.ei.visuals.J2DCollectable;
import be.uantwerpen.fti.ei.visuals.J2DObstacle;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Game {
    private boolean running;
    private boolean paused;
    private final AFact af;
    private AObstacle obstacle;
    private ACollectable collectable;
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
        this.input = new Input(af.getGctx());
        this.obstacle = new J2DObstacle(blocks, af.getGctx());
        this.collectable = new J2DCollectable(collectables, af.getGctx());
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
                    System.out.println("movementupdater");
                }
            }
            if (!paused) {
                obstacle.vis();
                collectable.vis();
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
        af.createObstacle(blocks, this.af.getGctx());
    }
}
