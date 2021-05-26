package com.game.core;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ControlsFrame extends JFrame {
//creating control frame in menu
    private final String NAME = "Controls";
    public int WIDTH = 400;
    public int HEIGHT = 400;
    private BufferedImage controlsimg;


    public ControlsFrame() {

        try {
            controlsimg = ImageIO.read(getClass().getClassLoader().getResourceAsStream("props/controlscreen.png")); //loading control screen image as background
        } catch(IOException e) {
            e.printStackTrace();

        }
        catch(Exception e) {
            e.printStackTrace();
        }

        JPanel controlsPanel = new JPanel(new BorderLayout()){
            @Override
            protected void paintComponent(Graphics g){//painting background to panel
                super.paintComponent(g);
                g.drawImage(controlsimg, 0,0, null);
            }

        };
        controlsPanel.setPreferredSize(new Dimension(controlsimg.getWidth(),controlsimg.getHeight()));
        controlsPanel.setSize(new Dimension(controlsimg.getWidth(),controlsimg.getHeight()));
        controlsPanel.setVisible(true);
        //setting dimensions for frame
        setTitle(NAME);
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        this.add(controlsPanel);
        pack();
        this.setVisible(true);
    }




}
