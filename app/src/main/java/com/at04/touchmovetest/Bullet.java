package com.at04.touchmovetest;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

import android.graphics.Bitmap;
import android.util.Log;

public class Bullet extends PhysicsSprite {
    //public Path path;
    private static final int SPEED_MULTIPLIER = 200;
    private float rotationSpeed = 5;
    public Bullet(Bitmap bitmap, Position p, float spd) {//, Path bp) {
        super(bitmap, p);
        this.spd = spd;
        //path = bp;
    }

    public void update() {
        sprite.rotateBitmap(rotationSpeed);
        super.update();
        //Log.d("bullet.update", pos.toString());
    }

    public void move() {
        pos.x += spd * SPEED_MULTIPLIER * GameLoop.dt_sec * cos(angle);
        pos.y += spd * SPEED_MULTIPLIER * GameLoop.dt_sec * sin(angle);
    }
}
