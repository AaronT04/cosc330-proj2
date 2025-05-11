package com.at04.touchmovetest;


import static java.lang.Thread.sleep;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;

import androidx.appcompat.app.AppCompatActivity;

public class Level extends AppCompatActivity {

    GameModel gameModel;
    GameView gameView;
    GameLoop gameLoop;
    public int levelID;
    public String levelType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GameAssets.init(getResources());
        gameView = (GameView) findViewById(R.id.gameView);

        levelID = getIntent().getIntExtra("LEVEL_ID", 0);
        levelType = getIntent().getStringExtra("LEVEL_TYPE");

        if(levelType.equals("remote")) {
            AttackInfoList atkList = (AttackInfoList)getIntent().getSerializableExtra("ATK_LIST");
            gameModel = new GameModel(this, atkList);
        }
        else {
            gameModel = new GameModel(this);
        }

        gameView.registerGameModel(gameModel);

        gameLoop = new GameLoop();
        gameLoop.registerModel(gameModel);
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
        //If the level failed to load properly, gameLoop might be null
        if(gameLoop != null) {
            gameLoop.setRunning(false);
        }
        switchToMenuActivity();
    }
}
