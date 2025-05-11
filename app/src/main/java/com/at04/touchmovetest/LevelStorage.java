package com.at04.touchmovetest;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
        testmethod_addlinetesttodatabase();
        //testmethod_add03todatabase();
        return initializers[levelID % initializers.length];
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
    private static void testmethod_add03todatabase() {
        AttackInfoList atkInfoList = new AttackInfoList();
        BaseAttackInfo atk_init0 = new BaseAttackInfo(10, 5, 1);
        atkInfoList.add(new AttackInfo(AttackInfo.CIRCLE_ATTACK, atk_init0, new Number[] {1f/2, -1f/2}));
        atkInfoList.add(new AttackInfo(AttackInfo.CIRCLE_ATTACK, atk_init0, new Number[] {1f/2, 1f/2}));
        atkInfoList.add(new AttackInfo(AttackInfo.CIRCLE_ATTACK, atk_init0, new Number[] {1f/3, 0f}));
        BaseAttackInfo simult_atk1 = new BaseAttackInfo(10, 2f, 0.001f);
        atkInfoList.add(new AttackInfo(AttackInfo.CIRCLE_ATTACK, simult_atk1, new Number[] {1f/3, 0f}));
        atkInfoList.add(new AttackInfo(AttackInfo.CIRCLE_ATTACK, simult_atk1, new Number[] {1f/2, 0f}));
        atkInfoList.add(new AttackInfo(AttackInfo.CIRCLE_ATTACK, simult_atk1, new Number[] {1f/4, 0f}));

        CustomLevelListEntry newEntry = new CustomLevelListEntry();
        newEntry.id = "1";
        newEntry.levelName = "Level03 test";
        newEntry.username = "Aaron";
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy  - hh:mm:ss a", Locale.getDefault());
        newEntry.timestamp = sdf.format(new Date());

        mDatabase.child("LevelEntries").child(newEntry.id).setValue(newEntry);
        mDatabase.child("LevelContents").child(newEntry.id).setValue(atkInfoList);
    }
    private static void testmethod_addlinetesttodatabase() {
        AttackInfoList atkInfoList = new AttackInfoList();
        atkInfoList.add(new AttackInfo(AttackInfo.LINE_ATTACK, new BaseAttackInfo(2, 5, 0.3f), new Number[] {0f}));
        atkInfoList.add(new AttackInfo(AttackInfo.LINE_ATTACK, new BaseAttackInfo(3, 10, 0.3f), new Number[] {0.1f}));
        atkInfoList.add(new AttackInfo(AttackInfo.LINE_ATTACK, new BaseAttackInfo(2, 5, 0.3f), new Number[] {0f}));
        atkInfoList.add(new AttackInfo(AttackInfo.LINE_ATTACK, new BaseAttackInfo(3, 10, 0.3f), new Number[] {0.1f}));
        atkInfoList.add(new AttackInfo(AttackInfo.LINE_ATTACK, new BaseAttackInfo(2, 5, 0.3f), new Number[] {0f}));
        atkInfoList.add(new AttackInfo(AttackInfo.LINE_ATTACK, new BaseAttackInfo(3, 10, 0.3f), new Number[] {0.1f}));
        atkInfoList.add(new AttackInfo(AttackInfo.LINE_ATTACK, new BaseAttackInfo(2, 5, 0.3f), new Number[] {0f}));
        atkInfoList.add(new AttackInfo(AttackInfo.LINE_ATTACK, new BaseAttackInfo(3, 10, 0.3f), new Number[] {0.1f}));
        atkInfoList.add(new AttackInfo(AttackInfo.LINE_ATTACK, new BaseAttackInfo(2, 5, 0.3f), new Number[] {0f}));
        atkInfoList.add(new AttackInfo(AttackInfo.LINE_ATTACK, new BaseAttackInfo(3, 10, 0.3f), new Number[] {0.1f}));
        atkInfoList.add(new AttackInfo(AttackInfo.FALL_ATTACK, new BaseAttackInfo(2, 10, 0.5f), new Number[] {1}));
        atkInfoList.add(new AttackInfo(AttackInfo.FALL_ATTACK, new BaseAttackInfo(3, 15, 0.5f), new Number[] {-1}));
        CustomLevelListEntry newEntry = new CustomLevelListEntry();
        newEntry.id = "0";
        newEntry.levelName = "Line test";
        newEntry.username = "Aaron";
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy  - hh:mm:ss a", Locale.getDefault());
        newEntry.timestamp = sdf.format(new Date());

        mDatabase.child("LevelEntries").child(newEntry.id).setValue(newEntry);
        mDatabase.child("LevelContents").child(newEntry.id).setValue(atkInfoList);
    }
    public static AttackSequence createSequenceFromInfoList(AttackInfoList atkList) {
        AttackSequence main = new AttackSequence();
        if(atkList != null) {
            for (int i = 0; i < atkList.size(); i++) {
                AttackInfo thisAtk = atkList.get(i);
                switch (thisAtk.attackType) {
                    case AttackInfo.CIRCLE_ATTACK:
                        main.add(new CircleAttack(thisAtk.baseAttackInfo, (float)TypedNumber.unwrap(thisAtk.info.get(0)), (float)TypedNumber.unwrap(thisAtk.info.get(1))));
                        break;
                    case AttackInfo.FALL_ATTACK:
                        main.add(new FallAttack(thisAtk.baseAttackInfo, false, (int)TypedNumber.unwrap(thisAtk.info.get(0))));
                        break;
                    case AttackInfo.LINE_ATTACK:
                        main.add(new LineAttack(thisAtk.baseAttackInfo, (float)TypedNumber.unwrap(thisAtk.info.get(0))));
                        break;
                    case AttackInfo.ELLIPSE_ATTACK:
                        main.add(new EllipseAttack(thisAtk.baseAttackInfo, null, null));
                    default:
                        break;
                }
            }
        }
        else {
            Log.d("createSequenceFromInfoList", "atkList is null");
        }
        return main;
    }
}

