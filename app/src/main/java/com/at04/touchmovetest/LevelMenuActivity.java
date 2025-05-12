package com.at04.touchmovetest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity for the start menu and submenus. The actual menu contents are stored in Fragments.
 */

public class LevelMenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levelmenu);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new RootMenuFragment())
                .commit();
    }
}
