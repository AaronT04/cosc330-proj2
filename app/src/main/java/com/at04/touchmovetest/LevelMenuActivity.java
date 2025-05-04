package com.at04.touchmovetest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class LevelMenuActivity extends AppCompatActivity {
    ImageButton button00;
    ImageButton button01;
    ImageButton button02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levelmenu);

        button00 = findViewById(R.id.select_lv00);
        button00.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivities(0);
            }
        });
        button01 = findViewById(R.id.select_lv01);
        button01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivities(1);
            }
        });
        button02 = findViewById(R.id.select_lv02);
        button02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivities(2);
            }
        });
    }

    private void switchActivities(int levelID) {
        Intent intent;
        intent = new Intent(this, Level.class);
        intent.putExtra("LEVEL_ID", levelID);
        startActivity(intent);
    }
}
