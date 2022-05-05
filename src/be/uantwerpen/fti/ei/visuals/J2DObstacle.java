package be.uantwerpen.fti.ei.visuals;

import be.uantwerpen.fti.ei.game.AObstacle;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class J2DObstacle extends AObstacle {

    private final GraphicsCTX gctx;
    private final Map<Integer, ArrayList<Integer>> pos;

    public J2DObstacle(Map<Integer, ArrayList<Integer>> pos, GraphicsCTX gctx){
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
        for (Map.Entry<Integer, ArrayList<Integer>> entry:getPos().entrySet()){
            ArrayList<Integer> xs = entry.getValue();
            for (int x : xs)
                g2d.fillRect(
                        x*blocksize,
                        entry.getKey()*blocksize,
                        blocksize,
                        blocksize
                );
        }

    }
    public Map<Integer, ArrayList<Integer>> getPos() {
        return this.pos;
    }

}
