package be.uantwerpen.fti.ei.game;
import be.uantwerpen.fti.ei.visuals.GraphicsCTX;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Input {
    public enum Inputs {LEFT, RIGHT, UP, SPACE};
    private boolean key_event = false;
    private final boolean[] inflags = new boolean[5];

    public Input(GraphicsCTX gctx) {
        gctx.getFrame().addKeyListener(new KeyInputAdapter());
    }
    public boolean inputAvailable(){
        return key_event;
    }
    public boolean[] getInput() {
        return inflags;
    }

    class KeyInputAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            key_event = true;
            inflags[4] = false;
            switch (keyCode) {
                case KeyEvent.VK_LEFT -> inflags[1] = true;
                case KeyEvent.VK_RIGHT -> inflags[2] = true;
                case KeyEvent.VK_UP -> inflags[3] = true;
                case KeyEvent.VK_SPACE -> inflags[0] = !inflags[0];
                case KeyEvent.VK_F -> inflags[4] = true;
            }
        }

        public void keyReleased(KeyEvent e){
            key_event = true;
            int keyCode = e.getKeyCode();
            if (keyCode==KeyEvent.VK_LEFT)
                inflags[1] = false;
            if (keyCode==KeyEvent.VK_UP)
                inflags[3] = false;
            if (keyCode==KeyEvent.VK_RIGHT)
                inflags[2] = false;
            if (keyCode==KeyEvent.VK_F)
                inflags[4] = false;
        }
    }
}
