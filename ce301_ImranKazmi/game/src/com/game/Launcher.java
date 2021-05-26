package com.game;

import com.game.core.ControlsFrame;
import com.game.core.GameFrame;
import com.game.core.MenuControl;
import com.game.util.Menu;

public class Launcher implements MenuControl {

    public static void main(String[] args) {
        Launcher launcher = new Launcher();
//main launches game

    }

    public Launcher() {

        Menu men = new Menu(this);
//calls menu when launched
        //Mouse mouse = new Mouse();
        //frame.addMouseMotionListener(mouse);


    }

    public static void main() {
        Launcher launcher = new Launcher();
    }



    @Override
    public void startGame() {
        GameFrame gameframe = new GameFrame();
       // new Launcher();
    }

    @Override
    public void showControls() {
        ControlsFrame controlsFrame = new ControlsFrame();

    }

    @Override
    public void Exit() {

    }

    @Override
    public void showScores() {

    }
}

