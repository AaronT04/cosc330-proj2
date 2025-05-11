package com.at04.touchmovetest;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.widget.Button;
import android.widget.LinearLayout;
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

        ViewHolder holder;
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.customlevel_list_entry, parent, false);
            holder = new ViewHolder();
            holder.container = convertView.findViewById(R.id.list_entry_container);
            holder.visibleSection = convertView.findViewById(R.id.list_entry_visible);
            holder.levelName = convertView.findViewById(R.id.customlevel_levelname_text);
            holder.username = convertView.findViewById(R.id.customlevel_username_text);
            holder.timestamp = convertView.findViewById(R.id.customlevel_timestamp_text);
            holder.expandSection = convertView.findViewById(R.id.list_entry_expand);
            holder.playButton = convertView.findViewById(R.id.list_entry_play_button);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        CustomLevelListEntry levelEntry = getItem(position);

        if(levelEntry != null) {
            holder.levelName.setText(levelEntry.levelName);
            holder.username.setText(levelEntry.username);
            holder.timestamp.setText(levelEntry.timestamp);
        }

        holder.container.setOnClickListener(v -> {
            if(holder.expandSection.getVisibility() == View.VISIBLE) {
                holder.expandSection.setVisibility(View.GONE);
            }
            else {
                holder.expandSection.setVisibility(View.VISIBLE);
            }
        });

        holder.playButton.setOnClickListener(v -> {
            holder.playButton.setBackgroundColor(Color.WHITE);
            holder.playButton.setTextColor(Color.BLACK);
            CustomLevelsFragment.onPlayButtonClick(levelEntry, getContext());
        });

        return convertView;
    }

    static class ViewHolder {
        LinearLayout visibleSection;
        TextView levelName;
        TextView username;
        TextView timestamp;
        LinearLayout expandSection;
        LinearLayout container;
        Button playButton;
    }
}
