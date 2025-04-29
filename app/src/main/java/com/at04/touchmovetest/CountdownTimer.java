package com.at04.touchmovetest;

public class CountdownTimer {
    private static long length_ms;
    private long time_ms;
    private boolean timerActive = false;

    public CountdownTimer(long length) {
        length_ms = length;
        time_ms = length;
    }

    public void reset() {
        timerActive = false;
        time_ms = length_ms;
    }
    public void setActive() {
        time_ms = length_ms;
        timerActive = true;
    }

    public void update(long time_elapsed){
        if(timerActive) {
            time_ms -= time_elapsed;
            if(time_ms <= 0) {
                timerActive = false;
            }
        }
        else {
            time_ms = 0;
        }
    }
    public boolean isActive() {
        return timerActive;
    }
    public String toString() {
        return Long.toString(time_ms);
    }
}
