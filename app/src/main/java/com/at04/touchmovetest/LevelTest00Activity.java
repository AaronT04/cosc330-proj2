package com.at04.touchmovetest;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


public class LevelTest00Activity extends Level {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        levelID = 0;
        GameAssets.init(getResources());
        super.onCreate(savedInstanceState);

    }
}