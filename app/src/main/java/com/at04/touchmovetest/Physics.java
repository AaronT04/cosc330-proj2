package com.at04.touchmovetest;

import android.graphics.RectF;

public class Physics {
    float spd = 0;
    float angle = 0;
    float angV = 0;
    Position pos = new Position(0, 0);
    Position targetPos = null;
    RectF bounds = null;
    float centerX = 0;
    float centerY = 0;
    float radius = 0;
    public Position getPos() {
        return pos;
    }
}
