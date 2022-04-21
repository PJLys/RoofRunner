package be.uantwerpen.fti.ei.game;

import java.util.Map;

public abstract class AObstacle extends AEntity {

    @Override
    public abstract void vis();
    public abstract Map<Integer, int[]> getPos();
}
