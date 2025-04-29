package com.at04.touchmovetest;

import static com.at04.touchmovetest.GameAssets.pinkStar;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;

public class AttackManager {
    private AttackSequence sequence;
    private Position playerPosition;
    private ArrayList<Bullet> activeBullets = new ArrayList<>();
    private ArrayList<Attack> activeAttacks = new ArrayList<>();
    private int currentAttackIndex = 0;
    private Timer profileTimer = new Timer();

    public static final int MAX_BULLETS = 256;
    public static int bulletIdx = 0;
    public static Bullet[] bullets;
    public static Matrix[] matrices;
    static Bitmap pinkStar = GameAssets.pinkStar;//BitmapFactory.decodeResource(res, R.drawable.pink_star);

    public AttackManager() {
        bulletIdx = 0;
        matrices = new Matrix[MAX_BULLETS];
        bullets = new Bullet[MAX_BULLETS];
        for(int i = 0; i < MAX_BULLETS; i++) {
            matrices[i] = new Matrix();
            bullets[i] = new Bullet(GameAssets.pinkStar);
            bulletIdx++;
        }
        bulletIdx = 0;
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

    public void startAttacks() {
        sequence.get(0).initialize();
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
        for(int i = 0; i < MAX_BULLETS; i++) {
            canvas.drawBitmap(GameAssets.pinkStar, matrices[i], null);
        }
    }

    public void loadNextAttack() {
        currentAttackIndex++;
        if(sequence.get(currentAttackIndex) != null) {
            activeAttacks.add(sequence.get(currentAttackIndex));
            if (!activeAttacks.isEmpty())
                activeAttacks.get(activeAttacks.size() - 1).initialize();
        }

        //activeBullets = getActiveBullets();
        //Log.d("notifyOffsetExpired", String.valueOf(activeAttacks.size()));
    }
    public void notifyAttackOffscreen(Attack atk) {
        activeAttacks.remove(atk);
        //activeBullets = getActiveBullets();
    }
}
