package com.at04.touchmovetest;

import android.util.Log;

import java.util.ArrayList;

public class FallAttack extends Attack {
    private Position playerPos;
    private BulletInfo bulletInfo;
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
        //Log.d("offsetYDir", String.valueOf(this.offsetYDir));
        //initialize();
    }
    public FallAttack(int count, float offsetSec) {
        super(count, offsetSec);
        this.wrapEnabled = false;
        this.spd = 10f;
        bulletInfo = new BulletInfo(count);
        bullets = new ArrayList<>();
        //initialize();
    }
    public Attack copy(){
        //Log.d("fallAttack.copy()", "");
        FallAttack copy = new FallAttack(count, offsetSec, wrapEnabled, spd, offsetYDir);
        copy.registerPlayerPosition(playerPos);
        copy.registerAttackManager(attackManager);
        return copy;
    }
    public void initialize() {
        for(int i = 0; i < count; i++) {
            Position p = calcInitialPosition(i, count);

            Bullet b = AttackManager.bullets[AttackManager.bulletIdx % AttackManager.MAX_BULLETS];
            b.load(p, spd, 0);
            //Bullet b = AttackManager.bullets[AttackManager.bulletIdx % AttackManager.MAX_BULLETS];
            bulletInfo.wrapped[i] = false;
            bulletInfo.offsetAmtX[i] = Math.copySign(((float)i / count) * offsetSizeX, i - (count / 2));
            bulletInfo.offsetAmtY[i] = ((float)Math.abs((count / 2) - i) / (count / 2) * offsetSizeY);
            b.pos.y += offsetYDir * bulletInfo.offsetAmtY[i];
            bullets.add(b);
            setAngleToTarget(i);

            AttackManager.bulletIdx++;

            if(AttackManager.bulletIdx == AttackManager.MAX_BULLETS) {
                Log.d("bulletIdx == 256", "");
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

    public void update() {
        if(bullets != null) {
            super.update();
            boolean canRemove;
            for(int i = 0; i < bullets.size(); i++) {
                canRemove = false;
                Bullet b = bullets.get(i);
                b.move();
                checkOffscreenHorizontal(i, b.pos, b.radius);
                checkOffscreenVertical(i, b.pos, b.radius);
                if (bulletInfo.wrapped[i]) {
                    if (wrapEnabled) {
                        setAngleToTarget(i);
                        bulletInfo.wrapped[i] = false;
                    } else {
                        canRemove = true;
                    }
                }
                b.update();
                if (canRemove) {
                    b.unloadAndReset();
                    bullets.remove(i);
                    i--;
                }
                if (bullets.isEmpty()) {
                    attackManager.notifyAttackOffscreen(this);
                }
            }
        }

    }
    private void checkOffscreenHorizontal(int i, Position pos, float radius) {
        int wrapDir = 0;
        //Check whether bullet is offscreen, and which direction
        if(pos.x >  (radius * 2) + DisplaySize.screenWidth) {
            wrapDir = 1;
            bulletInfo.wrapped[i] = true;
        }
        else if(pos.x < 0 - (radius * 2) ) {
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
            bulletInfo.wrapped[i] = true;
            wrapDir = 1;
        }
        else if(pos.y < 0 - (radius * 2) ) {
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
    static class BulletInfo {
        boolean[] wrapped;
        float[] offsetAmtX;
        float[] offsetAmtY;
        int liveBullets;

        public BulletInfo(int count) {
            liveBullets = count;
            wrapped = new boolean[count];
            offsetAmtX = new float[count];
            offsetAmtY = new float[count];
        }
    }
}