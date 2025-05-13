package com.at04.touchmovetest;

import android.graphics.Bitmap;
import android.view.View;

import java.util.ArrayList;

/**
 * TODO: Get rid of this class and replace it with an array of AttackSequences
 */
public abstract class LevelInitializer {
    public abstract AttackSequence getAttackSequence();
    public void setBackground() {
        GameAssets.bg = GameAssets.bg_sky;
        GameAssets.health_border = GameAssets.health_border_light;
    }
}
