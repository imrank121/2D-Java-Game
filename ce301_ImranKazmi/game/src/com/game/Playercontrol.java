package com.game;

public interface Playercontrol {


     void move(int direction); //control the direction of the sprite
     boolean canMove(float newX, float newY);
     void attack(int direction);//direction the sprite is going to be in when attacking

}

