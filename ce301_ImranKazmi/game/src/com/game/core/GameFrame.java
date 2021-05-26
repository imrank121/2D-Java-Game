package com.game.core;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    private final String NAME = "The Killing of Kaz";//title of game frame when game class is launched
    public int WIDTH = 1280;
    public int HEIGHT = 720;


    public GameFrame() {
        setTitle(NAME);
        setContentPane(new Game(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(WIDTH,HEIGHT));
        setVisible(true);
        pack();
//setting dimensions for game frame

    }
}
