package be.uantwerpen.fti.ei.visuals;

import be.uantwerpen.fti.ei.GraphicsCTX;
import be.uantwerpen.fti.ei.game.ACollectable;

import java.awt.*;
import java.util.LinkedList;
import java.util.Map;

public class J2DCollectable extends ACollectable {

    Map<Integer,LinkedList<Integer>> pos;
    GraphicsCTX gctx;

    public J2DCollectable(Map<Integer, LinkedList<Integer>> pos, GraphicsCTX gctx){
        this.pos = pos;
        this.gctx = gctx;
    }

    @Override
    public void vis() {
        Graphics2D g2d = getGctx().getG2d();
        int blocksize = getGctx().getSize();
        g2d.setColor(new Color(255,0,0));
        for (Map.Entry<Integer, LinkedList<Integer>> entry:getPos().entrySet()){
            LinkedList<Integer> ys = entry.getValue();
            for (int y : ys) {
                g2d.fillRect(
                        entry.getKey() * blocksize,
                        y * blocksize,
                        blocksize,
                        blocksize
                );
            }
        }
    }
    public GraphicsCTX getGctx() {
        return this.gctx;
    }
    public Map<Integer, LinkedList<Integer>> getPos() {
        return this.pos;
    }
}
