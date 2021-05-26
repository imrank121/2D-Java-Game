package com.game.entity;

import com.game.sprite.SpriteSheet;
import com.game.util.AnimationTimer;
import com.game.util.Vector2f;
import java.awt.*;


public abstract class Enemy extends Entity {
    int direction;
    int prev_direction;
    Player player;//creating player object
    float SPEED = 1.5f;//setting speed of enemy
    public float minDistToPlayer = 25f; //setting min dist enemy can be to player before attacking or movement
    public int attack_cooldown_default = 1000;
    public int attack_cooldown = attack_cooldown_default;
    final int attack_tag = 0;
    final int die_tag = 1;
    float attackRange = 10;//setting out range for triggering enemy attack
    int attackDamage = 1;//damage inflicted by enemy
    public boolean isAlive = true;//sets enemy state to alive


    public Enemy(SpriteSheet spriteSheet, Vector2f origin, int size, Player player, int max_health, int width, int height) {
        super(spriteSheet, origin, size);
        this.player = player;
        this.width = width;
        this.height = height;
        this.max_health = max_health;
        current_health = max_health;
    }
//abstract methods to be implemented in enemies - beetle/mantis
    @Override
    abstract public void die();

    @Override
    abstract public void onAnimationFinished(int tag);


    @Override
    public void render(Graphics g) {
        g.drawImage(spriteSheet.getSprite(spriteSheetx, spriteSheety), (int) pos.x, (int) pos.y, size, size, null);
    }

    abstract public void update();

    abstract public void faceDirection(int direction);

    abstract public void faceAttackDirection(int direction);

    abstract public void enemyAttack();

    public boolean isPlayerInRange() {
        if (Vector2f.distanceBetweenPoints(pos, player.pos) > attackRange) {
            return true; //if player position is greater than the attack range set, then enemy can attack
        }
        return false; //if not near player in given range, set to false
    }

    public void attack(int num_frames, int frame_rate){
//attack method for enemies, if player is in range then enemy will be attacking, decrement players health, if 0 - die animation for player
        is_attacking = true;
        if(isPlayerInRange()){
            player.current_health -= attackDamage;
            if(player.current_health ==0){
                player.die();
            }
        }

        new Thread(new AnimationTimer(num_frames, frame_rate, this, attack_tag,200)).start(); //animation time for enemy attacking, pre delay so player is constantly attacked
        try {
            Thread.sleep((long) (num_frames * (1f / frame_rate) * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace(); //enemy pauses while its attacking
        }

    }



}
