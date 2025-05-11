package com.at04.touchmovetest;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

public class AttackInfo implements Serializable {
    public static final int FALL_ATTACK = 0;
    public static final int LINE_ATTACK = 1;
    public static final int CIRCLE_ATTACK = 2;
    public static final int ELLIPSE_ATTACK = 3;
    public BaseAttackInfo baseAttackInfo;
    //"info" is a generic list of numbers. Firebase can't deserialize to a generic Number type,
    //so a custom class for generic numbers has to be used.
    public List<TypedNumber> info;
    public int attackType;
    public AttackInfo() {}

    public AttackInfo(int attackType, BaseAttackInfo baseAttackInfo, Number[] info) {
        this.attackType = attackType;
        this.baseAttackInfo = baseAttackInfo;
        this.info = new ArrayList<>();
        for(int i = 0; i < info.length; i++) {
            this.info.add(TypedNumber.wrap(info[i]));
        }
    }
}