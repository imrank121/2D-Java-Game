package com.game.entity;

import com.game.sprite.SpriteSheet;
import com.game.util.AnimationTimer;
import com.game.util.Values;
import com.game.util.Vector2f;


import static com.game.core.Game.overlapping;

public class Beetle extends Enemy {


    public Beetle(SpriteSheet spriteSheet, Vector2f origin, int size, Player player) {
        super(spriteSheet, origin, size, player, 12, 2, 2); //health set to 12 for greater tolerance to damage
        SPEED = 1;///setting beetles speed
        attackRange = 5; //setting attack range for min dist
        attackDamage = 1; //setting damage dealt to player as 1 per attack
    }

    @Override
    public void die() {
        setSpriteLocation(0,8); //die animation when health is 0
        new Thread(new AnimationTimer(1, 1, this, die_tag,0)).start();
    }



    @Override
    public void onAnimationFinished(int tag) {
        switch (tag) {
            case attack_tag:
                is_attacking = false;
                try {
                    Thread.sleep((2000)); //attack delay on enemy
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                faceDirection(direction); //make sure the enemy faces the right direction when animation is done
                break;
            case die_tag:
                isAlive = false;
                break;
        }
    }

    @Override
    public void update() {
        if(current_health > 0) {
            var playerpos = player.pos;
            if (!is_attacking) {
                // check collision / move
                var newPos = Vector2f.moveTowards(pos, playerpos, SPEED);
                //moves closer to player?
                if (Vector2f.distanceBetweenPoints(newPos, playerpos) > minDistToPlayer) {
                    pos = newPos; //if enemy is too far from player, go towards player till min dist is correct
                } else {             //close enough time to attack
                    enemyAttack(); //triggers enemy attacking
                    System.out.println("");
                }

                direction = Vector2f.directionTowards(this, playerpos);
                if (direction == prev_direction) {//if the prev direction is the same as next direction

                    getNextSprite(3); //incrementing for sprite animation
                } else {
                    faceDirection(direction); //otherwise face direction enemy is already in
                }

                prev_direction = direction;
            } else {
                if (attack_cooldown > 0) {
                    attack_cooldown--;
                } else {
                    is_attacking = false;
                    attack_cooldown = attack_cooldown_default;
                }
            }
            Vector2f bottomRightPos = new Vector2f(pos.x + width, pos.y + height);
            Vector2f playerbottomrightpos = new Vector2f(playerpos.x + player.width, playerpos.y + player.height);

            if (overlapping(pos, bottomRightPos, playerpos, playerbottomrightpos)) {
                System.out.println("overlap"); //registering overlap between player and enemy for detection, similar to prop and player detection
            }
        }
    }

    @Override
    public void faceDirection(int direction) { //setting directional values to correspond with sprite sheet location
        switch (direction) {
            case Values.RIGHT:
                setSpriteLocation(0, 0);
                break;
            case Values.DOWN:
                setSpriteLocation(0, 2);
                break;
            case Values.LEFT:
                setSpriteLocation(0, 1);
                break;
            case Values.UP:
                setSpriteLocation(0, 3);
                break;
        }
    }

    @Override
    public void faceAttackDirection(int direction) { //setting directional values to face the correct direction when attacking depending on players direction
        switch (direction) {
            case Values.RIGHT:
                setSpriteLocation(0, 0);
                break;
            case Values.DOWN:
                setSpriteLocation(0, 5);
                break;
            case Values.LEFT:
                setSpriteLocation(0, 6);
                break;
            case Values.UP:
                setSpriteLocation(0, 7);
                break;
        }
    }

    @Override
    public void enemyAttack() {
        faceAttackDirection(direction);
        attack(5,5);
    }
}
