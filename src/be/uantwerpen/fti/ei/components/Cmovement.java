package be.uantwerpen.fti.ei.components;


public class Cmovement {
    private float x;
    private float y;
    private float dx;
    private float dy;
    private boolean[] collisionflags;
    public Cmovement(float x, float y){
        this.x = x;
        this.y = y;
        this.dx = 0;
        this.dy = 0;
    }

    public float getX(){
        return this.x;
    }

    public float getY(){
        return this.y;
    }
}
