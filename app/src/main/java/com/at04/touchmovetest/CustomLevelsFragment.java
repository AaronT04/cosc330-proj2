package com.at04.touchmovetest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Menu fragment for the list of custom levels.
 */

public class CustomLevelsFragment extends Fragment{
    private static DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    //Database reference for the list of level entries - name, username, timestamp, id
    private static DatabaseReference levelListRef = mDatabase.child("LevelEntries");
    private static DatabaseReference levelLoadRef; // accesses level contents - unused until a level is clicked
    private CustomLevelList customLevelList;
    private ListView lvLevels;
    private FrameLayout loadingOverlay; //Displayed until the level list has been loaded
    //TODO: Only display loadingOverlay if loading takes a certain amount of time (>0.5sec)
    //Otherwise, if the levels are stored in the cache, it might "flicker" really fast
    //Which looks kind of bad

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
        loadingOverlay = view.findViewById(R.id.loading_overlay);
        loadingOverlay.setVisibility(View.VISIBLE);
    }

    private void setupValueEventListener() {
        levelListRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("levelListRef", "onDataChange");
                for(DataSnapshot snapshotItem : snapshot.getChildren()) { //each level in the list
                    customLevelList.add(snapshotItem.getValue(CustomLevelListEntry.class));
                }
                customLevelList.update(); //Updates the custom ArrayAdapter
                loadingOverlay.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /**
     * CustomLevelListAdapter stores all of the buttons. The clickListener for each button
     * calls this method based on the level associated with that button.
     * Because this is a static method, context has to be passed as a parameter.
     */
    public static void onPlayButtonClick(CustomLevelListEntry selectedLevel, Context context) {
        loadLevel(selectedLevel, context);
    }
    private static void loadLevel(CustomLevelListEntry selectedLevel, Context context) {
        levelLoadRef = mDatabase.child("LevelContents").child(selectedLevel.id);
        //ListenerForSingleValueEvent - you only need to get data once, then stop listening
        levelLoadRef.addListenerForSingleValueEvent(new ValueEventListener() {
            //"Loading" the level has to be done from inside onDataChange
            //to ensure that the game only loads it after it's been retrieved from the database
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) { //
                //snapshot stores "AttackInfoList" for the selected level
                AttackInfoList atkList = snapshot.getValue(AttackInfoList.class);
                switchActivities(atkList, context, selectedLevel.bg_id);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private static void switchActivities(AttackInfoList atkInfoList, Context context, String bgID) {
        Intent intent;
        intent = new Intent(context, Level.class);
        intent.putExtra("LEVEL_TYPE", "remote");
        intent.putExtra("ATK_LIST", atkInfoList);
        intent.putExtra("BG_ID", bgID);
        context.startActivity(intent);
    }
}
