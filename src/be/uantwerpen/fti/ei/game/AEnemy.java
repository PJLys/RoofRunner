package be.uantwerpen.fti.ei.game;
import be.uantwerpen.fti.ei.components.Cmovement;
import java.util.LinkedList;
public abstract class AEnemy {
    private LinkedList<Cmovement> c_move; //handier for deleting elements
    public abstract void vis();
    public abstract void setC_move(LinkedList<Cmovement> c_move);
    public abstract LinkedList<Cmovement> getC_move();
}
