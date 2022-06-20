package be.uantwerpen.fti.ei.visuals.city;

import be.uantwerpen.fti.ei.game.AObstacle;
import be.uantwerpen.fti.ei.visuals.GraphicsCTX;

import java.util.ArrayList;
import java.util.Map;

public class CityObstacles extends AObstacle {
    private final GraphicsCTX gctx;
    private final Map<Integer, ArrayList<Integer>> pos;

    public CityObstacles(Map<Integer,ArrayList<Integer>> pos, GraphicsCTX gctx){
        this.gctx=gctx;
        this.pos=pos;
    }

    @Override
    public void vis(int displacement) {

    }

    @Override
    public Map<Integer, ArrayList<Integer>> getPos() {
        return this.pos;
    }
}
