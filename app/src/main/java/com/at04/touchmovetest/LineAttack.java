package com.at04.touchmovetest;

import java.util.ArrayList;

public class LineAttack extends Attack {

    private float hzInsetRatio;
    private float spd;
    @Override
    public Position calcInitialPosition(int idx, int count) {
        float hzInset = hzInsetRatio * (float)(DisplaySize.screenWidth - 200);
        float offsetFromLeftPerIndex = ((float)(DisplaySize.screenWidth - 200) - (hzInset * 2)) / (count - 1);
        return new Position(hzInset + offsetFromLeftPerIndex * idx, 0);
    }

    public LineAttack(int count, float offsetSec, float spd, float hzInsetRatio) {
        super(count, offsetSec);
        this.hzInsetRatio = hzInsetRatio;
        this.spd = spd;
        bullets = new ArrayList<>();
    }
    public LineAttack(int count, float offsetSec) {
        super(count, offsetSec);
        this.hzInsetRatio = 0;
        this.spd = 10f;
        bullets = new ArrayList<>();
    }
    public void initialize() {
        for(int i = 0; i < count; i++) {
            Position p = calcInitialPosition(i, count);
            Bullet b = AttackManager.initializeBullet(GameAssets.arrow, p, spd, (float)Math.toRadians(90));
            bullets.add(b);
        }
    }
    public void attackUpdate() {
        if(bullets != null) {
            for(int i = 0; i < bullets.size(); i++) {
                Bullet b = bullets.get(i);
                b.move();
                if(checkOffscreen(i)) {
                    b.unloadAndReset();
                    bullets.remove(i);
                    i--;
                }
                b.update();
                if (bullets.isEmpty()) {
                    attackManager.notifyAttackOffscreen(this);
                }
            }
        }
    }
    public boolean checkOffscreen(int i) {
        Bullet b = bullets.get(i);
        if (b.pos.y > (b.radius * 2) + DisplaySize.screenHeight) {
            return true;
        }
        return false;
    }

    public Attack copy(){
        LineAttack copy = new LineAttack(count, offsetSec, spd, hzInsetRatio);
        copy.registerPlayerPosition(null);
        copy.registerAttackManager(attackManager);
        return copy;
    }
}