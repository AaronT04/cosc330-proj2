package com.at04.touchmovetest;

import static com.at04.touchmovetest.GameLoop.dt_sec;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Player extends PhysicsSprite implements GestureDetector.OnGestureListener{

    boolean isGrabbed = false;
    private GestureDetector gestureDetector;
    private RectF touchBounds;
    private CountdownTimer hitTimer;

    private AnimatedColorFilter hitFilter =
            new AnimatedColorFilter(0f,new Range(0.5f, 0.5f), new Range(1f, 1f),  3, 0.3f);


    DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
    final int screenWidth = displayMetrics.widthPixels;
    final int screenHeight = displayMetrics.heightPixels;

    float oldX;
    float oldY;


    public Player(Bitmap b) {
        super(b, false);

        touchBounds = new RectF(centerX - radius, centerY + radius,
                centerX + radius, centerY - radius);

        setInitialPosition();
    }

    public void setInitialPosition() {
        pos.x = screenWidth / 2 - radius;
        pos.y = screenHeight - 450 + radius * 2;
        centerX = pos.x + radius;
        centerY = pos.y + radius;
        oldX = pos.x;
        oldY = pos.y;
    }

    public void update() {
        touchBounds.set(centerX - radius, centerY + radius,
                centerX + radius, centerY - radius);
        float dy = pos.y - oldY;
        float dx = pos.x - oldX;
        if(!((dy == 0) && (dx == 0))) {
            float facingAngle =(float)Math.copySign(Math.max(Math.min(Math.toDegrees(Math.atan((dy) / (dx))) + 90, 45), -45), dx);
            angV = facingAngle - sprite.bitmapAngle;
            float angA = 3;
            sprite.bitmapAngle += angV * angA * dt_sec;
        }
        else {
            sprite.bitmapAngle = 0;
        }

        Log.d("bitmap angle", String.valueOf(sprite.bitmapAngle));
        oldX = pos.x;
        oldY = pos.y;

        super.update();
    }
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isGrabbed = (checkIfTouched(e.getX(), e.getY()));
            case MotionEvent.ACTION_MOVE:
                if (isGrabbed) {
                    pos.x = e.getX() - radius;
                    pos.y = e.getY() - radius - 150;
                }
                return true;
            case MotionEvent.ACTION_UP:
                isGrabbed = false;
        }
        return false;
    }

    public void registerHitTimer(CountdownTimer hitTimer) {
        this.hitTimer = hitTimer;
    }

    @Override
    public void draw(Canvas canvas) {
        if(hitTimer.isActive()) {

            sprite.paint.setAlpha(5);
            sprite.paint.setColorFilter(hitFilter.getColor(hitTimer));
            if(hitTimer.getTimeElapsed() % 100 < 90) {
                sprite.paint.setAlpha(255);
            }
        }
        else {
            sprite.paint.setAlpha(255);
            sprite.paint.setColorFilter(null);
        }
        super.draw(canvas);
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

