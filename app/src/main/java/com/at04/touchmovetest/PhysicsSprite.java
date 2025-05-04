package com.at04.touchmovetest;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

public class PhysicsSprite extends Physics {
    private int percentage = 50;
    protected Sprite sprite;
    float hitRadius;

    public PhysicsSprite() {
        this.sprite = new Sprite(null, true);
        this.sprite.matrix = AttackManager.getBulletMatrix();
    }
    public PhysicsSprite(Bitmap b, boolean isBullet) {
        this.sprite = new Sprite(b, isBullet);
        initBounds();
        applyMovement();
    }
    public PhysicsSprite(Bitmap b, Point p) {
        this.sprite = new Sprite(b, true);
        pos.x = p.x;
        pos.y = p.y;
        initBounds();
        applyMovement(); //this moves the sprite to the initial position
    }

    public void initBounds() {
        bounds = new RectF();
        int width = sprite.bitmap.getWidth();
        int height = sprite.bitmap.getHeight();
        float minAxis = Math.min(width, height);
        radius = minAxis / 2f;
        float skinWidth = radius - (minAxis / 2f * ((100f - percentage) / 100f));
        hitRadius = radius - skinWidth;
        updateBounds();
    }
    private void updateBounds() {
        centerX = pos.x + radius;
        centerY = pos.y + radius;
        bounds.set(centerX - hitRadius, centerY + hitRadius,
                centerX + hitRadius, centerY - hitRadius);
    }

    public void update() {
        //at this point, "pos" has already been set
        applyMovement(); // apply pos, angle information to the sprite
        updateBounds(); // bounds will be used for collision detection
    }
    public void draw(Canvas canvas) {
        if(sprite.bitmap != null) {
            sprite.draw(canvas);
        }
    }


    protected void applyMovement() {
        sprite.matrix.reset();
        sprite.matrix.postTranslate(-radius, -radius);
        sprite.matrix.postRotate(sprite.bitmapAngle);
        sprite.matrix.postTranslate(pos.x + radius, pos.y + radius);
    }

    public void setPosition(Point p) {
        pos.x = p.x;
        pos.y = p.y;
    }
}
