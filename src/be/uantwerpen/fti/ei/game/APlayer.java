package be.uantwerpen.fti.ei.game;

import be.uantwerpen.fti.ei.GraphicsCTX;
import be.uantwerpen.fti.ei.components.Cmovement;

public abstract class APlayer {
    private Cmovement c_mov;
    private int lives;
    public abstract void vis();
    public abstract void setC_mov(Cmovement c_mov);
    public abstract Cmovement getC_mov();
}
