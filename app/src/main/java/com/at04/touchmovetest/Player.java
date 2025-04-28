package com.at04.touchmovetest;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Player extends Sprite implements GestureDetector.OnGestureListener{

    boolean isGrabbed = false;
    private GestureDetector gestureDetector;
    private RectF touchBounds;

    DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
    final int screenWidth = displayMetrics.widthPixels;
    final int screenHeight = displayMetrics.heightPixels;


    public Player(Bitmap b) {
        super(b, 75);
        this.bitmap = b;

        touchBounds = new RectF(centerX - radius, centerY + radius,
                centerX + radius, centerY - radius);

        setInitialPosition();
        rotateBitmap(180f);
        matrix.postTranslate(pos.x, pos.y);
    }

    public void setInitialPosition() {
        pos.x = screenWidth / 2 - radius;
        pos.y = screenHeight - 450 + radius * 2;
        centerX = pos.x + radius;
        centerY = pos.y + radius;
    }

    public void update() {
        touchBounds.set(centerX - radius, centerY + radius,
                centerX + radius, centerY - radius);
        super.update();
    }

    @Override
    public void draw(Canvas canvas) {
        if(bitmap != null) {
            canvas.drawRect(bounds, paint);
            canvas.drawBitmap(bitmap, matrix, paint);
        }
    }

    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isGrabbed = (checkIfTouched(e.getX(), e.getY()));
            case MotionEvent.ACTION_MOVE:
                if (isGrabbed) {
                    pos.x = e.getX() - radius;
                    pos.y = e.getY() - radius - 100;
                }
                return true;
            case MotionEvent.ACTION_UP:
                isGrabbed = false;
        }
        return false;
    }

    private boolean checkIfTouched(float touchX, float touchY) {
        if (!((touchBounds.left > touchX || touchBounds.right < touchX
                || touchBounds.bottom > touchY || touchBounds.top < touchY)))
            return true;
        return false;
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

