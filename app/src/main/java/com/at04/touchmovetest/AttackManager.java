package com.at04.touchmovetest;

import android.graphics.Canvas;

import java.util.ArrayList;

public class AttackManager {
    private AttackSequence sequence;
    private Position playerPosition;
    private ArrayList<Bullet> activeBullets = new ArrayList<>();
    private ArrayList<Attack> activeAttacks = new ArrayList<>();
    private int currentAttackIndex = 0;

    public AttackManager(AttackSequence seq) {
        sequence = seq;
        for(int i = 0; i < sequence.size(); i++) {
            sequence.get(i).registerAttackManager(this);
        }
    }

    public AttackManager() {

    }

    public void setSequence(AttackSequence seq) {
        sequence = seq;
        for(int i = 0; i < sequence.size(); i++) {
            sequence.get(i).registerAttackManager(this);
        }
        activeAttacks.add(sequence.get(0));
    }

    public void registerPlayerPosition(Position p) {
        playerPosition = p;
        for(int i = 0; i < sequence.size(); i++) {
            sequence.get(i).registerPlayerPosition(p);
        }
    }

    public ArrayList<Sprite> getActiveBullets() {
        //assume for now that only the first attack is "active"
        //normally "activeAttacks" would have to be updated in a more complicated way
            //that I haven't thought of yet

        ArrayList<Sprite> allBullets = new ArrayList<>();
        //
        for(int i = 0; i < activeAttacks.size(); i++) {
            allBullets.addAll(activeAttacks.get(i).getBullets());
        }
        return allBullets;
    }

    public void update() {
        for(int i = 0; i < activeAttacks.size(); i++) {
            activeAttacks.get(i).update();
        }
    }
    public void draw(Canvas canvas) {
        for(int i = 0; i < activeAttacks.size(); i++) {
            activeAttacks.get(i).draw(canvas);
        }
    }

    public void notifyOffsetExpired() {
        currentAttackIndex++;
        activeAttacks.add(sequence.get(currentAttackIndex));
    }
}
