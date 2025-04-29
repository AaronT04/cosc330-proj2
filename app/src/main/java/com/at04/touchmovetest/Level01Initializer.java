package com.at04.touchmovetest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

public class Level01Initializer extends LevelInitializer {
    public AttackSequence getAttackSequence() {
        AttackSequence sq = new AttackSequence();
        sq.add(new LineAttack(5, 0.3f));
        sq.add(new LineAttack(3, 0.3f, 0.1f));
        sq.add(new LineAttack(5, 0.3f));
        sq.add(new LineAttack(3, 0.3f, 0.1f));
        sq.add(new LineAttack(5, 0.3f));
        sq.add(new LineAttack(2, 1f, 0.35f));
        sq.add(new FallAttack(2, 0.1f, false, 10f, 1));
        sq.add(new FallAttack(3, 0.5f, false, 15f, -1));
        sq.enableLoop();
        return sq;
    }
    public View[] setViews(Level context) {
        return null;
    }
}