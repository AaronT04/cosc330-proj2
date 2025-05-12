package com.at04.touchmovetest;

/**
 * Used to count down time. <br/>
 * Currently, the timer must be updated manually. However, it could be made
 * an observer to GameLoop, or to a universal Clock class if timers are needed outside of a Level activity.
 * In addition, classes could implement a TimerListener interface to take action on a timer's completion.
 */
public class CountdownTimer {
    private long length_ms;
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
    public void startTimer() {
        time_ms = length_ms;
        timerActive = true;
    }

    public void updateTimeElapsed(long time_elapsed){
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

    public long getTimeElapsed() {
        return length_ms - time_ms;
    }

    /**
     * TODO: instead of "isActive", use something like "isOver"
     * @return true if the timer has not expired yet.
     */
    public boolean isActive() {
        return timerActive;
    }
    public String toString() {
        return Long.toString(time_ms);
    }
}
