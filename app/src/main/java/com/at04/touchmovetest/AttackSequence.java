package com.at04.touchmovetest;

import java.util.ArrayList;
import android.util.Log;

public class AttackSequence {
    private ArrayList<Attack> sequence;
    private boolean looped = false;
    public float initOffset = 0;
    public AttackSequence() {
        sequence = new ArrayList<>();
    }
    public AttackSequence(float initOffset) {
        this();
        this.initOffset = initOffset;
    }
    public void add(Attack a) {
        sequence.add(a);
    }
    public void add(int repeat, Attack[] atks) {
        for(int i = 0; i < repeat; i++) {
            for(int j = 0; j < atks.length; j++) {
                Attack newAttack = atks[j].copy();
                if(j == 0 && initOffset != 0) {
                    newAttack.setInitialOffset(initOffset);
                }
                add(newAttack);
            }
        }
    }

    public void add(int repeat, AttackSequence[] seqs) {
        for(int i = 0; i < repeat; i++) {
            for(int j = 0; j < seqs.length; j++) {
                for(int k = 0; k < seqs[j].size(); k++) {
                    Attack newAttack = seqs[j].get(k).copy();
                    if(k == 0 && seqs[j].initOffset != 0) {
                        newAttack.setInitialOffset(seqs[j].initOffset);
                    }
                    add(newAttack);
                }
            }
        }
    }

    public Attack get(int idx) {
        if(looped && idx >= sequence.size()) {

            return sequence.get(idx % sequence.size()).copy();
        }
        else {
            if(idx < sequence.size())
                return sequence.get(idx);
            else {
                return null;
            }
        }
    }
    public int size() {
        return sequence.size();
    }
    public void enableLoop() {
        looped = true;
    }
    public void remove(Attack atk) {
        sequence.remove(atk);
        Log.d("atkSeq.remove()", String.valueOf(sequence.size()));
    }
}
