package com.at04.touchmovetest;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Range implements Serializable {
    public float min;
    public float max;
    public Range() {}
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

    public Range add(Number num) {
        return new Range(this.min + (float)num, this.max + (float)num);
    }
}
