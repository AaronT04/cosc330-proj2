package com.at04.touchmovetest;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class FallAttack extends Attack {
    private final BulletInfo bulletInfo;
    private final boolean wrapEnabled;
    private static final int offsetSizeX = 500;
    private static final int offsetSizeY = 200;
    private final int offsetYDir;
    private Point playerPos; //set at runtime (after constructor)
    public FallAttack(BaseAttackInfo atk_init, boolean wrapEnabled, int offsetYDir) {
        super(atk_init);
        this.wrapEnabled = wrapEnabled;
        this.offsetYDir = (int)Math.copySign(1, offsetYDir);
        bulletInfo = new BulletInfo(count);
    }
    public FallAttack(BaseAttackInfo atk_init, List<AttackParameter> params) {
        super(atk_init);
        this.wrapEnabled = (boolean)params.get(0).unwrap();
        int offsetYDirTemp = (int)params.get(1).unwrap();
        this.offsetYDir = (int)Math.copySign(1, offsetYDirTemp);
        bulletInfo = new BulletInfo(count);
    }

    public void initialize() {
        for(int i = 0; i < count; i++) {
            //setup Bullet and BulletInfo fields based on index
            Point p = calcInitialPosition(i, count);
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

    public Point calcInitialPosition(int idx, int count) {
        return new Point(idx * ((float)DisplaySize.screenWidth - 200) / count, 0 - GameAssets.pinkStar.getHeight());
    }

    @Override
    public void registerPlayerPosition(Point p) {
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
                bulletInfo.removed[i] = true;
            }
        }

        if (bulletInfo.removed[i]) {
            Log.d("unloaded", String.valueOf(i));
            b.unloadAndReset();
            //bullets.remove(i);
        }
    }

    private void checkOffscreenHorizontal(int i, Point pos, float radius) {
        int wrapDir = 0;
        //Check whether bullet is offscreen, and which direction
        if(pos.x >  (radius * 2) + DisplaySize.screenWidth) {
            //Log.d("wrap", "hz1");
            wrapDir = 1;
            bulletInfo.wrapped[i] = true;
        }
        else if(pos.x < 0 - (radius * 2) ) {
            //Log.d("wrap", "hz-1");
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
    private void checkOffscreenVertical(int i, Point pos, float radius) {
        int wrapDir = 0;

        if(pos.y >  (radius * 2) + DisplaySize.screenHeight) {
            //Log.d("wrap", "v1");
            bulletInfo.wrapped[i] = true;
            wrapDir = 1;
        }
        else if(pos.y < 0 - (radius * 2) ) {
            //Log.d("wrap", "v-1");
            //bulletInfo.wrapped[i] = true;
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
        BaseAttackInfo atk_init = new BaseAttackInfo(this.count, this.spd, this.offsetSec);
        FallAttack copy = new FallAttack(atk_init, wrapEnabled, offsetYDir);
        copy.registerPlayerPosition(playerPos);
        copy.registerAttackManager(attackManager);
        return copy;
    }

    public static AttackInfo getInitializer(BaseAttackInfo baseAttackInfo, boolean wrapEnabled, int offsetYDir) {
        ArrayList<AttackParameter> params = new ArrayList<>();
        params.add(new AttackParameter("boolean", wrapEnabled, "wrapEnabled"));
        params.add(new AttackParameter("int", offsetYDir, "offsetYDir"));
        return new AttackInfo(AttackInfo.FALL_ATTACK, baseAttackInfo, params);
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