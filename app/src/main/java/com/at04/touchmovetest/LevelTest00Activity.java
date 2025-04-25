package com.at04.touchmovetest;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


public class LevelTest00Activity extends Level {

    GameModel gameModel;
    public static final int levelID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameModel = new GameModel(this);
        gameModel.startGame();
    }
    public void end() {
        switchActivities();
    }

    private void switchActivities() {
        Intent switchActivityIntent = new Intent(this, LevelMenuActivity.class);
        startActivity(switchActivityIntent);
    }
}