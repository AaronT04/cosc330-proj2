package com.at04.touchmovetest;

import static com.at04.touchmovetest.GameLoop.dt_sec;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
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
    private CountdownTimer invincibilityTimer;

    private AnimatedColorFilter hitAnimation =
            new AnimatedColorFilter(0f,new Range(0.5f, 1f), new Range(1f, 0.5f),  10, 0.3f);


    DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
    final int screenWidth = displayMetrics.widthPixels;
    final int screenHeight = displayMetrics.heightPixels;

    float oldX;
    float oldY;


    public Player(Bitmap b) {
        super(b, false);

        //the normal bounds rectangle for collision detection is very small
        //a larger rectangle has to be used for touch detection
        touchBounds = new RectF(centerX - radius, centerY + radius,
                centerX + radius, centerY - radius);

        setInitialPosition();
    }

    public void setInitialPosition() {
        pos.x = DisplaySize.screenWidth / 2f - radius;
        //DisplaySize.screenHeight actually sets you somewhat far below the edge of the screen
        //-450 moves you back up to the bottom edge
        pos.y = DisplaySize.screenHeight - 450 + radius * 2;
        centerX = pos.x + radius;
        centerY = pos.y + radius;
        oldX = pos.x;
        oldY = pos.y;
    }

    public void update() {
        touchBounds.set(centerX - radius, centerY + radius,
                centerX + radius, centerY - radius);
        setFacingAngle();
        oldX = pos.x;
        oldY = pos.y;

        super.update();
    }

    /**
     * Controls the animation of the head turning when moving horizontally
     */
    private void setFacingAngle() {
        float dy = pos.y - oldY;
        float dx = pos.x - oldX;
        if(!((dy == 0) && (dx == 0))) { //If moving
            float facingAngle =(float)Math.copySign(
                    Math.max(                               //Angle can't be less than -45
                            Math.min(                           //Angle can't be greater than 45
                                    Math.toDegrees(Math.atan((dy) / (dx)))  //atan gets angle from y and x
                                            //convert from "unit circle" angle to "directional" angle:
                                            //we want 0 degrees to mean up, not left, so +90
                                            + 90,
                                    45),
                            -45),
                    dx);//Set the sign of the angle to direction of x movement (is this necessary?)
            angV = facingAngle - sprite.bitmapAngle; //Angular velocity - change in angle
            float angA = 1; //Angular acceleration - makes the animation smoother
            sprite.bitmapAngle += angV * angA * dt_sec;
        }
        else {
            sprite.bitmapAngle = 0; //Snap to 0 degrees if not moving
        }
    }


    /**
     * GameView dispatches touch events to Player via this method
     */
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isGrabbed = (checkIfTouched(e.getX(), e.getY()));
            case MotionEvent.ACTION_MOVE:
                if (isGrabbed) {    //Don't move if not grabbed
                    pos.x = e.getX() - radius;
                    pos.y = e.getY() - radius - 150; //Set player above finger
                }
                return true;
            case MotionEvent.ACTION_UP:
                isGrabbed = false;
        }
        return false;
    }

    public void registerInvincibilityTimer(CountdownTimer iTimer) {
        this.invincibilityTimer = iTimer;
    }

    @Override
    public void draw(Canvas canvas) {
        if(invincibilityTimer.isActive()) {
            applyHitAnimation(sprite.paint);
        }
        else {
           setUnmodified(sprite.paint);
        }
        super.draw(canvas);
    }

    private void setUnmodified(Paint p) {
        p.setAlpha(255);
        p.setColorFilter(null);
    }
    private void applyHitAnimation(Paint p) {
        p.setAlpha(5); //Make the sprite transparent
        //Update color using hitAnimation
        p.setColorFilter(hitAnimation.getColor(invincibilityTimer));
        //Flicker for 10% of frames
        if(invincibilityTimer.getTimeElapsed() % 100 < 90) {
            p.setAlpha(255); //Make the sprite opaque
        }
    }

    private boolean checkIfTouched(float touchX, float touchY) {
        if (!((touchBounds.left > touchX || touchBounds.right < touchX
                || touchBounds.bottom > touchY || touchBounds.top < touchY)))
            return true;
        return false;
    }

    //Unused methods from GestureDetector.OnGestureListener interface

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

