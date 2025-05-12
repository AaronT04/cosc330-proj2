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

    //TODO: Get rid of these static classes, move them somewhere else or replace with
    //AttackSequence array
    static class Level00Initializer extends LevelInitializer {
        @Override
        public AttackSequence getAttackSequence() {
            AttackSequence sq = new AttackSequence();
            BaseAttackInfo atk_init01 = new BaseAttackInfo(10, 5f, 0.3f);
            sq.add(7, new Attack[] {
                    new FallAttack(atk_init01, false, 1),
                    new FallAttack(atk_init01, false, -1)
            });
            for(int i = 0; i < sq.size(); i += 3) {
                if(i % 2 == 0) {
                    sq.insert(i, new EllipseAttack(new BaseAttackInfo(5, 3f, 0.5f),
                            new Line(Screen.width, new Range(0, 500)),
                            new Line(new Range(0, Screen.width), Screen.height)));
                }
                else {
                    sq.insert(i, new EllipseAttack(new BaseAttackInfo(5, 3f, 0.5f),
                            new Line(0, new Range(0, 500)),
                            new Line(new Range(0, Screen.width), Screen.height)));
                }
            }
            sq.add(new LineAttack(new BaseAttackInfo(2, 10f, 1f), 0));
            sq.add(new LineAttack(new BaseAttackInfo(2, 10f, 1f), 0));
            BaseAttackInfo atk_init02 = new BaseAttackInfo(10, 10f, 0.5f);
            BaseAttackInfo atk_init03 = new BaseAttackInfo(2, 20f, 0.01f);
            sq.add(7, new Attack[] {
                    new LineAttack(atk_init03, 0),
                    new FallAttack(atk_init02, false, 1),
                    new LineAttack(atk_init03, 0),
                    new FallAttack(atk_init02, false, -1)});

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
            AttackSequence main = new AttackSequence();
            BaseAttackInfo atk_init = new BaseAttackInfo(3, 2f, 0.1f);

            main.add(5, new Attack[] {
                    new EllipseAttack(
                            atk_init,
                            new Line((float) Screen.middleX - 100, Screen.top),
                            new Line(new Range(0, Screen.middleX - 200), Screen.bottom)
                    ),
                    new EllipseAttack(
                            atk_init,
                            new Line((float) Screen.middleX - 100, Screen.top),
                            new Line(new Range((Screen.middleX + 200), Screen.right), Screen.bottom)
                    ),
            });
            main.add(5, new Attack[] {
                    new EllipseAttack(
                            atk_init,
                            new Line(Screen.left, Screen.top),
                            new Line(new Range(200, Screen.right), Screen.bottom)
                    ),
                    new EllipseAttack(
                            atk_init,
                            new Line((float) Screen.right - 200, (float)Screen.bottom),
                            new Line(Screen.xRange, Screen.bottom)
                    ),
            });


            main.add(new LineAttack(new BaseAttackInfo(2, 10f, 1f), 0.5f));
            main.add(new LineAttack(new BaseAttackInfo(2, 10f, 1f), 0.4f));
            main.add(new LineAttack(new BaseAttackInfo(2, 10f, 1f), 0.4f));

            BaseAttackInfo atk_init3 = new BaseAttackInfo(5, 5f, 0.5f);


            XOffsetModifier xmod = new XOffsetModifier(new Range(-300, 300), 4, XOffsetModifier.MIDDLE, -1);
            for(int i = 0; i < 3; i++) {
                 for(int j = 0; j < 6; j++) {
                     main.add(new EllipseAttack(atk_init3,
                             new Line(Screen.topMiddlePoint),
                             new Line(Screen.xRange.add(xmod.get()), Screen.bottom)));
                 }
            }

            main.get(main.size() - 1).offsetSec = 1f;
            BaseAttackInfo atk_init2 = new BaseAttackInfo(3, 10f, 0.3f);
            main.add(3, new Attack[] {
                    new LineAttack(atk_init2, 0),
                    new LineAttack(atk_init2, 0.1f),
                    new LineAttack(atk_init2, 0.2f),
                    new LineAttack(atk_init2, 0.4f),
                    new LineAttack(atk_init2, 0.8f),
                    new FallAttack(atk_init2, false, -1),
                    new LineAttack(atk_init2, 0.8f),
                    new LineAttack(atk_init2, 0.4f),
                    new LineAttack(atk_init2, 0.2f),
                    new LineAttack(atk_init2, 0.1f)
            },
                    1.2f);
            AttackSequence seq2 = new AttackSequence();
            BaseAttackInfo atk_init1 = new BaseAttackInfo(15, 5f, 1f);
            seq2.add(new CircleAttack(atk_init1, 1f/2, -3f/3));
            seq2.add(new CircleAttack(atk_init1, 1f/2, -2f/3));
            seq2.add(new CircleAttack(atk_init1, 1f/2, -1f/3));
            seq2.add(new LineAttack(new BaseAttackInfo(2, 10, 0.01f), 0.3f));
            seq2.add(new CircleAttack(atk_init1, 1f/2, 0));
            seq2.add(new LineAttack(new BaseAttackInfo(2, 10, 0.01f), 0.2f));
            seq2.add(new CircleAttack(atk_init1, 1f/2, 1f/6));
            seq2.add(new CircleAttack(atk_init1, 1f/2, 2f/6));
            seq2.add(new LineAttack(new BaseAttackInfo(4, 10, 0.01f), 0));
            seq2.add(new CircleAttack(atk_init1, 1f/2, 3f/6));
            seq2.add(new CircleAttack(atk_init1, 1f/2, 4f/6));
            seq2.add(new CircleAttack(atk_init1, 1f/2, 5f/6));
            seq2.add(new LineAttack(new BaseAttackInfo(2, 10, 0.01f), 0.2f));

            main.add(1, new AttackSequence[] {seq2});
            return main;
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
}