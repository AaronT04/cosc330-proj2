package com.at04.touchmovetest;

import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.util.Log;

/**
 *
 * Used to animate a bitmap's color. Specifically, it's used for the hit animation.
 * It works by storing an array of color filters. which is accessed based on the value of a
 * CountdownTimer passed to getColor(), which controls the animation.
 */
public class AnimatedColorFilter {
    private LightingColorFilter[] colorFilters;
    private float timePerSwitchMs;
    private int curIdx = 0;

    public AnimatedColorFilter(float hue, Range satRange, Range valRange, int numColors, float timeSec) {
        if(numColors < 2) {numColors = 2;}
        timePerSwitchMs = (timeSec / numColors) * 1000;
        Log.d("timePerSwitch", String.valueOf(timePerSwitchMs));

        colorFilters = new LightingColorFilter[numColors];
        int numSegments = numColors - 1;

        for(int i = 0; i < colorFilters.length; i++) {
            float curSaturation = satRange.getStep(i, numSegments, 1);
            float curValue = valRange.getStep(i, numSegments, -1);
            colorFilters[i] = new LightingColorFilter(Color.HSVToColor
                    (new float[]{hue,
                            curSaturation,
                            curValue}), //Brightness
                    0);
        }
    }

    public LightingColorFilter getColor(CountdownTimer timer) {
        if(!timer.isOver()) {
            curIdx = (int)Math.floor(timer.getTimeElapsed() / timePerSwitchMs);
            return colorFilters[curIdx % colorFilters.length];
        }
        return null;
    }
}
