package com.game.entity;

import com.game.sprite.SpriteSheet;
import com.game.util.AnimationTimer;
import com.game.util.Values;
import com.game.util.Vector2f;
import java.util.Random;
//same layout as beetle class but with lower health as common enemy set and its own sprite animation references
import static com.game.core.Game.overlapping;

public class Mantis extends Enemy {

    public Mantis(SpriteSheet spriteSheet, Vector2f origin, int size, Player player) {
        super(spriteSheet, origin, size, player, 5, 2, 3);
        var random = new Random();
        var rand = (random.nextFloat() - 0.5) *2;
        var variation = 0.5f * SPEED;
        SPEED = (float) (SPEED + variation * rand);
    }

    @Override
    public void die() {
        setSpriteLocation(0,8);
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
                faceDirection(direction);
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
                //should move closer to player
                if (Vector2f.distanceBetweenPoints(newPos, playerpos) > minDistToPlayer) {
                    pos = newPos;
                } else {             //close enough time to attack
                    enemyAttack();

                }

                direction = Vector2f.directionTowards(this, playerpos);
                if (direction == prev_direction) {
                    getNextSprite(3);
                } else {
                    faceDirection(direction);
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
                System.out.println("overlap");
            }
        }
    }

    @Override
    public void faceDirection(int direction) {
        switch (direction) {
            case Values.RIGHT:
                setSpriteLocation(0, 0);
                break;
            case Values.DOWN:
                setSpriteLocation(0, 3);
                break;
            case Values.LEFT:
                setSpriteLocation(0, 1);
                break;
            case Values.UP:
                setSpriteLocation(0, 2);
                break;
        }

    }

    @Override
    public void faceAttackDirection(int direction) {
        switch (direction) {
            case Values.LEFT:
                setSpriteLocation(0, 4);
                break;
            case Values.RIGHT:
                setSpriteLocation(0, 5);
                break;

            case Values.UP:
                setSpriteLocation(0, 6);
                break;

            case Values.DOWN:
                setSpriteLocation(0, 7);
                break;
        }
    }

    @Override
    public void enemyAttack() {
        faceAttackDirection(direction);
        attack(5, 15);
    }


}