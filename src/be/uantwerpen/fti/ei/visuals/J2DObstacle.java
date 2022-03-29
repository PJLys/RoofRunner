package be.uantwerpen.fti.ei.visuals;

import be.uantwerpen.fti.ei.GraphicsCTX;
import be.uantwerpen.fti.ei.game.AObstacle;

import javax.swing.*;
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
    private final Map<Integer, Integer> pos;

    public J2DObstacle(Map<Integer, Integer> pos, GraphicsCTX gctx){
        this.gctx = gctx;
        this.pos = pos;
    }

    @Override
    public void vis() {
        Graphics2D g2d = getGctx().getG2d();
        int blocksize = getGctx().getSize();
        g2d.setColor(new Color(0,0,255));
        for (Map.Entry<Integer, Integer> entry:getPos().entrySet()){

            g2d.fillRect(
                    entry.getKey()*blocksize,
                    entry.getValue()*blocksize,
                    blocksize,
                    blocksize
            );

            g2d.drawRect(
                    entry.getKey()*blocksize,
                    entry.getValue()*blocksize,
                    blocksize,
                    blocksize);
        }

    }
    public GraphicsCTX getGctx() {
        return this.gctx;
    }
    public Map<Integer, Integer> getPos() {
        return this.pos;
    }

}
