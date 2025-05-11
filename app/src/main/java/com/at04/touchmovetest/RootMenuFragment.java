package com.at04.touchmovetest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

public class RootMenuFragment extends Fragment {
    ImageButton demoLevelsButton;
    ImageButton customLevelsButton;
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
        demoLevelsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    requireActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, new DemoLevelsFragment())
                            .addToBackStack(null)
                            .commit();
                }
            }
        );
        customLevelsButton = view.findViewById(R.id.select_customLevels);
        customLevelsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new CustomLevelsFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}
