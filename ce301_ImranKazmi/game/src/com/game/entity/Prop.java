package com.game.entity;

import com.game.sprite.SpriteSheet;
import com.game.util.Vector2f;

import java.awt.*;

public class Prop extends Entity
{

    public Vector2f bottomRightPos;

    public Prop(SpriteSheet spriteSheet, Vector2f origin, int size) {//extending objects in params
        super(spriteSheet, origin, size);
        bottomRightPos = new Vector2f(origin.x+size, origin.y+size);//retrieving bottom right pos of props
    }
//abstract methods implemented from entity
    @Override
    public void die() {

    }

    @Override
    public void onAnimationFinished(int tag) {

    }


    @Override
    public void render(Graphics g) {
        g.drawImage(spriteSheet.getSpriteSheet(), (int) pos.x, (int) pos.y, size,size,null);
    }
}
