package be.uantwerpen.fti.ei.visuals.city;

import be.uantwerpen.fti.ei.game.AObstacle;
import be.uantwerpen.fti.ei.visuals.GraphicsCTX;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

/**
 * Implementation of the obstacle class for an image based theme (concrete blocks)
 */
public class CityObstacles extends AObstacle {
    private CityGCTX gctx;
    private final Map<Integer, ArrayList<Integer>> pos;

    public CityObstacles(Map<Integer,ArrayList<Integer>> pos, GraphicsCTX gctx){
        this.gctx= (CityGCTX) gctx;
        this.pos=pos;
    }

    /**
     * Places the obstacle image on the right position
     * @param displacement
     */
    @Override
    public void vis(int displacement) {
        Graphics2D g2d = gctx.getG2d();
        int blocksize = gctx.getSize();
        for (Map.Entry<Integer, ArrayList<Integer>> entry:getPos().entrySet()){
            Integer x = entry.getKey();
            ArrayList<Integer> ys = entry.getValue();
            for (int y : ys)
                g2d.drawImage(gctx.obstacle_image,x*blocksize-displacement, y*blocksize,null);
        }

    }

    @Override
    public Map<Integer, ArrayList<Integer>> getPos() {
        return this.pos;
    }
}
