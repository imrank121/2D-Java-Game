package com.game.util;

import com.game.core.MenuControl;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class Menu extends JFrame implements ActionListener {
        public boolean running = true;
        public MenuControl menucontrol = null;
        private BufferedImage menuimg;
        private Menu menu = this;


        public Menu(MenuControl menuControl){

            this.setPreferredSize(new Dimension(450,600));//setting menu frame size

            this.menucontrol = menuControl;//referencing interface menucontrol

            try {
                menuimg = ImageIO.read(getClass().getClassLoader().getResourceAsStream("props/menuimg.jpg"));
            } catch(IOException e) {//loadbackgroud image
                e.printStackTrace();
                System.out.println("incorrect file path");
            }

            this.setTitle("MENU");
            JPanel MenuPanel = new JPanel(){
                @Override
                public void paintComponent(Graphics g) {
                    super.paintComponent(g);//paint background on to panel
                    Graphics2D g2D = (Graphics2D) g;

                    g.drawImage(menuimg, 0, 0, getWidth(), getHeight(), this);//drawing backgrounf on to panel

                }
            };

            this.setSize(1200, 720);


            JButton Play = new JButton("Play");//creating buttons to add on menu
            JButton Controls = new JButton("Controls");
            JButton Exit = new JButton("Exit");
            //JButton Score = new JButton("View Score");


            Play.addActionListener(new ActionListener() {//action listener for buttons
                @Override
                public void actionPerformed(ActionEvent e) {
                    menuControl.startGame();//call start game and launch game
                    System.out.println("pressed");
                    System.out.println(running);
                    menu.setVisible(false);

                }
            });

            Exit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }//exit system and close
            });

            Controls.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    menuControl.showControls();//show control frame



                }
            });



            MenuPanel.add(Play);
            MenuPanel.add(Controls);
            //MenuPanel.add(Score);
            MenuPanel.add(Exit);//adding buttons to panel
            
            this.add(MenuPanel);
            setResizable(true);
            this.setVisible(true);//set frame to visible
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            pack();



        }




    @Override
    public void actionPerformed(ActionEvent e) {

    }



}

