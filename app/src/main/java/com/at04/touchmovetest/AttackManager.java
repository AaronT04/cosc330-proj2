package com.at04.touchmovetest;

import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;

public class AttackManager {
    private AttackSequence sequence;
    private Position playerPosition;
    private ArrayList<Bullet> activeBullets = new ArrayList<>();
    private ArrayList<Attack> activeAttacks = new ArrayList<>();
    private int currentAttackIndex = 0;
    private Timer profileTimer = new Timer();

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

    public ArrayList<Bullet> getActiveBullets() {
        ArrayList<Bullet> allBullets = new ArrayList<>();
        for(int i = 0; i < activeAttacks.size(); i++) {
            allBullets.addAll(activeAttacks.get(i).getBullets());
        }
        return allBullets;
    }

    public void update() {
        for(int i = 0; i < activeAttacks.size(); i++) {
            //Log.d("AttackManager.update()", String.valueOf(activeAttacks.size()));
            activeAttacks.get(i).update();
        }
    }
    public void draw(Canvas canvas) {
        /*
        for(int i = 0; i < activeAttacks.size(); i++) {
            profileTimer.start();
            activeAttacks.get(i).draw(canvas);
            profileTimer.debugStop("attack: i = " + i);
        }
         */
        for(int i = 0; i < activeBullets.size(); i++) {
            Bullet b = activeBullets.get(i);
            canvas.drawBitmap(b.sprite.bitmap, b.sprite.matrix, b.sprite.paint);
        }
    }

    public void notifyOffsetExpired() {
        currentAttackIndex++;
        activeAttacks.add(sequence.get(currentAttackIndex));
        activeBullets = getActiveBullets();
        //Log.d("notifyOffsetExpired", String.valueOf(activeAttacks.size()));
    }
    public void notifyAttackOffscreen(Attack atk) {
        activeAttacks.remove(atk);
        activeBullets = getActiveBullets();
    }
}
