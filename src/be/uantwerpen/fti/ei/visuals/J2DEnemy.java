package be.uantwerpen.fti.ei.visuals;

import be.uantwerpen.fti.ei.components.Cmovement;
import be.uantwerpen.fti.ei.game.AEnemy;

import java.util.LinkedList;

public class J2DEnemy extends AEnemy {
    private GraphicsCTX gctx;
    private LinkedList<Cmovement> c_mov;

    @Override
    public void vis() {

    }
    public void setC_move(LinkedList<Cmovement> c_move) {

    }

    @Override
    public LinkedList<Cmovement> getC_move() {
        return this.c_mov;
    }
}
