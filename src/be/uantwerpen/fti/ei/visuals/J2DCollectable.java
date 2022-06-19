package be.uantwerpen.fti.ei.visuals;

import be.uantwerpen.fti.ei.game.ACollectable;

import java.awt.*;
import java.util.LinkedList;
import java.util.Map;

public class J2DCollectable extends ACollectable {
    /***
     * The collectable Data Type:
     * Mapping between Map(X,Y[]) with Y being a linked list which performs better with deletion of elements.
     */
    Map<Integer,LinkedList<Integer>> pos;
    GraphicsCTX gctx;

    public J2DCollectable(Map<Integer, LinkedList<Integer>> pos, GraphicsCTX gctx){
        this.pos = pos;
        this.gctx = gctx;
    }

    @Override
    public void vis(int displacement) {
        Graphics2D g2d = getGctx().getG2d();
        int blocksize = getGctx().getSize();
        g2d.setColor(new Color(255,0,0));
        for (Map.Entry<Integer, LinkedList<Integer>> entry:getPos().entrySet()){
            LinkedList<Integer> ys = entry.getValue();
            for (int y : ys) {
                g2d.fillRect(
                        (entry.getKey()*blocksize)-displacement,
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
