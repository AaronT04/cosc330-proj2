package com.at04.touchmovetest;

import android.graphics.Canvas;

import java.util.ArrayList;

public abstract class Attack {
    public abstract Attack copy();
    protected ArrayList<Bullet> bullets;
    protected int count;
    protected float offsetSec;
    protected CountdownTimer timer;
    protected AttackManager attackManager;

    public Attack(int count, float offsetSec) {
        this.count = count;
        this.offsetSec = offsetSec;
        if(offsetSec > 0) {
            timer = new CountdownTimer((long) (offsetSec * 1000));
            timer.setActive();
        }
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }
    public void draw(Canvas canvas) {
        for(int i = 0; i < bullets.size(); i++) {
            bullets.get(i).draw(canvas);
        }
    }

    public void update() {
        if(timer != null) {
            timer.update((long)(GameLoop.dt_sec * 1000));
            if (!timer.isActive()) {
                attackManager.loadNextAttack();
                timer = null;
            }
        }
    }
    protected abstract void initialize();

    public void registerAttackManager(AttackManager am) {
        this.attackManager = am;
    }
    public void registerPlayerPosition(Position p) {
        //ignored by default
    }
    public abstract Position calcInitialPosition(int idx, int count);
}
