package com.at04.touchmovetest;

public class LinearPath extends Path {
    Position currentPos;
    float angle;
    float spd;
    float dt_sec = GameLoop.dt_sec;
    boolean loopEnabled;
    public LinearPath(Position startPos, float angle, float spd, boolean loopEnabled) {
        this.currentPos = startPos;
        this.angle = angle;
        this.spd = spd * 200;
        this.loopEnabled = loopEnabled;
    }
    @Override
    public void move() {
        currentPos.y += spd * dt_sec * (float)Math.sin(angle);
    }

    @Override
    public void update(Bullet b) {
        move();
        if (loopEnabled) {
            wrapScreenVertical(currentPos, b.radius);
        }
        b.pos.x = currentPos.x;
        b.pos.y = currentPos.y;
    }

    public void wrapScreenVertical(Position pos, float radius) {
        if(pos.y >  (radius * 2) + DisplaySize.screenHeight) {
            pos.y = 0 +
                    (pos.y - (radius * 2 + DisplaySize.screenHeight)) //accounts for amt traveled past the screen edge
                    - radius * 2; //account for sprite height
        }
        else if(pos.y < 0 - (radius * 2) ) {
            pos.y = DisplaySize.screenHeight + radius * 2;
        }
    }

    @Override
    public void updateTarget(Position p) {
        //ignore
    }
}
