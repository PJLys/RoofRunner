package be.uantwerpen.fti.ei.game;

import be.uantwerpen.fti.ei.components.Cmovement;
import jdk.jfr.BooleanFlag;

public abstract class APlayer {
    public abstract void vis();
    public abstract void setC_mov(Cmovement c_mov);
    public abstract Cmovement getC_mov();

    public abstract void update();
    public abstract void setX(float newx);
    public abstract void setY(float newy);
    public abstract void setDx(float newdx);
    public abstract void setDy(float newdy);
    public abstract float getDy();
    public abstract float getDx();
    public abstract boolean isStanding();
    public abstract void setStanding(boolean b);
}
