package com.at04.touchmovetest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class RootMenuFragment extends Fragment {
    ImageButton demoLevelsButton;
    TextView demoLevelsTextView;
    ImageButton customLevelsButton;
    TextView customLevelsTextView;
    FrameLayout customLevelsExpandSection;
    Button createButton;
    Button playButton;
    public RootMenuFragment() {}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /*
        //Add levels to database from code for testing
        CustomLevelTest.testmethod_add03todatabase();
        CustomLevelTest.testmethod_addlinetesttodatabase();
        CustomLevelTest.testmethod_addellipsetesttodatabase();
        */
        return inflater.inflate(R.layout.fragment_rootmenu, container, false);
    }
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        demoLevelsButton = view.findViewById(R.id.select_demoLevels);
        demoLevelsButton.setOnClickListener(v -> { //go to DemoLevelsFragment
            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new DemoLevelsFragment())
                    .addToBackStack(null)
                    .commit();
            }
        );
        customLevelsButton = view.findViewById(R.id.select_customLevels);
        customLevelsExpandSection = view.findViewById(R.id.customLevels_expand);
        customLevelsButton.setOnClickListener(v -> {        //expand panel with buttons
             customLevelsExpandSection.setVisibility(View.VISIBLE);
        });
        createButton = view.findViewById(R.id.select_create);
        createButton.setOnClickListener(v -> {         //highlight button and go to other menu
            createButton.setTextColor(Color.BLACK);
            createButton.setBackgroundColor(Color.WHITE);
            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    //Even though you're about to switch the activity, you have to
                    //reload the RootMenuFragment so that when you hit the back button,
                    //the panel is collapsed and button color is reset
                    .replace(R.id.fragment_container, new RootMenuFragment())
                    .commit();
            switchToLevelCreator();

        });
        playButton = view.findViewById(R.id.select_play);
        playButton.setOnClickListener(v -> {    //go to CustomLevelsFragment()
            playButton.setTextColor(Color.BLACK);
            playButton.setBackgroundColor(Color.WHITE);
            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new CustomLevelsFragment())
                    .addToBackStack(null)
                    .commit();
        });
    }
    private void switchToLevelCreator() {
        Intent intent;
        intent = new Intent(requireActivity(), LevelCreator.class);
        startActivity(intent);
    }
}
