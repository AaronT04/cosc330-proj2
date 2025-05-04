package com.at04.touchmovetest;

public class Range {
    float min;
    float max;
    public Range(float min, float max) {
        this.min = min;
        this.max = max;
    }

    public float divide(int numSegments) {
        return (max - min) / numSegments;
    }
    public float getStep(int index, int numSegments, int dir) {
        if(dir == 1) {
            return min + (index * divide(numSegments));
        }
        else {
            return max - (index * divide(numSegments));
        }
    }
}
