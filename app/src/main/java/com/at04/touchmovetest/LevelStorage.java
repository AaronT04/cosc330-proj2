package com.at04.touchmovetest;

public class LevelStorage {

    private LevelStorage() {}
    private static final LevelInitializer[] initializers = new LevelInitializer[]
            {
                    new Level00Initializer(),
                    new Level01Initializer(),
                    new Level02Initializer(),
            };
    public static LevelInitializer getLevelInitializer(int levelID) {
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
            /*
            seq.add(5, new Attack[] {
                    new EllipseAttack(
                            new BaseAttackInfo(5, 1f, 0.1f),
                            new Line(DisplaySize.screenWidth, new Range(0, 200)), //startLine
                            new Line(new Range(0, DisplaySize.screenWidth), DisplaySize.screenHeight)//stopLine
                    ),
                    new EllipseAttack(
                            new BaseAttackInfo(5, 1f, 0.1f),
                            new Line(100, new Range(100, 200)), //startLine
                            new Line(new Range(100, DisplaySize.screenWidth), DisplaySize.screenHeight)//stopLine
                    )
            });
            */
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
}
