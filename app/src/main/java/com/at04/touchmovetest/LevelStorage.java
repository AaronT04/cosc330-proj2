package com.at04.touchmovetest;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LevelStorage {
    private static DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private static DatabaseReference level03Reference = mDatabase.child("levels").child("level03");
    private static AttackInfoList lv03AtkList;
    private LevelStorage() {
    }
    private static final LevelInitializer[] initializers = new LevelInitializer[]
            {
                    new Level00Initializer(),
                    new Level01Initializer(),
                    new Level02Initializer(),
                    new CustomLevelInitializer(),
                    new Level03Initializer(),

            };
    public static LevelInitializer getLevelInitializer(int levelID) {
        level03Reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lv03AtkList = snapshot.getValue(AttackInfoList.class);
                Log.d("lv03AtkList onDataChange", String.valueOf(lv03AtkList));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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


            //mDatabase.child("levels").child("level03").setValue(main);
            //main.add(2, new AttackSequence[] {seq_0}, AttackSequenceModifier.SHIFT_HORIZONTAL(0, 1, -1))
            return main;
        }
    }
    static class CustomLevelInitializer extends LevelInitializer {
        @Override
        public AttackSequence getAttackSequence() {
            AttackSequence main = new AttackSequence();

            /*
            AttackInfoList atkInfoList = new AttackInfoList();
            BaseAttackInfo atk_init0 = new BaseAttackInfo(10, 5, 1);
            atkInfoList.add(new AttackInfo(AttackInfo.CIRCLE_ATTACK, atk_init0, new Number[] {1f/2, -1f/2}));
            atkInfoList.add(new AttackInfo(AttackInfo.CIRCLE_ATTACK, atk_init0, new Number[] {1f/2, 1f/2}));
            atkInfoList.add(new AttackInfo(AttackInfo.CIRCLE_ATTACK, atk_init0, new Number[] {1f/3, 0f}));
            BaseAttackInfo simult_atk1 = new BaseAttackInfo(10, 2f, 0.001f);
            atkInfoList.add(new AttackInfo(AttackInfo.CIRCLE_ATTACK, simult_atk1, new Number[] {1f/3, 0f}));
            atkInfoList.add(new AttackInfo(AttackInfo.CIRCLE_ATTACK, simult_atk1, new Number[] {1f/2, 0f}));
            atkInfoList.add(new AttackInfo(AttackInfo.CIRCLE_ATTACK, simult_atk1, new Number[] {1f/4, 0f}));

            for(int i = 0; i < atkInfoList.size(); i++) {
                AttackInfo thisAtk = atkInfoList.get(i);
                switch(thisAtk.attackType) {
                    case AttackInfo.CIRCLE_ATTACK:
                        main.add(new CircleAttack(thisAtk.baseAttackInfo, (float)TypedNumber.unwrap(thisAtk.info.get(0)), (float)TypedNumber.unwrap(thisAtk.info.get(1))));
                        break;
                    default:
                        break;
                }
            }
            mDatabase.child("levels").child("level03").setValue(atkInfoList);
            */

            if(lv03AtkList != null) {
                for (int i = 0; i < lv03AtkList.size(); i++) {
                    AttackInfo thisAtk = lv03AtkList.get(i);
                    switch (thisAtk.attackType) {
                        case AttackInfo.CIRCLE_ATTACK:
                            main.add(new CircleAttack(thisAtk.baseAttackInfo, (float)TypedNumber.unwrap(thisAtk.info.get(0)), (float)TypedNumber.unwrap(thisAtk.info.get(1))));
                            break;
                        default:
                            break;
                    }
                }
            }
            else {
                Log.d("lv03AtkList == null", "");
            }


            return main;
        }
    }
}

