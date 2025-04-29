package com.at04.touchmovetest;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

import android.graphics.Bitmap;
import android.util.Log;

public class Bullet extends PhysicsSprite {
    //public Path path;
    private static final int SPEED_MULTIPLIER = 200;
    private float rotationSpeed = 5;

    public boolean isLoaded = false;
    public Bullet(Bitmap bitmap, Position p, float spd) {//, Path bp) {
        super(bitmap, p);
        this.spd = spd;
    }
    public Bullet(Bitmap bitmap) {
        super(bitmap, true);
    }

    public void update() {
        sprite.rotateBitmap(rotationSpeed);
        super.update();
        //Log.d("bullet.update", pos.toString());
    }

    public void unloadAndReset() {
        isLoaded = false;
        pos.x = FAR_AWAY.x;
        pos.y = FAR_AWAY.y;
        spd = 0;
        angle = 0;
        initBounds();
        applyMovement();
        //Log.d("unloaded", String.valueOf(this.sprite.matrix));
    }

    public void load(Position pos, float spd, float angle) {
        isLoaded = true;
        this.pos.x = pos.x;
        this.pos.y = pos.y;
        this.spd = spd;
        this.angle = angle;
        initBounds();
        applyMovement();
        //Log.d("loaded", String.valueOf(this.sprite.matrix));
    }

    public void move() {
        pos.x += spd * SPEED_MULTIPLIER * GameLoop.dt_sec * cos(angle);
        pos.y += spd * SPEED_MULTIPLIER * GameLoop.dt_sec * sin(angle);
    }
}
