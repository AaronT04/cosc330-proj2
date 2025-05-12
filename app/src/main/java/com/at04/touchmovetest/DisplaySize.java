package com.at04.touchmovetest;

import android.content.res.Resources;

/**
 * Provides a global access point to screen size, for convenience.<br/>
 * Width: screenWidth<br/>
 * Height: screenHeight
 */
public class DisplaySize {
    final static android.util.DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
    final static int screenWidth = displayMetrics.widthPixels;
    final static int screenHeight = displayMetrics.heightPixels;
}
