package com.at04.touchmovetest;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class GameModel {
    public DraggableSquare player;

    public Obstacle[] bullets;
    public Obstacle obs;
    public Obstacle obs2;
    public GameLoop gameLoop;
    public Level context;
    public View[] display;
    public GameModel(Level l) {
        this.context = l;
        LevelInitializer test_LI = new LevelInitializer();

        initialize(test_LI);//[level.levelID]);

        gameLoop = new GameLoop(this);
        gameLoop.registerTextView(display);

    }

    private void initialize(LevelInitializer li) {
        player = li.setPlayer(this.context);
        bullets = li.setBullets(this.context);
        display = li.setDisplay(this.context);
    }
    public void startGame() {
        gameLoop.setRunning(true);
        gameLoop.start();
    }

}
