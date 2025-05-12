package com.at04.touchmovetest;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.ImageButton;

/**
 * Menu fragment for the list of demo levels.
 */
public class DemoLevelsFragment extends Fragment {
    ImageButton button00;
    ImageButton button01;
    ImageButton button02;
    ImageButton button03;

    public DemoLevelsFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_demolevels, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        button00 = view.findViewById(R.id.select_lv00);
        button00.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivities(0);
            }
        });
        button01 = view.findViewById(R.id.select_lv01);
        button01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivities(1);
            }
        });
        button02 = view.findViewById(R.id.select_lv02);
        button02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivities(2);
            }
        });
        button03 = view.findViewById(R.id.select_lv03);
        button03.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {switchActivities(3);}
        });
    }

    private void switchActivities(int levelID) {
        Intent intent;
        intent = new Intent(requireActivity(), Level.class);
        intent.putExtra("LEVEL_TYPE", "local");
        intent.putExtra("LEVEL_ID", levelID);
        startActivity(intent);
    }
}