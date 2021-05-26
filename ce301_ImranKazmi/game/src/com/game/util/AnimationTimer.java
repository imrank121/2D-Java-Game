package com.game.util;

import com.game.entity.Entity;

public class AnimationTimer implements Runnable{
    int numFrames;
    int frameRate;
    Entity entity;
    int initialSpriteX, initialSpriteY;
    int tag;
    int preDelay;

    public AnimationTimer(int numFrames, int frameRate, Entity entity, int tag, int preDelay){
        this.numFrames = numFrames;//instantiating variables set in class in constructor
        this.frameRate = frameRate; //setting frame rate for speef of animation
        this.entity = entity;
        this.tag = tag;
        this.preDelay = preDelay; //delay created for smooter animation before attacks
    }

    @Override
    public void run() {
        initialSpriteY = entity.spriteSheety;
        initialSpriteX = entity.spriteSheetx;
        try {
            Thread.sleep((preDelay)); //delay pre-attack
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long sleepTime = (long) ((1f/frameRate) * 1000); //1 second delay during animation timer call
        for(int i = 1;i<=numFrames;i++){
            entity.getNextSprite(numFrames);
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        entity.setSpriteLocation(initialSpriteX, initialSpriteY);
        entity.onAnimationFinished(tag);
    }
}
