package com.at04.touchmovetest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

public class RootMenuFragment extends Fragment {
    ImageButton demoLevelsButton;
    ImageButton customLevelsButton;
    Button createButton;
    public RootMenuFragment() {}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /*
        CustomLevelTest.testmethod_add03todatabase();
        CustomLevelTest.testmethod_addlinetesttodatabase();
        CustomLevelTest.testmethod_addellipsetesttodatabase();
        */
        return inflater.inflate(R.layout.fragment_rootmenu, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        demoLevelsButton = view.findViewById(R.id.select_demoLevels);
        demoLevelsButton.setOnClickListener(v -> {
            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new DemoLevelsFragment())
                    .addToBackStack(null)
                    .commit();
            }
        );
        customLevelsButton = view.findViewById(R.id.select_customLevels);
        customLevelsButton.setOnClickListener(v -> {
            requireActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new CustomLevelsFragment())
                    .addToBackStack(null)
                    .commit();
        });
        createButton = view.findViewById(R.id.select_create);
        createButton.setOnClickListener(v -> {
            createButton.setTextColor(Color.BLACK);
            createButton.setBackgroundColor(Color.WHITE);
            switchToLevelCreator();
        });
    }
    private void switchToLevelCreator() {
        Intent intent;
        intent = new Intent(requireActivity(), LevelCreator.class);
        startActivity(intent);
    }
}
