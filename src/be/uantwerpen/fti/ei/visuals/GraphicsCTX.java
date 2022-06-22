package be.uantwerpen.fti.ei.visuals;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.IOException;

/**
 * Prescribes the graphical context:
 *      - Setting dimensions and blocksize
 *      - Making a frame and a panel
 */
public class GraphicsCTX {
    private final int ScreenWidth;
    private final int ScreenHeight;
    private JFrame frame;
    private JPanel panel;
    private BufferedImage g2dimage;
    private Graphics2D g2d;
    private int size;

    public Graphics2D getG2d(){
        return g2d;
    }
    public JFrame getFrame(){
        return frame;
    }
    public int getSize(){
        return this.size;
    }

    /**
     * Initiate frame and panel
     */
    public GraphicsCTX(){
        ScreenWidth = 1400;
        ScreenHeight = 810;
        frame = new JFrame();
        panel = new JPanel(true){
            @Override
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                doDrawing(g);
            }
        };
        frame.setFocusable(true);
        frame.add(panel);
        frame.setTitle("RoofRunner");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Repaint the panel
     */
    public void render() {
        panel.repaint();
    }

    /**
     * Draws the g2d image onto the graphics panel
     * @param g
     */
    private void doDrawing(Graphics g){
        Graphics2D graph2D = (Graphics2D) g;
        Toolkit.getDefaultToolkit().sync();
        graph2D.drawImage(g2dimage, 0, 0, null);
        graph2D.dispose();
        if (g2d != null)
            g2d.clearRect(0,0,frame.getWidth(),frame.getHeight());
    }


    /**
     * Sets blocksize and background, creates a new Buffered Image
     * @param x_dim
     * @param y_dim
     * @throws IOException
     */
    public void setGameDimensions(int x_dim, int y_dim) throws IOException {
        size = Math.min(ScreenWidth/x_dim, ScreenHeight/y_dim);
        frame.setLocation(50,50);
        frame.setSize(ScreenWidth, ScreenHeight);
        g2dimage = new BufferedImage(frame.getWidth(), frame.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        g2d = g2dimage.createGraphics();
        g2d.setBackground(new Color(0, 0, 0, 255));
        g2d.clearRect(0,0, frame.getWidth(), frame.getHeight());
    }
}
