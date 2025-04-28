package com.at04.touchmovetest;

public class LinearPath extends BulletPath {
    Position currentPos;
    float angle;
    float spd;
    float delta_ms = GameLoop.delta_ms;
    boolean loopEnabled;
    public LinearPath(Position startPos, float angle, float spd, int idx, int count, boolean loopEnabled) {
        this.currentPos = startPos;
        this.angle = angle;
        this.spd = spd * 200;
        this.loopEnabled = loopEnabled;
    }
    @Override
    public void move() {
        currentPos.y += spd * delta_ms * (float)Math.sin(angle);
    }

    @Override
    public void update(Bullet b) {
        move();
        if (loopEnabled) {
            wrapScreenVertical(currentPos, b.radius, b.skinWidth);
        }
        b.pos.x = currentPos.x;
        b.pos.y = currentPos.y;
    }

    public void wrapScreenVertical(Position pos, float radius, float skinWidth) {
        if(pos.y >  (radius * 2) + DisplaySize.screenHeight) {
            pos.y = 0 +
                    (pos.y - (radius * 2 + DisplaySize.screenHeight)) //accounts for amt traveled past the screen edge
                    - radius * 2 + skinWidth; //account for sprite height
        }
        else if(pos.y < 0 - (radius * 2) ) {
            pos.y = DisplaySize.screenHeight + radius * 2 - skinWidth;
        }
    }

    @Override
    public void updateTarget(Position p) {
        //ignore
    }
}
