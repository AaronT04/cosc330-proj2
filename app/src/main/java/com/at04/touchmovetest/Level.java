package com.at04.touchmovetest;


import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;

import androidx.appcompat.app.AppCompatActivity;

public abstract class Level extends AppCompatActivity {

    GameModel gameModel;
    GameView gameView;
    GameLoop gameLoop;
    public int levelID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameView = (GameView) findViewById(R.id.gameView);

        gameModel = new GameModel(this);
        gameView.registerGameModel(gameModel);

        gameLoop = new GameLoop();
        gameLoop.registerModel(gameModel);
        gameLoop.registerTextView(gameModel.display);
        gameLoop.registerView(gameView);
    }
    public void startGame() {
        gameLoop.setRunning(true);
        gameLoop.start();
    }

    protected void switchToMenuActivity() {
        Intent switchActivityIntent = new Intent(this, LevelMenuActivity.class);
        startActivity(switchActivityIntent);
        finish();
    }
    public void end() {
        switchToMenuActivity();
    }
}
