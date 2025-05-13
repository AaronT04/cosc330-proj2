package com.at04.touchmovetest;

import android.content.res.Resources;

/**
 * Provides a global access point to screen size, for convenience.<br/>
 * Width: screenWidth<br/>
 * Height: screenHeight
 */
public class Screen {
    final static android.util.DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
    final static int width = displayMetrics.widthPixels;
    final static int left = 0;
    final static int right = width;
    final static int height = displayMetrics.heightPixels;
    final static int top = 0;
    final static int bottom = height;
    //DisplaySize.screenHeight actually sets you somewhat far below the edge of the screen
    //-450 moves you back up to the bottom edge
    final static int bottomVisible = bottom - 450;
    final static Point topMiddlePoint = new Point(width / 2f, 0);
    final static int middleX = width / 2;
    final static int middleY = height / 2;
    final static Range xRange = new Range(0, width);
}
