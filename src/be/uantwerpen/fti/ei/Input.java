package be.uantwerpen.fti.ei;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class Input {
    public enum Inputs {LEFT, RIGHT, UP, SPACE};
    private LinkedList<Inputs> keyInputs;

    public Input(GraphicsCTX gctx) {
        gctx.getFrame().addKeyListener(new KeyInputAdapter());
        keyInputs = new LinkedList<>();
    }
    public boolean inputAvailable(){
        return keyInputs.size() > 0;
    }
    public Inputs getInput() {
        return keyInputs.poll();
    }

    class KeyInputAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            switch (keyCode){
                case KeyEvent.VK_LEFT : keyInputs.add(Inputs.LEFT); break;
                case KeyEvent.VK_RIGHT: keyInputs.add(Inputs.RIGHT); break;
                case KeyEvent.VK_UP: keyInputs.add(Inputs.UP); break;
                case KeyEvent.VK_SPACE: keyInputs.add(Inputs.SPACE); break;
            }
        }
    }
}
