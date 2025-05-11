package com.at04.touchmovetest;

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
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference levelListRef = mDatabase.child("LevelEntries");
    private DatabaseReference levelLoadRef; // accesses level contents - unused until a level is clicked
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
        setupListViewListener();
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
    private void setupListViewListener() {
        lvLevels.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                CustomLevelListEntry selectedLevel = customLevelList.get(position);
                loadLevel(selectedLevel);

                return true;
            }
        });
    }
    private void loadLevel(CustomLevelListEntry selectedLevel) {
        levelLoadRef = mDatabase.child("LevelContents").child(selectedLevel.id);
        levelLoadRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //snapshot stores "AttackInfoList" for the selected level
                //need to convert to attack sequence
                AttackInfoList atkList = snapshot.getValue(AttackInfoList.class);
                //AttackSequence main = LevelStorage.createSequenceFromInfoList(atkList);
                //now need to initialize GameModel with that sequence
                switchActivities(atkList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void switchActivities(AttackInfoList atkInfoList) {
        Intent intent;
        intent = new Intent(requireActivity(), Level.class);
        intent.putExtra("LEVEL_TYPE", "remote");
        intent.putExtra("ATK_LIST", atkInfoList);
        startActivity(intent);
    }
}
