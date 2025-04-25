package com.at04.touchmovetest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

public class LevelInitializer {
    public DraggableSquare setPlayer(Level context) {
        DraggableSquare player = context.findViewById(R.id.square);
        Bitmap bitmap1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.head);
        player.setBitmap(bitmap1);
        return player;
    }
    public Obstacle[] setBullets(Level context) {
        Obstacle obs = context.findViewById(R.id.obstacle1);
        Bitmap bitmap2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.spike_thingy);
        obs.setBitmap(bitmap2);

        Obstacle obs2 = context.findViewById(R.id.obstacle2);
        obs2.setPosition(new Position(300, 800));
        obs2.setBitmap(bitmap2);

        return new Obstacle[] {obs, obs2};
    }
    public View[] setDisplay(Level context) {
        return new View[] {
                context.findViewById(R.id.testDisplay),
                context.findViewById(R.id.hitTimerDisplay)
        };
    }

}
