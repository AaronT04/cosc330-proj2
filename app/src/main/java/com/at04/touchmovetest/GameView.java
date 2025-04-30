package com.at04.touchmovetest;

import android.content.Context;
import android.gesture.Gesture;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class GameView extends SurfaceView implements GestureDetector.OnGestureListener, SurfaceHolder.Callback {
    GestureDetector gestureDetector;
    GameModel model;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
        setZOrderOnTop(true);
        getHolder().setFormat(PixelFormat.TRANSLUCENT);
        gestureDetector = new GestureDetector(context, this);
    }

    public void registerGameModel(GameModel gameModel) {
        this.model = gameModel;
    }

    public boolean onTouchEvent(MotionEvent e) {
        gestureDetector.onTouchEvent(e);
        if(model.player.onTouchEvent(e)) {
            invalidate();
            return true;
        };
        return false;
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        model.startGame();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        //this is where you would update size variables for the screen
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
    }

    /*
    The following are unused methods from the GestureDetector.OnGestureListener interface
     */

    @Override
    public boolean onDown(@NonNull MotionEvent e) {
        return false;
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
