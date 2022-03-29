package be.uantwerpen.fti.ei.visuals;

import be.uantwerpen.fti.ei.GraphicsCTX;
import be.uantwerpen.fti.ei.game.ACollectable;

import java.awt.*;
import java.util.Map;

public class J2DCollectable extends ACollectable {

    Map<Integer,Integer> pos;
    GraphicsCTX gctx;

    public J2DCollectable(Map<Integer, Integer> pos, GraphicsCTX gctx){
        this.pos = pos;
        this.gctx = gctx;
    }

    @Override
    public void vis() {
        Graphics2D g2d = getGctx().getG2d();
        int blocksize = getGctx().getSize();
        g2d.setColor(new Color(255,0,0));
        for (Map.Entry<Integer, Integer> entry:getPos().entrySet()){
            g2d.fillRect(entry.getKey()*blocksize, entry.getValue()*blocksize, blocksize, blocksize);
        }
    }
    public GraphicsCTX getGctx() {
        return this.gctx;
    }
    public Map<Integer, Integer> getPos() {
        return this.pos;
    }
}
