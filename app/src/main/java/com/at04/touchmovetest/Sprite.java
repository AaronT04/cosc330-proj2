package com.at04.touchmovetest;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.RectF;

public class Sprite {
    private Bitmap bitmap = null;
    private RectF bounds;
    private float centerX;
    private float centerY;
    private float radius;
    private int percentage = 0;
    private Position pos;
    public Sprite() {
        bitmap = null;
        bounds = null;
        pos = new Position(0, 0);
    }
}
