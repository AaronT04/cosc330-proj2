package com.at04.touchmovetest;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.TextView;
import android.view.View;

public class GameLoop extends Thread {
    TextView testDisplay;
    TextView hitTimerDisplay;
    private SurfaceView view;
    Canvas canvas;
    public static final long FPS = 45;
    private static final long ticksPS = 1000 / FPS;


    //dt_sec: This is the time in seconds that passes each frame
    public static final float dt_sec = (float)1000 / FPS / 1000;

    public static final long hitbuffer_ms = 500;
    private CountdownTimer hitTimer = new CountdownTimer(hitbuffer_ms);
    private Timer profileTimer = new Timer();
    private long startTime;
    private long sleepTime;
    GameModel model;


    private float lagPercent;
    private long totalMs = 0;
    private long laggedMs = 0;
    public static boolean running;
    public void registerView(GameView view) {
        this.view = view;
    }
    public void registerModel(GameModel model) {
        this.model = model;
    }
    public void setRunning(boolean b) {
        running = b;
    }


    public void run() {
        while(running) {
            startTime = System.currentTimeMillis();
            //start
            //profileTimer.start();
            //handleCollision();
            model.update();
            //profileTimer.debugStop("game Logic");
            //profileTimer.start();
            draw();
            //profileTimer.debugStop("draw()");

            //end
            long time_elapsed = System.currentTimeMillis() - startTime;
            hitTimer.update((1000 / FPS));
            sleepTime = ticksPS - time_elapsed;
            //Log.d("frame length", String.valueOf(time_elapsed));
            //Log.d("lag amt", String.valueOf((time_elapsed - ticksPS)) + " ms");

            totalMs += ticksPS + Math.max(0, time_elapsed - ticksPS);
            laggedMs += Math.max(0, time_elapsed - ticksPS);
            if(totalMs > 10000) {
                totalMs = 1;
                laggedMs = 0;
            }
            lagPercent = 100 * ((float)(laggedMs + totalMs)/totalMs) - 100;

            //Log.d("lag%", String.valueOf(lagPercent) + "%");
            try {
                if (sleepTime > 0)
                    sleep(sleepTime);
                else
                    sleep(10);
            } catch (Exception ignore) {}
        }
    }

    private void draw() {
        try {
            canvas = view.getHolder().lockCanvas();
            synchronized (view.getHolder()) {

                model.draw(canvas);

            }
        } finally {
            if (canvas != null) {
                view.getHolder().unlockCanvasAndPost(canvas);
            }
        }
    }
}
