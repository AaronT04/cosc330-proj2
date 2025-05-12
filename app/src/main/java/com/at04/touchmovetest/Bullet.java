package com.at04.touchmovetest;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

import android.graphics.Bitmap;

/**
 * Stores physics for a bullet, as well as the bitmap information (Sprite).
 * Also handles "loading" and "unloading". By default, all bullets are already allocated
 * but not "loaded". Unloaded means that they are stopped and stored somewhere far offscreen.
 */
public class Bullet extends PhysicsSprite {
    private static final int SPEED_MULTIPLIER = 200; //Convert spd from Attacks into a usable value
    private float rotationSpeed = 0; //Refers to bitmap rotation speed (rotation of the image)

    /**
     * By default, all bullets are already allocated but not "loaded". Unloaded means that
     * they are stopped and stored somewhere far offscreen.
     */

    public boolean isLoaded = false;
    public Bullet() {
        super();
    }

    public void update() {
        sprite.rotateBitmap(rotationSpeed);
        super.update();
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
