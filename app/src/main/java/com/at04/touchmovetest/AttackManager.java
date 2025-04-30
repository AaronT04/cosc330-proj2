package com.at04.touchmovetest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;

public class AttackManager {
    private AttackSequence sequence;
    private GameModel model;
    private Position playerPosition;
    private ArrayList<Bullet> activeBullets = new ArrayList<>();
    private ArrayList<Attack> activeAttacks = new ArrayList<>();
    private int currentAttackIndex = 0;
    public static final int MAX_BULLETS = 512;
    public static int bulletIdx = 0;
    public static Bullet[] bullets;
    public static Matrix[] matrices;
    public static Bitmap[] bitmaps;
    private boolean readyToEnd = false;

    public AttackManager() {

        matrices = new Matrix[MAX_BULLETS];
        bullets = new Bullet[MAX_BULLETS];
        bitmaps = new Bitmap[MAX_BULLETS];
        for(int i = 0; i < MAX_BULLETS; i++) {
            bulletIdx = i;
            matrices[i] = new Matrix();
            matrices[i].postTranslate(Physics.FAR_AWAY.x, Physics.FAR_AWAY.y);
            bullets[i] = new Bullet();
            bitmaps[i] = GameAssets.pinkStar;
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

    public void registerModel(GameModel model) {
        this.model = model;
    }

    public void startAttacks() {
        sequence.get(0).initialize();
    }

    public void update() {
        for(int i = 0; i < activeAttacks.size(); i++) {
            activeAttacks.get(i).update();
        }
    }
    public void draw(Canvas canvas) {
        for(int i = 0; i < MAX_BULLETS; i++) {
            canvas.drawBitmap(bitmaps[i], matrices[i], null);
        }
    }

    public static Bullet initializeBullet(Bitmap bitmap, Position p, float spd, float angle) {
        Bullet bullet = bullets[AttackManager.bulletIdx % AttackManager.MAX_BULLETS];
        if(bullet.isLoaded){
            bullet.unloadAndReset();
        }

        bullet.load(bitmap, p, spd, angle);
        bitmaps[bulletIdx] = bitmap;

        bulletIdx++;
        if(bulletIdx >= MAX_BULLETS) {
            bulletIdx -= MAX_BULLETS;
        }

        return bullet;
    }

    public static Matrix getBulletMatrix() {
        return matrices[AttackManager.bulletIdx % AttackManager.MAX_BULLETS];
    }

    public void loadNextAttack() {
        currentAttackIndex++;
        if(sequence.get(currentAttackIndex) != null) {
            activeAttacks.add(sequence.get(currentAttackIndex));
            if (!activeAttacks.isEmpty())
                activeAttacks.get(activeAttacks.size() - 1).initialize();
        }
        else {
            readyToEnd = true;
        }
    }
    public void notifyAttackOffscreen(Attack atk) {
        activeAttacks.remove(atk);
        if(readyToEnd && activeAttacks.isEmpty()) {
            model.context.end();
        }
    }
}
