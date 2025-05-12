package com.at04.touchmovetest;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Provides a global point of access to all bitmaps used.
 * Bitmaps can also be scaled here. <br/><br/>
 * The Level activity calls GameAssets.init(getResources()) to initialize the Bitmap references.
 */
public class GameAssets {
    public static Bitmap playerSprite;
    public static Bitmap shruikenA;
    public static Bitmap shruikenB;
    public static Bitmap pinkStar;
    public static Bitmap bg_sky;
    public static Bitmap arrow;
    public static void init(Resources res) {
        Bitmap playerSprite_ = BitmapFactory.decodeResource(res, R.drawable.head);
        playerSprite = Bitmap.createScaledBitmap(playerSprite_, 125, 125, true);
        Bitmap pinkStar_ = BitmapFactory.decodeResource(res, R.drawable.pink_star);
        pinkStar = Bitmap.createScaledBitmap(pinkStar_, 100, 100, true);
        Bitmap bg_sky_ = BitmapFactory.decodeResource(res, R.drawable.bg_dark2);
        bg_sky = Bitmap.createScaledBitmap(bg_sky_, DisplaySize.screenWidth, DisplaySize.screenHeight, true);
        Bitmap arrow_ = BitmapFactory.decodeResource(res, R.drawable.arrow);
        arrow = Bitmap.createScaledBitmap(arrow_, 125, 175, true);
    }

}
