package com.at04.touchmovetest;

import static java.lang.Math.PI;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import java.util.ArrayList;

public class Level01Initializer extends LevelInitializer {
    public AttackSequence getAttackSequence() {
        AttackSequence sq1 = new AttackSequence();
        float spd = 10f;
        float offsetSec = 0.3f;
        sq1.add(5,
                new Attack[]
                {new LineAttack(2, offsetSec, spd/2, 0),
                 new LineAttack(3, offsetSec, spd, 0.1f)});
        AttackSequence sq2 = new AttackSequence();
        sq2.add(new FallAttack(2, 0.1f, false, 10f, 1));
        //sq2.add(new FallAttack(3, 0.5f, false, 15f, -1));
        sq2.add(new CircleAttack(5,1f));
        sq2.add(new CircleAttack(5,1f,122));
        sq2.add(new CircleAttack(5,1f,122,-4));
        AttackSequence full = new AttackSequence();

        
        full.add(3, new AttackSequence[] {sq1, sq2});
        return full;
    }
    public View[] setViews(Level context) {
        return null;
    }
}