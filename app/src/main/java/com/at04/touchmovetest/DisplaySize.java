package com.at04.touchmovetest;

import android.content.res.Resources;

public class DisplaySize {
    final static android.util.DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
    final static int screenWidth = displayMetrics.widthPixels;
    final static int screenHeight = displayMetrics.heightPixels;
}
