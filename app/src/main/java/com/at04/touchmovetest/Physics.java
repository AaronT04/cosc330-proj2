package com.at04.touchmovetest;

import android.graphics.RectF;

public class Physics {
    public static final Position FAR_AWAY = new Position(-50000, -50000);
    float spd = 0;
    float angle = 0;
    float angV = 0;
    Position pos = new Position(FAR_AWAY.x, FAR_AWAY.y);
    RectF bounds = null;
    float centerX = 0;
    float centerY = 0;
    float radius = 0;

}
