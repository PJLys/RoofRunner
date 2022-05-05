package be.uantwerpen.fti.ei.game;

import be.uantwerpen.fti.ei.visuals.J2DObstacle;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
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
    private static int score = 0;
    private int cellsX = 25;
    private int cellsY = 15;

    public Game(AFact af){
        this.af = af;
    }
    public void run(){
        af.getGctx().setGameDimensions(cellsX, cellsY);
        running = true;
        paused = false;
        build("src\\be\\uantwerpen\\fti\\ei\\buildfiles\\build1.bd");
        input = af.createInput();
        while(running){
            // INPUT

            boolean[] movement = input.getInput();
            // space will pause the game
            if (movement[0])
                paused = !paused;
            //horizontal
            if (movement[1] && !movement[2]) {
                player.setDx(-15);
                player.setLookingRight(false);
            }
            else
            {
                if (movement[2]) {
                    player.setDx(15);
                    player.setLookingRight(true);
                }
                else {
                    if (abs(player.getDx() * .6f) > .1)
                        player.setDx(player.getDx() * .6f);
                    else
                        player.getC_mov().setDx(0);
                }
            }
            if (movement[3])
                jump(player.isStanding());


            int x0 = (int) player.getC_mov().getX();
            int y0 = (int) player.getC_mov().getY();
            // VISUALISATION
            if (!paused) {
                player.setDy(min(player.getDy()+2,20));
                int x1 = (int) player.getDx()+x0;
                int y1 = (int) player.getDy()+y0;
                cd.detectCollisions(x0, x1, y0, y1);
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


    //JUMP
    private void jump(boolean standing){
        if (standing) {
            player.setDy(-25);
            player.setStanding(false);
        }
    }
    public static void incScore(){
        score++;
        System.out.println("Score: "+score);
    }

    //BUILD
    public void build(String bdfile) {
        try {
            //Convert the fileaddress to a file
            File buildfile = new File (bdfile);

            //create a scanner for the file
            Scanner read = new Scanner(buildfile);

            //Initiate datatypes for the create() functions
            //Enemy ==> create(x,y,d,t)
            ArrayList<Integer> enemyx = new ArrayList<>();
            ArrayList<Integer> enemyy = new ArrayList<>();
            ArrayList<Integer> enemyd = new ArrayList<>();
            ArrayList<Character> enemyt = new ArrayList<>();
            //Obstacle ==> create(map(y,xs))
            Map<Integer, ArrayList<Integer>> obstaclepos = new HashMap<>();
            ArrayList<Integer> obstaclex;
            //Collectable ==> create(map(y,ll(x)))
            Map<Integer, LinkedList<Integer>> collectablepos = new HashMap<>();
            LinkedList<Integer> collectablex;


            //init y_coordinate to 0
            int y_coordinate = 0;
            //Iterate through every line of the file
            while (read.hasNextLine()) {
                String line = read.nextLine();

                //Make an iterator for the line
                CharacterIterator charit = new StringCharacterIterator(line);

                //init x_coordinate to 0;
                int x_coordinate = 0;

                // reset x arrays
                obstaclex = new ArrayList<>();
                collectablex = new LinkedList<>();

                while (charit.current() != CharacterIterator.DONE) {
                    switch (charit.current()) {
                        case ('X') -> {
                            obstaclex.add(x_coordinate);
                        }
                        case ('|') -> {
                            enemyx.add(x_coordinate);
                            enemyy.add(y_coordinate);
                            enemyt.add('|');
                            enemyd.add(3);
                        }
                        case ('-') -> {
                            enemyx.add(x_coordinate);
                            enemyy.add(y_coordinate);
                            enemyt.add('-');
                            enemyd.add(3);
                        }
                        case ('C') -> {
                            collectablex.add(x_coordinate);
                        }
                        default -> System.out.println("Unknown type: " + charit.current());
                    }
                    charit.next();
                    x_coordinate++;
                }
                if (!obstaclex.isEmpty())
                    obstaclepos.put(y_coordinate, obstaclex);
                if (!collectablex.isEmpty())
                    collectablepos.put(y_coordinate, collectablex);
                y_coordinate++;
            }

            obstacle = af.createObstacle(obstaclepos);
            collectable = af.createCollectable(collectablepos);
            enemies = af.createEnemy(enemyx, enemyy, enemyd, enemyt);
            player = af.createPlayer(2*af.getGctx().getSize(), 2*af.getGctx().getSize(), 5);
            cd = af.createCD(af, player, collectable, obstacle);


        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
    }

}
