package com.at04.touchmovetest;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DraggableSquare extends View implements GestureDetector.OnGestureListener{

    private Bitmap bitmap = null;
    public RectF bounds;
    public float skinWidth;
    private Paint paint;
    private float centerX;
    private float centerY;
    private float radius;
    private int percentage = 25;

    private GestureDetector gestureDetector;

    private Position pos = new Position(0, 0);

    DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
    final int screenWidth = displayMetrics.widthPixels;
    final int screenHeight = displayMetrics.heightPixels;

    public DraggableSquare(Context context) {
        this(context, null);
    }

    public DraggableSquare(Context context, AttributeSet attrs) {
        super(context, attrs);

        //bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.head);
        bounds = new RectF();
        gestureDetector = new GestureDetector(context, this);
        paint = new Paint();

        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
    }

    public void setBitmap(Bitmap b) {
        this.bitmap = b;
        invalidate();
    }

    public void setInitialPosition() {
        pos.x = screenWidth / 2 - radius;
        pos.y = screenHeight - 450 + radius * 2;
        centerX = pos.x + radius;
        centerY = pos.y + radius;
    }

    @Override
    public void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        float hitRadius = radius - skinWidth;
        if(bitmap != null) {
            bounds.set(centerX - hitRadius, centerY + hitRadius, centerX + hitRadius, centerY - hitRadius);
            //Log.d("Radius", Float.toString(radius));
            //Log.d("Bounds", bounds.toString());
            canvas.drawBitmap(bitmap, pos.x, pos.y, paint);
            canvas.drawRect(bounds, paint);
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

        centerX = (float)width / 2;
        centerY = (float)height / 2;
        float minAxis = Math.min(width, height);
        radius = minAxis / 2f;// * ((100f - percentage) / 100f);
        skinWidth = radius - (minAxis / 2f * ((100f - percentage) / 100f));
        setInitialPosition();
    }

    public boolean onTouchEvent(MotionEvent e) {
        gestureDetector.onTouchEvent(e);
        Log.d("onTouchEvent", "Pressure: " + e.getPressure());
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                centerX = e.getX();
                centerY = e.getY();
                pos.x = centerX - radius;
                pos.y = centerY - radius;
                invalidate();
                return true;
        }
        return false;
    }

    public Position getPos() {
        return pos;
    }

    @Override
    public boolean onDown(@NonNull MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(@NonNull MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(@NonNull MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(@NonNull MotionEvent e) {

    }

    @Override
    public boolean onFling(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}

