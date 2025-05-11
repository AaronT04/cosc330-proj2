package com.at04.touchmovetest;

import java.io.Serializable;
import java.util.ArrayList;

public class AttackInfoList implements Serializable {
    public ArrayList<AttackInfo> attackInfoList;
    public AttackInfoList() {
        attackInfoList = new ArrayList<>();
    }
    public void add(AttackInfo atk) {
        attackInfoList.add(atk);
    }
    public void add(int repeat, AttackInfo[] atks) {
        for(int i = 0; i < repeat; i++) {
            for(int j = 0; j < atks.length; j++) {
                AttackInfo newAttack = atks[j].copy();
                add(newAttack);
            }
        }
    }
    public void add(int repeat, AttackInfoList[] lists) {
        for(int i = 0; i < repeat; i++) {
            for(int j = 0; j < lists.length; j++) {
                for(int k = 0; k < lists[j].size(); k++) {
                    AttackInfo newAttack = lists[j].get(k).copy();
                    add(newAttack);
                }
            }
        }
    }

    public int size() {
        return attackInfoList.size();
    }
    public AttackInfo get(int idx) {
        return attackInfoList.get(idx);
    }
}
