package com.at04.touchmovetest;

import android.util.Log;

public class Timer {
    private long start_time;
    private boolean running = false;
    public void start() {
        if(!running) {
            running = true;
            start_time = System.nanoTime();
        }
    }
    public float stop() {
        if(running) {
            running = false;
            return (float)(System.nanoTime() - start_time) / 1000000;
        }
        return -1;
    }
    public void debugStop(String message) {
        if(running) {
            running = false;
            Log.d(message, String.valueOf((float)(System.nanoTime() - start_time) / 1000000) + "ms");
        }
    }
}
