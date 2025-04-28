package com.at04.touchmovetest;

import java.util.ArrayList;

public class AttackSequence {
    private ArrayList<Attack> sequence;
    private boolean looped = false;
    public AttackSequence() {
        sequence = new ArrayList<>();
    }
    public void add(Attack a) {
        sequence.add(a);
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
}
