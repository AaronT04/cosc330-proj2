package com.at04.touchmovetest;

import android.graphics.Bitmap;
import android.util.Log;

public class Bullet extends PhysicsSprite {
    public Path path;
    private float rotationSpeed = 5;
    public Bullet(Bitmap bitmap, Position p, Path bp) {
        super(bitmap, p);
        path = bp;
    }

    public void update() {
        path.update(this);
        sprite.rotateBitmap(rotationSpeed);
        super.update();
        Log.d("bullet.update", pos.toString());
    }
}
