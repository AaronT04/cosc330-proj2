package com.at04.touchmovetest;

import android.graphics.Bitmap;

public class Bullet extends Sprite {
    public BulletPath path;
    private float rotationSpeed = 5;
    public Bullet(Bitmap bitmap, Position p, BulletPath bp) {
        super(bitmap, 50);
        path = bp;
        this.pos = p;
        matrix.postTranslate(pos.x, pos.y);
    }

    public void update() {
        path.update(this);
        rotateBitmap(rotationSpeed);
        super.update();

    }


}
