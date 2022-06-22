package be.uantwerpen.fti.ei.visuals.J2D;

import be.uantwerpen.fti.ei.game.AObstacle;
import be.uantwerpen.fti.ei.visuals.GraphicsCTX;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

/**
 * Visualizes the obstacles as orange blocks
 */
public class J2DObstacle extends AObstacle {

    private final GraphicsCTX gctx;
    /**
     * Blocks will be stored in a Mapping of an x coordinate with y array
     */
    private final Map<Integer, ArrayList<Integer>> pos;

    public J2DObstacle(Map<Integer, ArrayList<Integer>> pos, GraphicsCTX gctx){
        this.gctx = gctx;
        this.pos = pos;
    }
    public GraphicsCTX getGctx() {
        return this.gctx;
    }

    /**
     * Iterate through the Map and colour the blocks in orange
     * @param displacement
     */
    @Override
    public void vis(int displacement){
        Graphics2D g2d = gctx.getG2d();
        int blocksize = gctx.getSize();
        g2d.setColor(new Color(255, 153, 0));
        for (Map.Entry<Integer, ArrayList<Integer>> entry:getPos().entrySet()){
            Integer x = entry.getKey();
            ArrayList<Integer> ys = entry.getValue();
            for (int y : ys)
                g2d.fillRect(
                        x*blocksize-displacement,
                        y*blocksize,
                        blocksize,
                        blocksize
                );
        }

    }
    public Map<Integer, ArrayList<Integer>> getPos() {
        return this.pos;
    }

}
