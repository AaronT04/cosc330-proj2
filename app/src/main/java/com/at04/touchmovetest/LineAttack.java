package com.at04.touchmovetest;

import java.util.ArrayList;

public class LineAttack extends Attack {

    private float hzInsetRatio;
    @Override
    public Position calcInitialPosition(int idx, int count) {
        float hzInset = hzInsetRatio * (float)(DisplaySize.screenWidth - 200);
        float offsetFromLeftPerIndex = ((float)(DisplaySize.screenWidth - 200) - (hzInset * 2)) / (count - 1);
        return new Position(hzInset + offsetFromLeftPerIndex * idx, 0);
    }

    public LineAttack(int count, float offsetSec, float hzInsetRatio) {
        super(count, offsetSec);
        this.hzInsetRatio = hzInsetRatio;
        initialize();
    }
    public LineAttack(int count, float offsetSec) {
        super(count, offsetSec);
        this.hzInsetRatio = 0;
        initialize();
    }
    public void initialize() {
        bullets = new ArrayList<>();
        for(int i = 0; i < count; i++) {
            Position p = calcInitialPosition(i, count);
            Bullet b = new Bullet(GameAssets.shruikenA,
                    p,
                    new LinearPath(p, -180f, 10f, i, count, false));
            bullets.add(b);
        }
    }

    public Attack copy(){
        LineAttack copy = new LineAttack(count, offsetSec, hzInsetRatio);
        copy.registerPlayerPosition(null);
        copy.registerAttackManager(attackManager);
        return copy;
    }
}
