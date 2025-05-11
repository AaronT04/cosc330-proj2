package com.at04.touchmovetest;

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import android.content.Context;

public class CustomLevelList {
    private static final int MAX_LENGTH = 25;
    public ArrayList<CustomLevelListEntry> customLevelList;
    private CustomLevelListAdapter listAdapter;
    public CustomLevelList(Context context) {
        customLevelList = new ArrayList<>();
        listAdapter = new CustomLevelListAdapter(context, customLevelList);
    }
    public void add(CustomLevelListEntry listEntry) {
        if(customLevelList.size() < MAX_LENGTH) {
            customLevelList.add(listEntry);
        }
    }
    public CustomLevelListEntry get(int idx) {
        return customLevelList.get(idx);
    }
    public CustomLevelListAdapter getListAdapter() {
        return listAdapter;
    }
    public void update() {
        listAdapter.notifyDataSetChanged();
    }
}
