package be.uantwerpen.fti.ei.game;

public class Helperfunctions {
    /**
     * Turns a real coordinate used by graphics into a
     * relative coordinate with base blocksize used for iteration and data storage
     * @param realcoordinate
     * @param blocksize
     * @return
     */
    public static int realtoRel(float realcoordinate, int blocksize){
        return (int) (realcoordinate/blocksize);
    }
}
