package com.at04.touchmovetest;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import androidx.annotation.NonNull;

public class Sprite {
    protected Bitmap bitmap;
    protected float skinWidth;
    protected Matrix matrix;
    protected Paint paint;

    protected float bitmapAngle = 0;

    public Sprite(Bitmap b) {
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        matrix = new Matrix();
        bitmap = b;
    }

    public void rotateBitmap(float angle) {
        bitmapAngle += angle;// * GameLoop.delta_ms;
        if(bitmapAngle > 360) {
            bitmapAngle -= 360;
        }
    }

    public void setPlayerPos(Position playerPos) {
    }

    public void draw(Canvas canvas) {
        if(bitmap != null) {
            canvas.drawBitmap(bitmap, matrix, paint);
        }
        else
            Log.d("Sprite->draw()", "bitmap==null");
    }

    /*
    public void update() {
        applyMovement();
        float hitRadius = radius - skinWidth;
        centerX = pos.x + radius;
        centerY = pos.y + radius;
        bounds.set(centerX - hitRadius, centerY + hitRadius,
                centerX + hitRadius, centerY - hitRadius);
    }
    */
}
