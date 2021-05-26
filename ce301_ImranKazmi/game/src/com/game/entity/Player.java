package com.game.entity;

import com.game.sprite.SpriteSheet;
import com.game.util.AnimationTimer;
import com.game.util.Values;
import com.game.util.Vector2f;

import java.awt.*;

public class Player extends Entity{

    public int speed = 10;//setting player speed for movement
    public int previous_dir;
    public int direction;
    int action = -1;//action used to track attack state
    int ATTACKING = 1;//attack state set
    final int attack_tag = 0;
    public int attackRange =60;//players attack range set
    public boolean isAlive = true;

    public Player(SpriteSheet spriteSheet, Vector2f origin, int size) {
        super(spriteSheet, origin, size);
        xMargin = 10;
        yMargin = 10;
        width = size-xMargin-5;
        height = size-yMargin-5;
        max_health = 5;
        current_health = max_health;
    }


    @Override
    public void die() {
        setSpriteLocation(0,8);//die method set to death sprite animation
        isAlive = false;
    }

    @Override
    public void onAnimationFinished(int tag) {
        switch (tag){
            case attack_tag: //tracking the state the animation is in
                is_attacking = false;
                lookInDirection(direction);
                break;
        }
    }

    public void lookInDirection(int direction){
        if(this.current_health<0){return;}
        switch (direction){
            case Values.RIGHT:
                setSpriteLocation(0,0);
                break;
            case Values.DOWN:
                setSpriteLocation(0,2);
                break;
            case Values.LEFT:
                setSpriteLocation(0,1);
                break;
            case Values.UP:
                setSpriteLocation(0,3);
                break;
        }
    }

    public void attack(int direction){
        if(this.current_health<0){return;}
        switch(direction){
            case 0:
                setSpriteLocation(0,4);
                break;

            case 1:
                setSpriteLocation(0,5);
                break;

            case 2:
                setSpriteLocation(0,6);
                break;

            case 3:
                setSpriteLocation(0,7);

            default:
                break;
        }
        var numframes = 4;
        var framerate = 24;
        is_attacking =true;
        new Thread(new AnimationTimer(numframes,framerate,this, attack_tag,0)).start(); //placing sword attacking on a thread with animation timer for smooth effect and quicker execution

    }

    @Override
    public void render(Graphics g) {//used to render all entities in renderables
        g.drawImage(spriteSheet.getSprite(spriteSheetx, spriteSheety), (int) pos.x, (int) pos.y, size,size,null);
    }


    @Override
    public void nextActionFrame() {
        if(action == ATTACKING){
            getNextSprite(4);//tracks attack animation frame
        }
    }


}
