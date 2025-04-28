package com.at04.touchmovetest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LevelMenuActivity extends AppCompatActivity {
    Button button00;
    Button button01;

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
    }

    private void switchActivities(int levelID) {
        Intent intent;
        switch(levelID) {
            case 0:
                intent = new Intent(this, LevelTest00Activity.class);
                startActivity(intent);
                break;
            case 1:
                intent = new Intent(this, LevelTest01Activity.class);
                startActivity(intent);
                break;
        }
    }
}
