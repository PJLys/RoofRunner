package be.uantwerpen.fti.ei.components;


public class Cmovement {
    private float x;
    private float y;
    private float dx;
    private float dy;
    private enum Collisions {CTOP, CBOTTOM, CLEFT, CRIGHT}
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
    public void setDx(float dx){
        this.dx = dx;
    }
    public void setDy(float dy) {
        this.dy = dy;
    }
    public void update(){
        this.x += dx;
        this.y += dy;
    }
    public float getDy() {
        return this.dy;
    }
    public float getDx() {
        return this.dx;
    }
}
