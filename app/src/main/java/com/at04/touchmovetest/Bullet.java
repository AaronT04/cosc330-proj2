package com.at04.touchmovetest;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

import android.graphics.Bitmap;

public class Bullet extends PhysicsSprite {
    private static final int SPEED_MULTIPLIER = 200;
    private float rotationSpeed = 0;

    public boolean isLoaded = false;
    public Bullet(Bitmap bitmap, Point p, float spd) {//, Path bp) {
        super(bitmap, p);
        this.spd = spd;
    }
    public Bullet(Bitmap bitmap) {
        super(bitmap, true);
    }
    public Bullet() {
        super();
    }

    public void update() {
        sprite.rotateBitmap(rotationSpeed);
        super.update();
        //Log.d("bullet.update", pos.toString());
    }
    public void setBitmapRotationSpeed(float anglePerFrame) {
        this.rotationSpeed = anglePerFrame;
    }

    public void unloadAndReset() {
        isLoaded = false;
        pos.x = FAR_AWAY.x;
        pos.y = FAR_AWAY.y;
        spd = 0;
        angle = 0;
        rotationSpeed = 0;
        initBounds();
        applyMovement();
    }

    public void load(Bitmap bitmap, Point pos, float spd, float angle) {
        isLoaded = true;
        this.sprite.bitmap = bitmap;
        this.pos.x = pos.x;
        this.pos.y = pos.y;
        this.spd = spd;
        this.angle = angle;
        initBounds();
        applyMovement();
    }

    public void move() {
        pos.x += (float) (spd * SPEED_MULTIPLIER * GameLoop.dt_sec * cos(angle));
        pos.y += (float) (spd * SPEED_MULTIPLIER * GameLoop.dt_sec * sin(angle));
    }
}
