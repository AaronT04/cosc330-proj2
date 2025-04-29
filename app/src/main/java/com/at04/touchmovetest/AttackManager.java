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
    private Paint paint;
    static Bitmap pinkStar = GameAssets.pinkStar;//BitmapFactory.decodeResource(res, R.drawable.pink_star);

    public AttackManager(AttackSequence seq) {
        sequence = seq;
        for(int i = 0; i < sequence.size(); i++) {
            sequence.get(i).registerAttackManager(this);
        }
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
    }

    public AttackManager() {
        matrices = new Matrix[MAX_BULLETS];
        bullets = new Bullet[MAX_BULLETS];
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
        /*
        for(int i = 0; i < activeBullets.size(); i++) {
            Bullet b = activeBullets.get(i);
            canvas.drawBitmap(b.sprite.bitmap, b.sprite.matrix, b.sprite.paint);
        }
        */
        /*
        for(int i = 0; i < MAX_BULLETS; i++) {
            if (bullets[i] != null) {
                canvas.drawBitmap(GameAssets.pinkStar, bullets[i].sprite.matrix, paint);
            }
        }

         */
        for(int i = 0; i < MAX_BULLETS; i++) {
            if (matrices[i] != null) {
                canvas.drawBitmap(pinkStar, matrices[i], paint);
            }
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
