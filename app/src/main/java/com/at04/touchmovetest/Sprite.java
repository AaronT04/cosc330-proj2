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
    public Sprite(Bitmap b, boolean isBullet) {
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        //matrix = new Matrix();
        bitmap = b;
        AttackManager.matrices[AttackManager.bulletIdx % AttackManager.MAX_BULLETS]
                = new Matrix();
        matrix = AttackManager.matrices[AttackManager.bulletIdx % AttackManager.MAX_BULLETS];
    }

    public void rotateBitmap(float angle) {
        bitmapAngle += angle;// * GameLoop.delta_ms;
        if(bitmapAngle > 360) {
            bitmapAngle -= 360;
        }
    }
    private Timer profileTimer = new Timer();
    public void draw(Canvas canvas) {
        if(bitmap != null) {
            //profileTimer.start();
            canvas.drawBitmap(bitmap, matrix, paint);
            //profileTimer.debugStop("sprite->draw()");
        }
        else
            Log.d("Sprite->draw()", "bitmap==null");
    }
}
