package com.at04.touchmovetest;

public class BaseAttackInfo {
    final float offsetSec;
    final float spd;
    final int count;
    public BaseAttackInfo(int count, float spd, float offsetSec) {
        this.count = count;
        this.spd = spd;
        this.offsetSec = offsetSec;
    }
}
