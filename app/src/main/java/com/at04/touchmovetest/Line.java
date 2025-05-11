package com.at04.touchmovetest;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Line implements Serializable {
    public Range x;
    public Range y;
    public Line() {}
    public Line(Range x, float y) {
        this.x = x;
        this.y = new Range(y, y);
    }
    public Line(float x, Range y) {
        this.x = new Range(x, x);
        this.y = y;
    }
    public Point[] getSegmentPoints(int numSegments) {
        Point[] segments = new Point[numSegments];
        for(int i = 0; i < numSegments; i++) {
            segments[i] = new Point(x.getStep(i, numSegments, 1),
                                    y.getStep(i, numSegments, 1));
        }
        return segments;
    }
}
