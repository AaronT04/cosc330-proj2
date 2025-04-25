package com.at04.touchmovetest;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;

import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.content.Context;

import androidx.annotation.NonNull;

public class Obstacle extends View {
    private Bitmap bitmap = null;
    public RectF bounds;
    private Paint paint;
    private float centerX;
    private float centerY;
    private float radius;
    private int percentage = 25;
    private float angle = 0;
    private float skinWidth;
    private Position playerPos;
    private Flag wrappedFlag;
    private BasePathStrategy pathStrategy;

    DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
    final int screenWidth = displayMetrics.widthPixels;
    final int screenHeight = displayMetrics.heightPixels;


    private Matrix matrix;
    private Position pos = new Position(200, 200);

    public Obstacle(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        paint = new Paint();
        bounds = new RectF();
        matrix = new Matrix();
        matrix.postTranslate(pos.x, pos.y);

        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);

        pathStrategy = new LinearPath(pos, new Position(pos.x, pos.y + 1), 15f);

        playerPos = new Position(500, 500);
        wrappedFlag = new Flag(false);
    }

    public void setBitmap(Bitmap b) {
        this.bitmap = b;
        invalidate();
    }

    public void onDraw(@NonNull Canvas canvas) {
        applyMovement();
        wrapScreenHorizontal();
        wrapScreenVertical();
        wrappedFlag.check( () -> {pathStrategy.updateTarget(playerPos);});
        float hitRadius = radius - skinWidth;
        centerX = pos.x + radius;
        centerY = pos.y + radius;
        bounds.set(centerX - hitRadius, centerY + hitRadius, centerX + hitRadius, centerY - hitRadius);
        canvas.drawBitmap(bitmap, matrix, paint);
        //canvas.drawBitmap(bitmap, pos.x, pos.y, paint);
        canvas.drawRect(bounds, paint);
    }

    public void wrapScreenHorizontal() {
        if(pos.x >  (radius * 2) + screenWidth) {
            pos.x = 0 - radius * 2 + skinWidth;
            //wrappedFlag.set();
        }
        else if(pos.x < 0 - (radius * 2) ) {
            pos.x = screenWidth + radius * 2 - skinWidth;
            //wrappedFlag.set();
        }
    }
    public void wrapScreenVertical() {
        if(pos.y >  (radius * 2) + screenHeight) {
            pos.y = 0 - radius * 2 + skinWidth;
            wrappedFlag.set();
        }
        else if(pos.y < 0 - (radius * 2) ) {
            pos.y = screenHeight + radius * 2 - skinWidth;
            wrappedFlag.set();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //Log.d("DraggableSquare", "onMeasure");
        int width;
        int height;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(bitmap != null) {
            width = bitmap.getWidth();
            height = bitmap.getHeight();
            //setMeasuredDimension(width, height);
        }
        else {
            Log.d("onMeasure","bitmap == null");
            width = MeasureSpec.getSize(widthMeasureSpec);
            height = MeasureSpec.getSize(heightMeasureSpec);
        }
        centerX = pos.x + (float)width / 2;
        centerY = pos.y + (float)height / 2;
        float minAxis = Math.min(width, height);
        radius = minAxis / 2f;// * ((100f - percentage) / 100f);
        skinWidth = radius - (minAxis / 2f * ((100f - percentage) / 100f));
    }
    public void rotateBitmap(float angle) {
        this.angle += angle;// * GameLoop.delta_ms;
        if(this.angle > 360) {
            this.angle -= 360;
        }
    }

    public void setPosition(Position p) {
        pos.x = p.x;
        pos.y = p.y;
    }

    public void setPlayerPos(Position playerPos) {
        this.playerPos = playerPos;
    }

    public void applyMovement() {
        pathStrategy.move();
        pathStrategy.setPosition(pos);

        matrix.reset();
        matrix.postTranslate(-radius, -radius);
        matrix.postRotate(this.angle);
        matrix.postTranslate(pos.x + radius, pos.y + radius);
        invalidate();
    }


}
