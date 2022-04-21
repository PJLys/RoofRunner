package be.uantwerpen.fti.ei.visuals;

import be.uantwerpen.fti.ei.game.AObstacle;

import java.awt.*;
import java.util.Map;

/**
 * This is the graphical representation of the obstacle
 * current problems:
 *      - inheritance of the constructor
 *      - placing of the blocks
 */

public class J2DObstacle extends AObstacle {

    private final GraphicsCTX gctx;
    private final Map<Integer, int[]> pos;

    public J2DObstacle(Map<Integer, int[]> pos, GraphicsCTX gctx){
        this.gctx = gctx;
        this.pos = pos;
    }
    public GraphicsCTX getGctx() {
        return this.gctx;
    }

    @Override
    public void vis() {
        Graphics2D g2d = getGctx().getG2d();
        int blocksize = getGctx().getSize();
        g2d.setColor(new Color(0,0,255));
        for (Map.Entry<Integer, int[]> entry:getPos().entrySet()){
            int[] ys = entry.getValue();
            for (int y : ys){
                g2d.fillRect(
                        entry.getKey()*blocksize,
                        y*blocksize,
                        blocksize,
                        blocksize
                );
            }



        }

    }
    public Map<Integer, int[]> getPos() {
        return this.pos;
    }

}
