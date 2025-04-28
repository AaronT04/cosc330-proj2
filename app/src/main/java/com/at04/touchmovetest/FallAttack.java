package com.at04.touchmovetest;

import java.util.ArrayList;

public class FallAttack extends Attack {
    private Position playerPos;
    private boolean wrapEnabled;
    private float spd;
    public FallAttack(int count, float offsetSec, boolean wrapEnabled, float spd) {
        super(count, offsetSec);
        this.wrapEnabled = wrapEnabled;
        this.spd = spd;
        initialize();
    }
    public FallAttack(int count, float offsetSec) {
        super(count, offsetSec);
        this.wrapEnabled = false;
        this.spd = 10f;
        initialize();
    }


    public Attack copy(){
        FallAttack copy = new FallAttack(count, offsetSec, wrapEnabled, spd);
        copy.registerPlayerPosition(playerPos);
        copy.registerAttackManager(attackManager);
        return copy;
    }
    public void initialize() {
        bullets = new ArrayList<>();
        for(int i = 0; i < count; i++) {
            Position p = calcInitialPosition(i, count);
            Bullet b = new Bullet(GameAssets.pinkStar,
                    calcInitialPosition(i, count),
                    new FollowPath(p, new Position(p.x, p.y + 1), this.spd, i, count, this.wrapEnabled));
            bullets.add(b);
        }
    }

    public Position calcInitialPosition(int idx, int count) {
        return new Position(idx * (float)DisplaySize.screenWidth / count, 0);
    }

    @Override
    public void registerPlayerPosition(Position p) {
        this.playerPos  = p;
        for(int i = 0; i < count; i++) {
            bullets.get(i).path.updateTarget(p);
        }
    }

}
