package com.at04.touchmovetest;

public class BaseAttackInfo {
    public final float offsetSec;
    public final float spd;
    public final int count;

    //blank constructor for firebase storage
    public BaseAttackInfo() {
        offsetSec = 0;
        spd = 0;
        count = 0;
    }
    public BaseAttackInfo(int count, float spd, float offsetSec) {
        this.count = count;
        this.spd = spd;
        this.offsetSec = offsetSec;
    }
}
