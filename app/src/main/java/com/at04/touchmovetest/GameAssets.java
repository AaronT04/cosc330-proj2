package com.at04.touchmovetest;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class GameAssets {
    public static Bitmap playerSprite;
    public static Bitmap shruikenA;
    public static Bitmap shruikenB;
    public static Bitmap pinkStar;
    public static void init(Resources res) {
        playerSprite = BitmapFactory.decodeResource(res, R.drawable.head);
        shruikenA = BitmapFactory.decodeResource(res, R.drawable.spike_thingy);
        shruikenB = BitmapFactory.decodeResource(res, R.drawable.another_spike);
        pinkStar = BitmapFactory.decodeResource(res, R.drawable.pink_star);
    }
}
