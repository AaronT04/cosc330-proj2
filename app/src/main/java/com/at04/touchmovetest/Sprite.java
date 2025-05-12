package com.at04.touchmovetest;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import androidx.annotation.NonNull;

/**
 * Holds bitmap, matrix and paint - what's required to draw to a Canvas.
 * Organizes graphics information for Player and Bullets
 */
public class Sprite {
    protected Bitmap bitmap;
    protected float skinWidth;
    protected Matrix matrix;
    protected Paint paint;

    protected float bitmapAngle = 0;

    public Sprite(Bitmap b, boolean isBullet) {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        bitmap = b;
        if(isBullet) {
            //Matrices are stored directly in AttackManager for increased speed during rendering
            //This sprite's "matrix" field stores a reference to the one in AttackManager
            matrix = AttackManager.getBulletMatrix();
        }
        else {
            matrix = new Matrix();
        }
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
