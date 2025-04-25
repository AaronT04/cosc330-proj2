package com.at04.touchmovetest;
import android.util.Log;
public class LinearPath extends BasePathStrategy{

    protected void initialize() {
        setAngleToTarget();
    }

    public LinearPath(Position start, Position target, float spd) {
        super(start, start, target, spd);
    }
    public void move() {
        currentPos.x += spd * delta_ms * (float)Math.cos(angle);
        currentPos.y += spd * delta_ms * (float)Math.sin(angle);
        //Log.d("LinearPath : currentPos", currentPos.toString());
        //Log.d("LinearPath : angle", Float.toString(spd));
    }

}
