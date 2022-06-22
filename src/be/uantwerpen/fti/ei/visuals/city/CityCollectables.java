package be.uantwerpen.fti.ei.visuals.city;

import be.uantwerpen.fti.ei.game.ACollectable;
import be.uantwerpen.fti.ei.visuals.GraphicsCTX;

import java.awt.*;
import java.util.LinkedList;
import java.util.Map;

/**
 * Represents the Collectables with rubies
 */
public class CityCollectables extends ACollectable {
    Map<Integer, LinkedList<Integer>> pos;
    CityGCTX gctx;

    public CityCollectables(Map<Integer, LinkedList<Integer>> pos, GraphicsCTX gctx){
        this.pos = pos;
        this.gctx = (CityGCTX) gctx;
    }

    /**
     * Places a ruby on the right position of the frame
     * @param displacement
     */
    @Override
    public void vis(int displacement) {
        Graphics2D g2d = getGctx().getG2d();
        int blocksize = getGctx().getSize();
        for (Map.Entry<Integer, LinkedList<Integer>> entry:getPos().entrySet()){
            LinkedList<Integer> ys = entry.getValue();
            for (int y : ys) {
                g2d.drawImage(gctx.collectable_image,(entry.getKey()*blocksize)-displacement,y * blocksize,null);
            }
        }
    }
    public Map<Integer, LinkedList<Integer>> getPos() {
        return this.pos;
    }
    public GraphicsCTX getGctx() {
        return this.gctx;
    }
}
