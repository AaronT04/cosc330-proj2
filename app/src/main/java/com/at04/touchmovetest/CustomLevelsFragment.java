package com.at04.touchmovetest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CustomLevelsFragment extends Fragment{
    private static DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private static DatabaseReference levelListRef = mDatabase.child("LevelEntries");
    private static DatabaseReference levelLoadRef; // accesses level contents - unused until a level is clicked
    private CustomLevelList customLevelList;
    private ListView lvLevels;

    public CustomLevelsFragment() {
        setupValueEventListener();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_customlevels, container, false);
    }
    public void onViewCreated(View view, Bundle savedInstanceState) {
        customLevelList = new CustomLevelList(this.getActivity());
        lvLevels = view.findViewById(R.id.listview_custom_levels);
        lvLevels.setAdapter(customLevelList.getListAdapter());
    }

    private void setupValueEventListener() {
        levelListRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("levelListRef", "onDataChange");
                for(DataSnapshot snapshotItem : snapshot.getChildren()) {
                    customLevelList.add(snapshotItem.getValue(CustomLevelListEntry.class));
                }
                customLevelList.update();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public static void onPlayButtonClick(CustomLevelListEntry selectedLevel, Context context) {
        loadLevel(selectedLevel, context);
    }
    private static void loadLevel(CustomLevelListEntry selectedLevel, Context context) {
        levelLoadRef = mDatabase.child("LevelContents").child(selectedLevel.id);
        levelLoadRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //snapshot stores "AttackInfoList" for the selected level
                //need to convert to attack sequence
                AttackInfoList atkList = snapshot.getValue(AttackInfoList.class);
                //AttackSequence main = LevelStorage.createSequenceFromInfoList(atkList);
                //now need to initialize GameModel with that sequence
                switchActivities(atkList, context);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private static void switchActivities(AttackInfoList atkInfoList, Context context) {
        Intent intent;
        intent = new Intent(context, Level.class);
        intent.putExtra("LEVEL_TYPE", "remote");
        intent.putExtra("ATK_LIST", atkInfoList);
        context.startActivity(intent);
    }
}
