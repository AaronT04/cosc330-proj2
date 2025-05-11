package com.at04.touchmovetest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AttackInfo implements Serializable {
    public static final int FALL_ATTACK = 0;
    public static final int LINE_ATTACK = 1;
    public static final int CIRCLE_ATTACK = 2;
    public static final int ELLIPSE_ATTACK = 3;
    public BaseAttackInfo baseAttackInfo;
    public List<AttackParameter> params;
    public int attackType;
    public AttackInfo() {}

    public AttackInfo(int attackType, BaseAttackInfo baseAttackInfo, List<AttackParameter> params) {
        this.attackType = attackType;
        this.baseAttackInfo = baseAttackInfo;
        this.params = params;
    }
    public AttackInfo copy() {
        return new AttackInfo(attackType, baseAttackInfo, params);
    }
}