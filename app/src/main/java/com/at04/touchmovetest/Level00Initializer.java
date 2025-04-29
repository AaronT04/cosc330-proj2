package com.at04.touchmovetest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

public class Level00Initializer extends LevelInitializer {
    public AttackSequence getAttackSequence() {
        AttackSequence sq = new AttackSequence();
        sq.add(new FallAttack(10, 0.3f, false, 5f, 1));
        sq.add(new FallAttack(10, 0.3f, false, 5f, -1));
        sq.add(new FallAttack(10, 0.1f, false, 5f, 1));
        sq.add(new FallAttack(10, 0.3f, false, 5f, -1));
        sq.add(new FallAttack(10, 0.3f, false, 5f, 1));
        sq.add(new FallAttack(10, 1f, false, 5f, -1));
        sq.enableLoop();
        return sq;
    }
}
