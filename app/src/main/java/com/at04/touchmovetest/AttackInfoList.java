package com.at04.touchmovetest;

import java.util.ArrayList;

public class AttackInfoList {
    public ArrayList<AttackInfo> attackInfoList;
    public AttackInfoList() {
        attackInfoList = new ArrayList<>();
    }
    public void add(AttackInfo atk) {
        attackInfoList.add(atk);
    }
    public int size() {
        return attackInfoList.size();
    }
    public AttackInfo get(int idx) {
        return attackInfoList.get(idx);
    }
}
