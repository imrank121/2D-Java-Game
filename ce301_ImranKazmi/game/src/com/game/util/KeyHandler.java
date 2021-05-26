package com.game.util;

import com.game.Playercontrol;
import com.game.core.Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    
    Playercontrol c; //referance to playercontrol created c

    public KeyHandler(Game p, Playercontrol c) {
        p.addKeyListener(this);
        this.c = c; //instantiating in constructor
    }

    public KeyHandler(){
        System.out.println("new key list");
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if(e.getKeyCode()== KeyEvent.VK_W) {

            c.move(3);//direction reference from interface, direction 3 correspond to UP
        }
        else if(e.getKeyCode()== KeyEvent.VK_S){
            c.move(1);//move to position down, move references sprite direction
        }
        else if(e.getKeyCode()== KeyEvent.VK_A){
            c.move(2);//left
        }
        else if(e.getKeyCode()== KeyEvent.VK_D) {
            c.move(0);//right
        }
        else if(e.getKeyCode()== KeyEvent.VK_RIGHT){
            c.attack(0);//attack right
        }
        else if(e.getKeyCode()==KeyEvent.VK_DOWN){
            c.attack(1);//attack down
        }
        else if(e.getKeyCode()== KeyEvent.VK_LEFT){
            c.attack(2);//attack left
        }
        else if(e.getKeyCode()== KeyEvent.VK_UP){
            c.attack(3);//attack upwards
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
