package com.at04.touchmovetest;

import android.app.Activity;
import android.util.Log;
import android.widget.TextView;
import android.view.View;

public class GameLoop extends Thread {

    TextView testDisplay;
    TextView hitTimerDisplay;
    private static final long FPS = 24;
    private static final long ticksPS = 1000 / FPS;

    public static final float delta_ms = (float)FPS / 1000;

    public static final long hitbuffer_ms = 500;
    public int hitsLeft = 2;
    private Timer hitTimer = new Timer(hitbuffer_ms);
    private long startTime;
    private long sleepTime;
    GameModel model;
    public static boolean running;
    public GameLoop(GameModel model) {
        this.model = model;
    }
    public void setRunning(boolean b) {
        running = b;
    }

    public void registerTextView(View[] display) {
        this.testDisplay = (TextView)display[0];//td;
        this.hitTimerDisplay = (TextView)display[1];//ht;
    }

    public void run() {
        while(running) {
            startTime = System.currentTimeMillis();
            //start
            checkCollision();
            updateView();

            //end
            long time_elapsed = System.currentTimeMillis() - startTime;
            hitTimer.update((1000 / FPS));
            sleepTime = ticksPS - time_elapsed;
            try {
                if (sleepTime > 0)
                    sleep(sleepTime);
                else
                    sleep(10);
            } catch (Exception ignore) {}
        }
    }

    private void checkCollision() {
        boolean collision = checkAABBCollision(model.player, model.bullets);
        if(collision) {
            //Log.d("Collision", "HitsLeft: " + hitsLeft);
            if(!(hitTimer.isActive())) {
                hitsLeft--;
                hitTimer.setActive();
            }
            if(hitsLeft < 0) {
                //Log.d("hitsLeft < 0", "end()");
                model.context.end();}

        }
    }
    private void updateView() {
        ((Activity) model.context).runOnUiThread(() -> {
            //testDisplay.setText(collision?"hit":"not hit");
            hitTimerDisplay.setText(hitTimer.toString());
            model.bullets[0].setPlayerPos(model.player.getPos());
            model.bullets[0].rotateBitmap(15f);
            model.bullets[1].rotateBitmap(15f);
        });
    }
    private boolean checkAABBCollision(DraggableSquare p, Obstacle[] bullets) {
        for(int i = 0; i < bullets.length; i++) {
            Obstacle o = bullets[i];
            if (!(p.bounds.left > o.bounds.right || p.bounds.right < o.bounds.left
                    || p.bounds.bottom > o.bounds.top || p.bounds.top < o.bounds.bottom))
                return true;
        }
        return false;
    }
}
