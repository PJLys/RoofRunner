package be.uantwerpen.fti.ei.game;
import be.uantwerpen.fti.ei.components.Cmovement;
import java.util.LinkedList;

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
    public abstract void fire(int playerx, int playery, boolean direction, float framerate);
    public abstract void update();
}
