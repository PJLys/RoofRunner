package be.uantwerpen.fti.ei.visuals.city;

import be.uantwerpen.fti.ei.visuals.GraphicsCTX;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CityGCTX extends GraphicsCTX {
    private final int ScreenWidth;
    private final int ScreenHeight;
    private JFrame frame;
    private JPanel panel;
    private BufferedImage g2dimage;
    private Graphics2D g2d;
    private int size;

    public BufferedImage obstacle_image;
    public BufferedImage player_image_right;
    public BufferedImage player_image_left;
    public BufferedImage collectable_image;
    public BufferedImage enemy_image;
    public BufferedImage projectile_image_left, projectile_image_right;

    public Graphics2D getG2d(){
        return g2d;
    }
    public JFrame getFrame(){
        return frame;
    }
    public int getSize(){
        return this.size;
    }
    public CityGCTX() {
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
    public void render() {
        panel.repaint();
    }
    private void doDrawing(Graphics g){
        Graphics2D graph2D = (Graphics2D) g;
        Toolkit.getDefaultToolkit().sync();
        graph2D.drawImage(g2dimage, 0, 0, null);
        graph2D.dispose();
        if (g2d != null)
            g2d.clearRect(0,0,frame.getWidth(),frame.getHeight());
    }
    public void setGameDimensions(int x_dim, int y_dim) throws IOException {
        size = Math.min(ScreenWidth/x_dim, ScreenHeight/y_dim);
        frame.setLocation(50,50);
        frame.setSize(ScreenWidth, ScreenHeight);
        g2dimage = new BufferedImage(frame.getWidth(), frame.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        g2d = g2dimage.createGraphics();
        g2d.setBackground(new Color(0, 0, 0, 255));
        g2d.clearRect(0,0, frame.getWidth(), frame.getHeight());
        loadImages();
        resizeAll();
    }

    private void loadImages() throws IOException {
        obstacle_image = ImageIO.read(new File("src\\be\\uantwerpen\\fti\\ei\\visuals\\city\\images\\obstacle.png"));
        player_image_right = ImageIO.read(new File("src\\be\\uantwerpen\\fti\\ei\\visuals\\city\\images\\playersprites\\Jump__003.png"));
        player_image_left = ImageIO.read(new File("src\\be\\uantwerpen\\fti\\ei\\visuals\\city\\images\\playersprites\\Jump__003_L.png"));
        collectable_image = ImageIO.read(new File("src\\be\\uantwerpen\\fti\\ei\\visuals\\city\\images\\ruby.png"));
        enemy_image = ImageIO.read(new File("src\\be\\uantwerpen\\fti\\ei\\visuals\\city\\images\\enemiessprites\\skeleton-animation_00.png"));
        projectile_image_right = ImageIO.read(new File("src\\be\\uantwerpen\\fti\\ei\\visuals\\city\\images\\playersprites\\Kunai.png"));
        projectile_image_left = ImageIO.read(new File("src\\be\\uantwerpen\\fti\\ei\\visuals\\city\\images\\playersprites\\Kunai_L.png"));

    }

    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight){
        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_DEFAULT);
        BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_4BYTE_ABGR_PRE);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        return outputImage;
    }

    private void resizeAll(){
        obstacle_image = resizeImage(obstacle_image,size,size);
        player_image_right = resizeImage(player_image_right, (int) (size*.8), (int) (size*1.6));
        player_image_left = resizeImage(player_image_left, (int) (size*.8), (int) (size*1.6));
        collectable_image = resizeImage(collectable_image,size,size);
        enemy_image = resizeImage(enemy_image,size,size);
        projectile_image_right = resizeImage(projectile_image_right,20,5);
        projectile_image_left = resizeImage(projectile_image_left,20,5);
    }
}
