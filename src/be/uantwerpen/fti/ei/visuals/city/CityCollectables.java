package be.uantwerpen.fti.ei.visuals.city;

import be.uantwerpen.fti.ei.game.ACollectable;
import be.uantwerpen.fti.ei.game.Helperfunctions;
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
        int relative_displacement = Helperfunctions.realtoRel(displacement,gctx.getSize());
        Graphics2D g2d = getGctx().getG2d();
        int blocksize = getGctx().getSize();
        for (int x=relative_displacement;x<relative_displacement+22;x++){
            try {
                LinkedList<Integer> ys = pos.get(x);
                for (int y : ys) {
                    g2d.drawImage(gctx.collectable_image,(x*blocksize)-displacement,y * blocksize,null);
                }
            } catch (NullPointerException ignored){}
        }
    }
    public Map<Integer, LinkedList<Integer>> getPos() {
        return this.pos;
    }
    public GraphicsCTX getGctx() {
        return this.gctx;
    }
}
