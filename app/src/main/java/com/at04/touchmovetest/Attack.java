package com.at04.touchmovetest;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Controls a group of bullets.
 * All subclass constructors should call super(BaseAttackInfo)
 * initialize() - load bullets from the bullet pool in AttackManager
 * calcInitialPosition() - set initial position of all bullets
 * attackUpdate() - control the movement of bullets on each frame
 */
public abstract class Attack implements Serializable {
    /**
     * Used in AttackSequence to duplicate Attacks. This allows for more efficient creation of
     * sequences.
     * @return A copy of the Attack that calls it
     */
    public abstract Attack copy();
    protected ArrayList<Bullet> bullets;
    protected int count;
    protected float offsetSec;
    protected float spd;
    /**
     * Counts the time until the next Attack should be loaded.
     */
    protected CountdownTimer nextTimer;
    /**
     * TODO:
     * Counts the time until the bullets of this Attack should start being loaded -
     * however, initialOffset currently does not work properly.
     */
    protected CountdownTimer initialTimer;
    /**
     * TODO:
     * A reference to the AttackManager is used to notify it when an Attack goes offscreen -
     * however, this could be accomplished with static methods.
     */
    protected AttackManager attackManager;
    protected float initialOffset = 0;

    public Attack(BaseAttackInfo atk_init) {
        this.spd = atk_init.spd;
        this.count = atk_init.count;
        this.offsetSec = atk_init.offsetSec;
        if(offsetSec > 0) {
            nextTimer = new CountdownTimer((long) (offsetSec * 1000));
            nextTimer.startTimer();
        }
        bullets = new ArrayList<>();
    }

    public void update() {
        if(initialTimer == null) {      //The attack has been started
            if (nextTimer != null) {    //The next attack has not been triggered yet
                nextTimer.updateTimeElapsed((long) (GameLoop.dt_sec * 1000));
                if (!nextTimer.isActive()) { //If the nextTimer has expired
                    attackManager.loadNextAttack();
                    nextTimer = null;
                }
            }
            attackUpdate();
        }
        else { //If there is an initial offset
            initialTimer.updateTimeElapsed((long) (GameLoop.dt_sec * 1000));
            if (!initialTimer.isActive()) { //If the initial offset timer is over
                initialTimer = null;
                if(nextTimer != null) //If this is not the last attack
                    nextTimer.startTimer();
            }
        }
    }


    public void registerAttackManager(AttackManager am) {
        this.attackManager = am;
    }
    public void registerPlayerPosition(Point p) {
        //ignored by default
    }
    public void multiplySpeed(float spdMult) {
        this.spd *= spdMult;
    }
    public void setInitialOffset(float initOff) {
        this.initialOffset = initOff;
        initialTimer = new CountdownTimer((long) (initialOffset * 1000));
        initialTimer.startTimer();
        //nextTimer == null if offsetSec was set to 0 in the constructor
        //(meaning this is the last or only attack)
        if(nextTimer != null)
            nextTimer.reset();
    }

    /**
     * Used in LevelStorage.createSequenceFromInfoList() when loading an AttackInfoList from Firebase
     * @param info an AttackInfo object
     * @return - an Attack corresponding to the AttackInfo object
     */
    public static Attack make(AttackInfo info) {
        switch(info.attackType) {
            case AttackInfo.CIRCLE_ATTACK:
                return new CircleAttack(info.baseAttackInfo, info.params);
            case AttackInfo.LINE_ATTACK:
                return new LineAttack(info.baseAttackInfo, info.params);
            case AttackInfo.FALL_ATTACK:
                return new FallAttack(info.baseAttackInfo, info.params);
            case AttackInfo.ELLIPSE_ATTACK:
                return new EllipseAttack(info.baseAttackInfo, info.params);
        }
        return null;
    }
    protected abstract void attackUpdate();
    protected abstract void initialize();
    public abstract Point calcInitialPosition(int idx, int count);

}
