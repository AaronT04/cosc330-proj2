package com.at04.touchmovetest;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;

/**
 * Draws the health bar.
 */
public class HealthBar {
    RectF innerBounds;
    RectF drawArea;
    Paint emptyPaint;
    Paint healthPaint;
    LinearGradient gradient;
    Bitmap outerImage;
    int maxHitCount;
    int currentHitCount;
    int hzInset;
    int vInset;
    int height;
    Range healthBarWidth;
    public HealthBar(int hzInset, int vInset, int height, int maxHitCount) {
        this.hzInset = hzInset;
        this.vInset = vInset;
        this.height = height;
        healthBarWidth = new Range(hzInset, DisplaySize.screenWidth - hzInset);
        this.maxHitCount = maxHitCount;
        emptyPaint = new Paint();
        emptyPaint.setColor(Color.BLACK);
        emptyPaint.setAlpha(128);
        innerBounds = new RectF(
                hzInset,
                vInset,
                DisplaySize.screenWidth - hzInset,
                vInset + height
                );
        drawArea = new RectF(innerBounds);
        healthPaint = new Paint();
        healthPaint.setColor(Color.WHITE);
        gradient = new LinearGradient(
                drawArea.left, drawArea.top,
                drawArea.left, drawArea.top + (drawArea.top - drawArea.bottom) / 2,
                Color.HSVToColor(new float[] {132, 0.5f, 1f}),
                Color.HSVToColor(new float[] {132, 1f, 0.8f}),
                Shader.TileMode.MIRROR
                );
        healthPaint.setShader(gradient);
    }

    public void update(int newHitCount) {
        if(newHitCount != currentHitCount) {
            currentHitCount = newHitCount;
            drawArea = new RectF(
                    hzInset,
                    vInset,
                    healthBarWidth.getStep(currentHitCount, maxHitCount, 1),
                    vInset + height
            );
        }
    }

    public void draw(Canvas canvas) {
        //canvas.drawBitmap(...)
        canvas.drawRect(innerBounds, emptyPaint);
        canvas.drawRect(drawArea, healthPaint);
    }
}
