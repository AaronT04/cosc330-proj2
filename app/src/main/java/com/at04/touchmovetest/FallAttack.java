package com.at04.touchmovetest;

import android.util.Log;

import java.util.ArrayList;

public class FallAttack extends Attack {
    private Position playerPos;
    private BulletInfo bulletInfo;
    private ArrayList<Bullet> toRemove = new ArrayList<>();
    private boolean wrapEnabled;
    private float spd;
    private static final int offsetSizeX = 500;
    private static final int offsetSizeY = 200;
    int offsetYDir;
    public FallAttack(int count, float offsetSec, boolean wrapEnabled, float spd, int offsetYDir) {
        super(count, offsetSec);
        this.wrapEnabled = wrapEnabled;
        this.spd = spd;
        this.offsetYDir = (int)Math.copySign(1, offsetYDir);
        bulletInfo = new BulletInfo(count);
        bullets = new ArrayList<>();
    }
    public FallAttack(int count, float offsetSec) {
        super(count, offsetSec);
        this.wrapEnabled = false;
        this.spd = 10f;
        bulletInfo = new BulletInfo(count);
        bullets = new ArrayList<>();
    }
    public void initialize() {
        for(int i = 0; i < count; i++) {
            //setup Bullet and BulletInfo fields based on index
            Position p = calcInitialPosition(i, count);
            bulletInfo.wrapped[i] = false;
            bulletInfo.removed[i] = false;
            bulletInfo.offsetAmtX[i] = Math.copySign(((float)i / count) * offsetSizeX, i - (count / 2));
            bulletInfo.offsetAmtY[i] = ((float)Math.abs((count / 2) - i) / (count / 2) * offsetSizeY);

            //Load bullet and add it to the array
            Bullet b = AttackManager.initializeBullet(GameAssets.pinkStar, p, spd, 0);
            bullets.add(b);

            //Apply setup to bullet
            b.pos.y += offsetYDir * bulletInfo.offsetAmtY[i];
            setAngleToTarget(i);
            if(count < 10) {
                b.setBitmapRotationSpeed(15f);
            }
        }
    }

    public Position calcInitialPosition(int idx, int count) {
        return new Position(idx * ((float)DisplaySize.screenWidth - 200) / count, 0);
    }

    @Override
    public void registerPlayerPosition(Position p) {
        this.playerPos = p;
    }

    public void attackUpdate() {
        if(bullets != null) {
            for(int i = 0; i < bullets.size(); i++) {
                if(!(bulletInfo.removed[i])) {
                    Bullet b = bullets.get(i);
                    b.move();
                    checkBulletOffscreen(i);
                    b.update();
                    checkAttackOffscreen();
                }
            }
        }
    }

    private void checkAttackOffscreen() {
        int numRemoved = 0;
        for(int j = 0; j < count; j++) {
            if(bulletInfo.removed[j])
                numRemoved++;
        }
        if(numRemoved == count) {
            attackManager.notifyAttackOffscreen(this);
        }
    }
    private void checkBulletOffscreen(int i) {
        Bullet b = bullets.get(i);
        checkOffscreenHorizontal(i, b.pos, b.radius);
        checkOffscreenVertical(i, b.pos, b.radius);
        if (bulletInfo.wrapped[i]) {
            if (wrapEnabled) {
                setAngleToTarget(i);
                bulletInfo.wrapped[i] = false;
            } else {
                toRemove.add(b);
                bulletInfo.removed[i] = true;
            }
        }

        if (bulletInfo.removed[i]) {
            Log.d("unloaded", String.valueOf(i));
            b.unloadAndReset();
            //bullets.remove(i);
        }
    }

    private void checkOffscreenHorizontal(int i, Position pos, float radius) {
        int wrapDir = 0;
        //Check whether bullet is offscreen, and which direction
        if(pos.x >  (radius * 2) + DisplaySize.screenWidth) {
            Log.d("wrap", "hz1");
            wrapDir = 1;
            bulletInfo.wrapped[i] = true;
        }
        else if(pos.x < 0 - (radius * 2) ) {
            Log.d("wrap", "hz-1");
            wrapDir = - 1;
            bulletInfo.wrapped[i] = true;
        }
        //if wrap enabled, wrap around to other side
        if(wrapEnabled) {
            if(wrapDir == 1) {
                pos.x = 0 - radius * 2;
            }
            else if(wrapDir == -1) {
                pos.x = DisplaySize.screenWidth + radius * 2;
            }
        }
    }
    private void checkOffscreenVertical(int i, Position pos, float radius) {
        int wrapDir = 0;

        if(pos.y >  (radius * 2) + DisplaySize.screenHeight) {
            Log.d("wrap", "v1");
            bulletInfo.wrapped[i] = true;
            wrapDir = 1;
        }
        else if(pos.y < 0 - (radius * 2) ) {
            Log.d("wrap", "v-1");
            bulletInfo.wrapped[i] = true;
            wrapDir = -1;
        }
        if(wrapEnabled) {
            if(wrapDir == 1) {
                pos.y = 0 +
                        (pos.y - (radius * 2 + DisplaySize.screenHeight))
                        - radius * 2;

            }
            else if(wrapDir == -1) {
                pos.y = DisplaySize.screenHeight + radius * 2;
            }
        }
    }

    private void setAngleToTarget(int i) {
        Bullet b = bullets.get(i);
        b.angle = (float)Math.atan2((playerPos.y - b.pos.y),(playerPos.x + bulletInfo.offsetAmtX[i] - b.pos.x));
    }

    public Attack copy(){
        FallAttack copy = new FallAttack(count, offsetSec, wrapEnabled, spd, offsetYDir);
        copy.registerPlayerPosition(playerPos);
        copy.registerAttackManager(attackManager);
        return copy;
    }
    static class BulletInfo {
        boolean[] wrapped;
        float[] offsetAmtX;
        float[] offsetAmtY;
        int liveBullets;

        boolean[] removed;

        public BulletInfo(int count) {
            liveBullets = count;
            wrapped = new boolean[count];
            removed = new boolean[count];
            offsetAmtX = new float[count];
            offsetAmtY = new float[count];
        }
    }
}