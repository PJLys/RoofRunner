package be.uantwerpen.fti.ei.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.time.Instant;
import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;

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
    private ABullet bullet;
    private CollisionDetection cd;
    private static int score = 0;
    private int cellsX = 20;
    private int cellsY = 12;
    private int initial_framerate = 500;
    private double framerate = initial_framerate;
    private boolean shoot_enable = true;
    private Instant prev_instance;
    short lvl = 1;
    private boolean newframe = false;
    private Timer timer = new Timer();
    private TimerTask task = new TimerTask() {
        /**
         * The action to be performed by this timer task.
         */
        @Override
        public void run() {
            newframe = true;
        }
    };


    /**
     * Game constructor, with only a factory as parameter
     * @param af
     */
    public Game(AFact af){
        this.af = af;
    }

    /**
     * Runs the game by creating timer interrupts for framerate and setting the dimensions
     * Loops:
     *      Input sequence -> set Dx and Dy
     *      Gameloop:
     *          Collision detection
     *          Position update
     *          Visualisation
     *
     * @throws IOException
     */
    public void run() throws IOException {
        System.out.println("Timer period: "+ 1e3/initial_framerate);
        timer.scheduleAtFixedRate(task, 0, (long) (1e3/framerate));
        af.getGctx().setGameDimensions(cellsX, cellsY);
        running = true;
        paused = false;
        input = af.createInput();
        build("src\\be\\uantwerpen\\fti\\ei\\buildfiles\\build"+lvl+".bd", initial_framerate);
        prev_instance = Instant.now();
        while(running){
            //System.out.println(Duration.between(prev_instance,Instant.now()).getSeconds());
            // INPUT
            boolean[] movement = input.getInput();
            //prev_instance = Instant.now();

            // space will pause the game
            if (movement[0])
                paused = !paused;
            //horizontal
            if (movement[1] && !movement[2]) {
                player.setDx((float) (-500.0/framerate));
                player.setLookingRight(false);
            }
            else
            {
                if (movement[2]) {
                    player.setDx((float) (500.0/framerate));
                    player.setLookingRight(true);
                }
                else {
                    if (abs(player.getDx() * .6f) > .1/framerate)
                        player.setDx(player.getDx() * .6f);
                    else
                        player.getC_mov().setDx(0);
                }
            }

            if (movement[3])
                jump(player.isStanding());

            if (movement[4] & shoot_enable){
                shoot();
                shoot_enable = false;
            }

            if (!movement[4]){
                shoot_enable=true;
            }

            int x0 = (int) player.getC_mov().getX();
            int y0 = (int) player.getC_mov().getY();

            // Game loop
            if (!paused && newframe) {
                newframe = false;
                player.setDy((float) min(player.getDy()+10000.0/(framerate*framerate),800.0/framerate)); //falling

                //System.out.println("CD");
                int x1 = (int) player.getDx()+x0;
                int y1 = (int) player.getDy()+y0;
                cd.detectCollisions(x0, x1, y0, y1);
                //System.out.println("UD");
                player.update();
                enemies.update();
                bullet.update();
                //System.out.println("Vis");
                int displacement = (int) player.getC_mov().getX() - 4 * af.getGctx().getSize();
                try {
                    obstacle.vis(displacement);
                    collectable.vis(displacement);
                    enemies.vis(displacement);
                    bullet.vis(displacement);
                    player.vis();
                } catch (NullPointerException e){
                    System.out.println(e.getMessage());
                }

                af.getGctx().render();
                if (y1>1000) {
                    System.out.println(y1);
                    running = !running;
                }
                if (player.getLives()==0)
                    running = false;
                if (player.getC_mov().getX()>7500){
                    lvl = (short) (1+floorMod(lvl,3));
                    build("src\\be\\uantwerpen\\fti\\ei\\buildfiles\\build"+lvl+".bd", (float) framerate);
                }
            }
        }
        System.exit(0);
    }


    //GAME Specific functions

    /**
     * Specific funtion that will be called when the jump key is pressed
     * Jump when not standing
     * @param standing
     */
    private void jump(boolean standing){
        if (standing) {
            player.setDy(((float)(-2000/framerate))); //vertical accelleration
            player.setStanding(false);
        }
    }

    /**
     * Function that can be used by collision detection in order to increase the score
     */
    public static void incScore(){
        score++;
        System.out.println("Score: "+score);
    }

    /**
     * Creates a bullet with a certain direction (max 2 at once)
     */
    public void shoot(){
        int playerx = (int) player.getC_mov().getX();
        int playery = (int) player.getC_mov().getY();
        bullet.fire(playerx,playery,player.isLookingRight(), (float) framerate);
    }

    //BUILD

    /**
     * Reads a file into arrays, change into more useful data types, create game objects
     * @param bdfile
     * @param framerate
     */
    public void build(String bdfile, float framerate) {
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
            ArrayList<Integer> obstacley = new ArrayList<>();
            ArrayList<Integer> obstaclex = new ArrayList<>();
            //Collectable ==> create(map(y,ll(x)))
            Map<Integer, LinkedList<Integer>> collectablepos = new HashMap<>();
            ArrayList<Integer> collectabley = new ArrayList<>();
            ArrayList<Integer> collectablex = new ArrayList<>();


            //init y_coordinate to 0
            int y_coordinate = 0;
            //Iterate through every line of the file and add to an array
            while (read.hasNextLine()) {
                String line = read.nextLine();

                //Make an iterator for the line
                CharacterIterator charit = new StringCharacterIterator(line);

                //init x_coordinate to 0;
                int x_coordinate = 0;


                while (charit.current() != CharacterIterator.DONE) {
                    switch (charit.current()) {
                        case ('X') -> {
                            obstaclex.add(x_coordinate);
                            obstacley.add(y_coordinate);
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
                            collectabley.add(y_coordinate);
                        }
                        case ('.') -> {}
                        default -> System.out.println("Unknown type: " + charit.current());
                    }
                    charit.next();
                    x_coordinate++;
                }
                y_coordinate++;
            }
            //Resize the array to more functional datatypes for the objects
            //OBSTACLES
            for (int i=0; i<obstacley.size();i++){
                Integer x_coord = obstaclex.get(i);
                Integer y_coord = obstacley.get(i);
                if (!obstaclepos.containsKey(x_coord)){
                    ArrayList<Integer> newYlist = new ArrayList<>();
                    newYlist.add(y_coord);
                    obstaclepos.put(x_coord, newYlist);
                } else{
                    obstaclepos.get(x_coord).add(y_coord); //GEEFT MISSCHIEN FOUT
                }
            }
            //COLLECTABLES
            for (int i=0; i<collectabley.size(); i++){
                Integer x_coord = collectablex.get(i);
                Integer y_coord = collectabley.get(i);
                if (!collectablepos.containsKey(x_coord)){
                    LinkedList<Integer> ylist = new LinkedList<>();
                    ylist.add(y_coord);
                    collectablepos.put(x_coord, ylist);
                } else
                    collectablepos.get(x_coord).add(y_coord);
            }

            obstacle = af.createObstacle(obstaclepos);
            collectable = af.createCollectable(collectablepos);
            enemies = af.createEnemy(enemyx, enemyy, enemyd, enemyt, framerate);
            player = af.createPlayer(4*af.getGctx().getSize(), 4*af.getGctx().getSize(), 5);
            bullet = af.createBullet();
            cd = af.createCD(af, player, collectable, obstacle, enemies, bullet, framerate);

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
    }

}
