package com.at04.touchmovetest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class CustomLevelListAdapter extends ArrayAdapter<CustomLevelListEntry> {
    public CustomLevelListAdapter(Context context, List<CustomLevelListEntry> levelList) {
        super(context, 0, levelList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CustomLevelListEntry levelEntry = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.customlevel_list_entry, parent, false);
        }

        TextView levelName = convertView.findViewById(R.id.customlevel_levelname_text);
        TextView username = convertView.findViewById(R.id.customlevel_username_text);
        TextView timestamp= convertView.findViewById(R.id.customlevel_timestamp_text);

        if(levelEntry != null) {
            levelName.setText(levelEntry.levelName);
            username.setText(levelEntry.username);
            timestamp.setText(levelEntry.timestamp);
        }

        return convertView;
    }
}
