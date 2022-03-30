package be.uantwerpen.fti.ei.game;

import be.uantwerpen.fti.ei.components.Cmovement;
import jdk.jfr.BooleanFlag;

public abstract class APlayer {
    public abstract void vis();
    public abstract void setC_mov(Cmovement c_mov);
    public abstract Cmovement getC_mov();
    @BooleanFlag
    public abstract boolean getLowerFlag();
    public abstract boolean getLeftFlag();
    public abstract boolean getRightFlag();
    public abstract boolean getUpperFlag();
    public abstract void setLowerFlag(boolean b);
    public abstract void setLeftFlag(boolean b);
    public abstract void setRightFlag(boolean b);
    public abstract void setUpperFlag(boolean b);
    public abstract void resetflags();

    public abstract int[] update();
}
