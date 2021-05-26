package com.game.util;

import com.game.entity.Entity;

//CODE & MATHS ADAPTED FROM THE FOLLOWING SOURCES:
// https://answers.unity.com/questions/1005591/having-trouble-getting-vector2movetowards-to-work.html
//https://answers.unity.com/questions/1570910/vector2movetowards-not-working-1.html
//https://docs.unity3d.com/ScriptReference/Vector3.MoveTowards.html

public class Vector2f {

    public float x;
    public float y;

    public static float worldX;
    public static float worldY;

    public Vector2f() {
        x = 0;
        y = 0;
    }

    public Vector2f(Vector2f vec) {
        new Vector2f(vec.x, vec.y);
    }

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }


    @Override
    public String toString() {
        return x + ", " + y;
    }

    public static Vector2f moveTowards(Vector2f current, Vector2f target, float maxDistanceDelta)
    {//method to move towards player for enemies
        float toVector_x = target.x - current.x;
        float toVector_y = target.y - current.y;

        float sqDist = toVector_x * toVector_x + toVector_y * toVector_y;

        if (sqDist == 0 || (maxDistanceDelta >= 0 && sqDist <= maxDistanceDelta * maxDistanceDelta))
            return target;

        float dist = (float)Math.sqrt(sqDist);

        return new Vector2f(current.x + toVector_x / dist * maxDistanceDelta,
                current.y + toVector_y / dist * maxDistanceDelta);
    }

    public static double distanceBetweenPoints(Vector2f p1, Vector2f p2){
        double dist = Math.hypot(p1.x-p2.x, p1.y-p2.y);
        return dist;
//takes 2 pos values and finds the distance between them, used for attack ranges and gauging mis dist between player and enemy
    }

    public static float getAngle(Vector2f from,Vector2f to) {//get angle of enemy
        float angle = (float) Math.toDegrees(Math.atan2(to.y - from.y, to.x - from.x));

        if(angle < 0){
            angle += 360;//up,down,left,right angles
        }

        return angle;
    }

    public static int directionTowards(Entity ent,Vector2f pos){
        var angle = Vector2f.getAngle(ent.pos, pos);
        //System.out.println(angle);
        int direction;
//if within defined ranges in 360, set enemy animation to change direction when moving towards player so they are turning during pathfinding
        if (angle > 45 && angle < 135) {
            direction = Values.DOWN;


        } else if (angle > 135 && angle < 225) {
            direction = Values.LEFT;


        } else if (angle > 225 && angle < 315) {
            direction = Values.UP;


        } else {
            direction = Values.RIGHT;

        }
      return direction;
    }
}
