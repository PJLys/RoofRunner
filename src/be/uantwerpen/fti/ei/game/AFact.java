package be.uantwerpen.fti.ei.game;

import be.uantwerpen.fti.ei.Input;

public abstract class AFact {
    public abstract APlayer createPlayer(int x, int y);
    public abstract AEnemy createEnemy(int[] x, int[] y);
    public abstract AObstacle createObstacle(int[] x, int[] y);
    public abstract ACollectable createCollectable(int[] x, int[] y);
    public abstract Input createInput();
}
