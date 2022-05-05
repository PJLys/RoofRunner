package be.uantwerpen.fti.ei.game;

import java.util.ArrayList;
import java.util.Map;

public abstract class AObstacle extends AEntity {

    @Override
    public abstract void vis(int displacement);
    public abstract Map<Integer, ArrayList<Integer>> getPos();
}
