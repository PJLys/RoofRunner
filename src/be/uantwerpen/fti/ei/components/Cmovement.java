package be.uantwerpen.fti.ei.components;


public class Cmovement {
    private float x;
    private float y;
    private float dx;
    private float dy;
    private boolean[] collisions = {false,false,false,false};
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

    public boolean getLowFlag(){
        return collisions[0];
    }
    public boolean getUpFlag() {
        return collisions[1];
    }
    public boolean getLeftFlag(){
        return collisions[2];
    }
    public boolean getRightFlag(){
        return collisions[3];
    }

    public void setLowFlag(boolean b){
        collisions[0] = b;
    }
    public void setUpFlag(boolean b) {
        collisions[1] = b;
    }
    public void setLeftFlag(boolean b) {
        collisions[2] = b;
    }
    public void setRightFlag(boolean b) {
        collisions[3] = b;
    }
}
