package com.at04.touchmovetest;

import android.graphics.Matrix;

public abstract class BasePathStrategy {
    Position startPos;
    Position currentPos;
    Position targetPos;
    float spd;
    float angle;
    float delta_ms = GameLoop.delta_ms;


    public BasePathStrategy() {
        startPos = null;
        currentPos = null;
        targetPos = null;
        spd = -1;
    }

    public BasePathStrategy(Position startPos, Position currentPos, Position targetPos, float spd) {
        this.startPos = startPos;
        this.currentPos = currentPos;
        this.targetPos = targetPos;
        this.spd = spd * 200;
        initialize();
    }

    protected void setAngleToTarget() {
        angle = (float)Math.atan2((targetPos.y - currentPos.y),(targetPos.x - currentPos.x));
    }
    public void setPosition(Position p) {
        p.x += (currentPos.x -p.x) * GameLoop.delta_ms;
        p.y += (currentPos.y -p.y) * GameLoop.delta_ms;
    }
    public void setPosition(Position p, Matrix m) {
        setPosition(p);
        m.postTranslate(p.x, p.y);
    }
    public void updateTarget(Position p) {
        targetPos.x = p.x;
        targetPos.y = p.y;
        setAngleToTarget();
    }

    protected abstract void initialize();
    public abstract void move();


}
