package com.game.entity;

import com.game.sprite.SpriteSheet;
import com.game.util.Vector2f;

import java.awt.*;

public abstract class Entity {
    //setting out variables that enemies and players would need to use as enemy and player extend entity
    public SpriteSheet spriteSheet;
    public Vector2f pos;
    public int size;
    private int spriteOriginx;
    private int spriteOriginy;
    public int xMargin=0;
    public int yMargin=0;
    public int width=0;
    public int height=0;
    public boolean is_attacking = false; //attacking state set to false intially
    public float max_health;
    public float current_health; //variables created for health
    public int spriteSheetx =0;//creating x variable for sub image location
    public int spriteSheety =0;//creating y variable for sub image location


    public Entity(SpriteSheet spriteSheet, Vector2f origin, int size) {
        this.spriteSheet = spriteSheet;
        pos = origin;
        this.size = size;
    }


    public abstract void die();


    public void setSpriteLocation(int x, int y){ //method to set sprite x and y animation from sprite sheet
        spriteSheetx =x;
        spriteSheety =y;
        spriteOriginx =x;
        spriteOriginy =y;

    }

    public void getNextSprite(int animationLength){ //gets the next sprite in the sprite sheet by incrementing by one
        spriteSheetx +=1;
        if(spriteSheetx > spriteOriginx + animationLength){
            spriteSheetx = spriteOriginx;
        }

    }

    public void nextActionFrame() {}

    public abstract void onAnimationFinished(int tag);

    public abstract void render(Graphics g);

    public Vector2f getEntityCentre(){ //get the coordinates for the centre of the player for pathfinding and debugging
        Vector2f vector2f = new Vector2f(pos.x + xMargin + (width / 2), pos.y + yMargin + (height / 2));
        return vector2f;
    }
}

