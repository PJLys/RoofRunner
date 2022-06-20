package be.uantwerpen.fti.ei.visuals.city;

import be.uantwerpen.fti.ei.game.ACollectable;
import be.uantwerpen.fti.ei.visuals.GraphicsCTX;

import java.util.LinkedList;
import java.util.Map;

public class CityCollectables extends ACollectable {
    Map<Integer, LinkedList<Integer>> pos;
    GraphicsCTX gctx;

    public CityCollectables(Map<Integer, LinkedList<Integer>> pos, GraphicsCTX gctx){
        this.pos = pos;
        this.gctx = gctx;
    }

    @Override
    public void vis(int displacement) {

    }
    public Map<Integer, LinkedList<Integer>> getPos() {
        return this.pos;
    }

    public GraphicsCTX getGctx() {
        return this.gctx;
    }
}


