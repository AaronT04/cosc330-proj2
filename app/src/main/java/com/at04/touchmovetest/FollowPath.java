package com.at04.touchmovetest;

public class FollowPath extends BulletPath{

    float spd;
    float angle;
    float delta_ms = GameLoop.delta_ms;
    Position targetPos;
    Position currentPos;
    private Flag wrappedFlag;
    private boolean wrapEnabled;
    private static final int offsetSizeX = 500;
    private static final int offsetSizeY = 1000;
    private float offsetAmtX;
    private float offsetAmtY;
    protected void setAngleToTarget() {
        angle = (float)Math.atan2((targetPos.y - currentPos.y),(targetPos.x + offsetAmtX - currentPos.x));
    }
    public FollowPath(Position currentPos, Position targetPos, float spd, int idx, int count, boolean wrapEnabled) {
        this.spd = spd * 200;
        this.currentPos = currentPos;

        offsetAmtX = ((float)idx / count) * offsetSizeX;
        offsetAmtY = ((float)idx / count) * offsetSizeY;
        currentPos.y += offsetAmtY;

        this.targetPos = targetPos;
        setAngleToTarget();

        wrappedFlag = new Flag(false);
        this.wrapEnabled = wrapEnabled;
    }
    public void move() {
        currentPos.x += spd * delta_ms * (float)Math.cos(angle);
        currentPos.y += spd * delta_ms * (float)Math.sin(angle);
    }

    public void updateTarget(Position targetPos) {
        this.targetPos = targetPos;
        setAngleToTarget();
    }

    public void update(Bullet b) {
        move();
        if(wrapEnabled) {
            wrapScreenHorizontal(currentPos, b.radius, b.skinWidth);
            wrapScreenVertical(currentPos, b.radius, b.skinWidth);
            wrappedFlag.check(() -> {
                setAngleToTarget();
            });
        }

        b.pos.x = currentPos.x;
        b.pos.y = currentPos.y;
    }

    public void wrapScreenHorizontal(Position pos, float radius, float skinWidth) {
        if(pos.x >  (radius * 2) + DisplaySize.screenWidth) {
            pos.x = 0 - radius * 2 + skinWidth;
            //wrappedFlag.set();
        }
        else if(pos.x < 0 - (radius * 2) ) {
            pos.x = DisplaySize.screenWidth + radius * 2 - skinWidth;
            //wrappedFlag.set();
        }
    }
    public void wrapScreenVertical(Position pos, float radius, float skinWidth) {
        if(pos.y >  (radius * 2) + DisplaySize.screenHeight) {
            pos.y = 0 +
                    (pos.y - (radius * 2 + DisplaySize.screenHeight)) //accounts for amt traveled past the screen edge
                    - radius * 2 + skinWidth; //account for sprite height
            wrappedFlag.set();
        }
        else if(pos.y < 0 - (radius * 2) ) {
            pos.y = DisplaySize.screenHeight + radius * 2 - skinWidth;
            wrappedFlag.set();
        }
    }

}
