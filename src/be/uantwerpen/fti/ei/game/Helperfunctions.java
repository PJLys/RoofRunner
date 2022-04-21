package be.uantwerpen.fti.ei.game;

public class Helperfunctions {
    public static int realtoRel(float realcoordinate, int blocksize){
        return (int) (realcoordinate/blocksize);
    }
}
