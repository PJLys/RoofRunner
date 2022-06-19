package be.uantwerpen.fti.ei.game;

import be.uantwerpen.fti.ei.visuals.J2DObstacle;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.time.Instant;
import java.time.Duration;
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
    private ABullet bullet;
    private CollisionDetection cd;
    private static int score = 0;
    private int cellsX = 20;
    private int cellsY = 12;
    private float framerate = 100;
    private boolean shoot_enable = true;


    public Game(AFact af){
        this.af = af;
    }
    public void run(){
        af.getGctx().setGameDimensions(cellsX, cellsY);
        running = true;
        paused = false;
        input = af.createInput();
        build("src\\be\\uantwerpen\\fti\\ei\\buildfiles\\build1.bd", framerate);
        while(running){
            // INPUT
            Instant start = Instant.now();
            boolean[] movement = input.getInput();
            // space will pause the game
            if (movement[0])
                paused = !paused;
            //horizontal
            if (movement[1] && !movement[2]) {
                player.setDx(-750/framerate);
                player.setLookingRight(false);
            }
            else
            {
                if (movement[2]) {
                    player.setDx(750/framerate);
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
            if (!paused) {
                player.setDy(min(player.getDy()+12000/(framerate*framerate),1000/framerate)); //falling
                int x1 = (int) player.getDx()+x0;
                int y1 = (int) player.getDy()+y0;
                cd.detectCollisions(x0, x1, y0, y1);

                player.update();
                enemies.update();
                bullet.update();

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
            }

            // SLEEP
            try{
                Instant stop = Instant.now();
                Duration time_elapsed = Duration.between(start,stop);

                double technicalsleep_ns = (1E9/framerate);
                //System.out.println("Technical sleep (ns): "+technicalsleep_ns);
                double realsleep_ns = technicalsleep_ns-time_elapsed.toNanos();
                //System.out.println("Real sleep (ns): "+realsleep_ns);
                long roundedsleep_ms = (long) (realsleep_ns*1E-6);
                //System.out.println("Rounded sleep (ms): "+roundedsleep_ms);
                int roundedsleep_ns = (int) (realsleep_ns-roundedsleep_ms*1E6);
                //System.out.println("Rounded sleep (ns): "+roundedsleep_ns);


                Thread.sleep(max(roundedsleep_ms,0), max(roundedsleep_ns,0));

            } catch (InterruptedException e){
                System.out.println(Arrays.toString(e.getStackTrace()));
            }
        }
        System.exit(0);
    }


    //GAME Specific functions
    private void jump(boolean standing){
        if (standing) {
            player.setDy(-20*100/framerate); //vertical accelleration
            player.setStanding(false);
        }
    }
    public static void incScore(){
        score++;
        System.out.println("Score: "+score);
    }
    public void shoot(){
        System.out.println("Pew!");
        int playerx = (int) player.getC_mov().getX();
        int playery = (int) player.getC_mov().getY();
        bullet.fire(playerx,playery,player.isLookingRight(),framerate);
    }

    //BUILD
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
                };
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
