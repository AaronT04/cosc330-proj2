package com.at04.touchmovetest;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LevelStorage {
    private static DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private LevelStorage() {
    }
    private static final LevelInitializer[] initializers = new LevelInitializer[]
            {
                    new Level00Initializer(),
                    new Level01Initializer(),
                    new Level02Initializer(),
                    new Level03Initializer(),
                    new CustomLevelInitializer(),
            };
    public static LevelInitializer getLevelInitializer(int levelID) {
        //used to test adding a level, before level editor is created
        return initializers[levelID % initializers.length];
    }

    public static void saveToDatabase(AttackInfoList atkInfoList, String levelName, String username, String id) {
        CustomLevelListEntry newEntry = new CustomLevelListEntry();
        newEntry.id = id;
        newEntry.levelName = levelName;
        newEntry.username = username;
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy  - hh:mm:ss a", Locale.getDefault());
        newEntry.timestamp = sdf.format(new Date());
        mDatabase.child("LevelEntries").child(newEntry.id).setValue(newEntry);
        mDatabase.child("LevelContents").child(newEntry.id).setValue(atkInfoList);
    }

    static class Level00Initializer extends LevelInitializer {
        @Override
        public AttackSequence getAttackSequence() {
            AttackSequence sq = new AttackSequence();
            BaseAttackInfo atk_init01 = new BaseAttackInfo(30, 5f, 0.3f);
            sq.add(7, new Attack[] {
                    new FallAttack(atk_init01, false, 1),
                    new FallAttack(atk_init01, false, -1)
            });
            return sq;
        }
    }
    static class Level01Initializer extends LevelInitializer {
        public AttackSequence getAttackSequence() {
            AttackSequence sq1 = new AttackSequence();
            float spd = 10f;
            float offsetSec = 0.3f;
            sq1.add(5,
                    new Attack[]
                            {new LineAttack(new BaseAttackInfo(2, spd/2, offsetSec), 0),
                                    new LineAttack(new BaseAttackInfo(3, spd, offsetSec), 0.1f)});
            AttackSequence sq2 = new AttackSequence();
            sq2.add(new FallAttack(new BaseAttackInfo(2, 10f, 0.5f), false,  1));
            sq2.add(new FallAttack(new BaseAttackInfo(3, 15f, 0.5f), false, -1));

            AttackSequence full = new AttackSequence(1f);

            full.add(3, new AttackSequence[] {sq1, sq2});
            return full;
        }
    }
    static class Level02Initializer extends LevelInitializer {
        @Override
        public AttackSequence getAttackSequence() {
            AttackSequence seq = new AttackSequence();
            BaseAttackInfo atk_init = new BaseAttackInfo(3, 2f, 0.1f);
            seq.add(5, new Attack[] {
                    new EllipseAttack(
                            atk_init,
                            new Line((float)DisplaySize.screenWidth / 2 - 100, new Range(0, 0)),
                            new Line(new Range(0, (DisplaySize.screenWidth / 2) - 200), DisplaySize.screenHeight)
                    ),
                    new EllipseAttack(
                            atk_init,
                            new Line((float)DisplaySize.screenWidth / 2 - 100, new Range(0, 0)),
                            new Line(new Range((DisplaySize.screenWidth / 2) + 200, DisplaySize.screenWidth), DisplaySize.screenHeight)
                    ),
            });
            seq.add(5, new Attack[] {
                    new EllipseAttack(
                            atk_init,
                            new Line(0, new Range(0, 0)),
                            new Line(new Range(200, DisplaySize.screenWidth), DisplaySize.screenHeight)
                    ),
                    new EllipseAttack(
                            atk_init,
                            new Line((float)DisplaySize.screenWidth - 200, new Range(0, 0)),
                            new Line(new Range(0,DisplaySize.screenWidth),DisplaySize.screenHeight)
                    ),
            });
            return seq;
        }
    }
    static class Level03Initializer extends LevelInitializer {
        @Override
        public AttackSequence getAttackSequence() {

            AttackSequence main = new AttackSequence();
            BaseAttackInfo atk_init1 = new BaseAttackInfo(10, 5f, 1f);
            main.add(new CircleAttack(atk_init1, 1f/2, -1f/2));
            main.add(new CircleAttack(atk_init1, 1f/2, 1f/2));
            main.add(new CircleAttack(atk_init1, 1f/3, 0));
            AttackSequence seq_0 = new AttackSequence(1f);
            BaseAttackInfo simult_atk1 = new BaseAttackInfo(10, 2f, 0.001f);
            seq_0.add(new CircleAttack(simult_atk1, 1f/3, 0));
            seq_0.add(new CircleAttack(simult_atk1, 1f/2, 0));
            seq_0.add(new CircleAttack(simult_atk1, 1f/4, 0));
            main.add(2, new AttackSequence[] {seq_0});
            return main;
        }
    }
    static class CustomLevelInitializer extends LevelInitializer {
        @Override
        public AttackSequence getAttackSequence() {
            AttackSequence main = new AttackSequence();
            return main;
        }
    }

    public static AttackSequence createSequenceFromInfoList(AttackInfoList atkList) {
        AttackSequence main = new AttackSequence();
        if(atkList != null) {
            for (int i = 0; i < atkList.size(); i++) {
                AttackInfo thisAtk = atkList.get(i);
                main.add(Attack.make(thisAtk));
            }
        }
        else {
            Log.d("createSequenceFromInfoList", "atkList is null");
        }
        return main;
    }
}