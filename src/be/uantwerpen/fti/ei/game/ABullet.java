package be.uantwerpen.fti.ei.game;
import be.uantwerpen.fti.ei.components.Cmovement;
import java.util.LinkedList;

/**
 * Defines the tasks of the bullet: firing, position update and visualization
 */
public abstract class ABullet extends AEntity {
    public abstract void vis(int displacement);
    /**
     * Bullet data type
     * Linked list
     * - Easy add/remove
     * - No iteration problems when out of order remove (instead of FIFO)
     * @return
     */
    public abstract LinkedList<Cmovement> getCmov();

    /**
     * Fires a bullet with a certain direction from a certain position with a framerate dependant Dx
     * @param playerx
     * @param playery
     * @param direction
     * @param framerate
     */
    public abstract void fire(int playerx, int playery, boolean direction, float framerate);
    public abstract void update();
}
