package com.at04.touchmovetest;

import java.util.ArrayList;

public abstract class Attack {
    public abstract Attack copy();
    protected ArrayList<Bullet> bullets;
    protected int count;
    protected float offsetSec;
    protected float spd;
    protected CountdownTimer nextTimer;
    protected CountdownTimer initialTimer;
    protected AttackManager attackManager;
    protected float initialOffset = 0;

    public Attack(BaseAttackInfo atk_init) {
        this.spd = atk_init.spd;
        this.count = atk_init.count;
        this.offsetSec = atk_init.offsetSec;
        if(offsetSec > 0) {
            nextTimer = new CountdownTimer((long) (offsetSec * 1000));
            nextTimer.setActive();
        }
        bullets = new ArrayList<>();
    }

    public void update() {
        if(initialTimer == null) {      //The attack has been started
            if (nextTimer != null) {    //The next attack has not been triggered yet
                nextTimer.update((long) (GameLoop.dt_sec * 1000));
                if (!nextTimer.isActive()) {
                    attackManager.loadNextAttack();
                    nextTimer = null;
                }
            }
            attackUpdate();
        }
        else { //If there is an initial offset
            initialTimer.update((long) (GameLoop.dt_sec * 1000));
            if (!initialTimer.isActive()) {
                initialTimer = null;
                nextTimer.setActive();
            }
        }
    }


    public void registerAttackManager(AttackManager am) {
        this.attackManager = am;
    }
    public void registerPlayerPosition(Point p) {
        //ignored by default
    }
    public void setInitialOffset(float initOff) {
        this.initialOffset = initOff;
        initialTimer = new CountdownTimer((long) (initialOffset * 1000));
        initialTimer.setActive();
        nextTimer.reset();
    }
    protected abstract void attackUpdate();
    protected abstract void initialize();
    public abstract Point calcInitialPosition(int idx, int count);

}
