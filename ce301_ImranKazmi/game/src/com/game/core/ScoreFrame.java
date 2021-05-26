/*package com.game.core;

import com.game.util.ScoreBoard;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ScoreFrame extends JFrame {

    private final String NAME = "Scores";
    public int WIDTH = 400;
    public int HEIGHT = 400;
    private BufferedImage Scoresimg;
    public int score;

    public ScoreFrame(int score){

        this.score = score;
        var scores = new ScoreBoard();
        var output = scores.read();
        System.out.println(output);

        try {
            Scoresimg = ImageIO.read(getClass().getClassLoader().getResourceAsStream("props/menuimg.jpg"));
        } catch(IOException e) {
            e.printStackTrace();

        }

        catch(Exception e) {
            e.printStackTrace();
        }

        JButton Scoresbutton = new JButton("Scores");
        Scoresbutton.setPreferredSize(new Dimension(40,40));
        Scoresbutton.setSize(40,40);
        Scoresbutton.setBounds(20,30,50,30);


        JPanel scorePanel = new JPanel(new BorderLayout()){
            @Override
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                g.drawImage(Scoresimg, 0,0, null);
            }

        };


        Scoresbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               var scorecounter = new ScoreBoard();
               scorecounter.saveScore("Kaz",score);
            }
        });

        scorePanel.add(Scoresbutton);

        scorePanel.setPreferredSize(new Dimension(Scoresimg.getWidth(),Scoresimg.getHeight()));
        scorePanel.setSize(new Dimension(Scoresimg.getWidth(),Scoresimg.getHeight()));
        scorePanel.setVisible(true);


        setTitle(NAME);

        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        //controlpanel.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        this.add(scorePanel);
        pack();
        this.setVisible(true);

    }

}*/
