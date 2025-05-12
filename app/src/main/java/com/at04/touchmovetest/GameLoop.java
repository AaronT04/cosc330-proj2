package com.at04.touchmovetest;

import android.graphics.Canvas;
import android.view.SurfaceView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Runs the game at a certain frame rate by calling model.update() to update the game world
 * and draw() to render the frame. <br/><br/>
 * The structure of this class was copied from https://github.com/erz05/JoyStick
 */
public class GameLoop extends Thread {
    private SurfaceView view;
    Canvas canvas;
    public static final long FPS = 45;
    private static final long ticksPS = 1000 / FPS;


    //dt_sec: This is the time in seconds that passes each frame
    public static final float dt_sec = (float)1000 / FPS / 1000;

    private static ArrayList<CountdownTimer> timers = new ArrayList<>();

    //profileTimer can be used to log the amount of time spent in a method
    //private Timer profileTimer = new Timer();
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
            //profileTimer.start();
            model.update();
            //profileTimer.debugStop("game Logic");
            //profileTimer.start();
            draw();
            //profileTimer.debugStop("draw()");

            long time_elapsed = System.currentTimeMillis() - startTime;
            sleepTime = ticksPS - time_elapsed;
            //Log.d("frame length", String.valueOf(time_elapsed));
            //Log.d("lag amt", String.valueOf((time_elapsed - ticksPS)) + " ms");

            totalMs += ticksPS + Math.max(0, time_elapsed - ticksPS);
            laggedMs += Math.max(0, time_elapsed - ticksPS);
            if(totalMs > 10000) { //reset the lag percentage calculation every ten seconds
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
            //canvas is locked - cannot be accessed from other threads while drawing
            canvas = view.getHolder().lockCanvas();
            synchronized (view.getHolder()) { //???
                model.draw(canvas); /*TODO: ideally, all graphic elements would be stored in
                a GameView class for faster drawing, similar to how AttackManager stores
                matrices and bitmaps for every bullet*/
            }
        } finally {
            if (canvas != null) {
                //without this, the game can crash when you press the home button
                view.getHolder().unlockCanvasAndPost(canvas);
            }
        }
    }
}
