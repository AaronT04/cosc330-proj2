package com.at04.touchmovetest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

public class Level00Initializer extends LevelInitializer {

    //just uses test method "setBullets"
    public AttackSequence getAttackSequence() {

        AttackSequence sq = new AttackSequence();
        sq.add(new FallAttack(30, 0, true, 15f));
        //sq.enableLoop();
        return sq;
    }
}
