package be.uantwerpen.fti.ei.visuals.city;

import be.uantwerpen.fti.ei.game.AObstacle;
import be.uantwerpen.fti.ei.game.Helperfunctions;
import be.uantwerpen.fti.ei.visuals.GraphicsCTX;
import be.uantwerpen.fti.ei.game.Helperfunctions.*;

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
        int relative_displacement= Helperfunctions.realtoRel(displacement,blocksize);
        for (int x=relative_displacement;x<relative_displacement+22;x++){
            try {
                ArrayList<Integer> ys = this.pos.get(x);
                for (int y : ys)
                    g2d.drawImage(gctx.obstacle_image,x*blocksize-displacement, y*blocksize,null);
            } catch (NullPointerException ignored){}
        }

    }

    @Override
    public Map<Integer, ArrayList<Integer>> getPos() {
        return this.pos;
    }
}
