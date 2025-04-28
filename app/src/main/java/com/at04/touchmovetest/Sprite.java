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
    public RectF bounds;
    protected float centerX;
    protected float centerY;
    protected float radius;
    protected int percentage = 50;
    protected float skinWidth;
    protected Position pos = new Position(0, 0);
    protected Matrix matrix;
    protected Paint paint;
    protected float angle = 0;

    public Sprite(Bitmap b, float percentage) {
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        bounds = new RectF();
        matrix = new Matrix();
        bitmap = b;

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        centerX = (float)width / 2;
        centerY = (float)height / 2;
        float minAxis = Math.min(width, height);
        radius = minAxis / 2f;
        skinWidth = radius - (minAxis / 2f * ((100f - percentage) / 100f));
    }

    protected void applyMovement() {
        matrix.reset();
        matrix.postTranslate(-radius, -radius);
        matrix.postScale(0.8f, 0.8f);
        matrix.postRotate(this.angle);
        matrix.postTranslate(pos.x + radius * 0.8f, pos.y + radius * 0.8f);
    }

    public void rotateBitmap(float angle) {
        this.angle += angle;// * GameLoop.delta_ms;
        if(this.angle > 360) {
            this.angle -= 360;
        }
    }

    public void setPlayerPos(Position playerPos) {
    }

    public Position getPos() {
        return pos;
    }

    public void draw(Canvas canvas) {
        if(bitmap != null) {
            canvas.drawBitmap(bitmap, matrix, paint);
            canvas.drawRect(bounds, paint);
        }
        else
            Log.d("Sprite->draw()", "bitmap==null");
    }

    public void setPosition(Position p) {
        pos.x = p.x;
        pos.y = p.y;
    }

    public void update() {
        applyMovement();
        float hitRadius = radius - skinWidth;
        centerX = pos.x + radius;
        centerY = pos.y + radius;
        bounds.set(centerX - hitRadius, centerY + hitRadius,
                centerX + hitRadius, centerY - hitRadius);
    }
}
