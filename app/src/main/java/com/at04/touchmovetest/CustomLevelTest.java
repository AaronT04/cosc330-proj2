package com.at04.touchmovetest;

/**
 * Used to add custom levels to the database before a LevelCreator activity has been made.
 * This is used to make sure that saving/loading levels to Firebase works properly.
 */
public class CustomLevelTest {
    public static void testmethod_add03todatabase() {
        AttackInfoList atkInfoList = new AttackInfoList();

        BaseAttackInfo atk_init0 = new BaseAttackInfo(10, 5, 1);
        atkInfoList.add(CircleAttack.getInitializer(atk_init0, 1f/2, -1f/2));
        atkInfoList.add(CircleAttack.getInitializer(atk_init0, 1f/2, 1f/2));
        atkInfoList.add(CircleAttack.getInitializer(atk_init0, 1f/3, 0f));
        BaseAttackInfo simult_atk1 = new BaseAttackInfo(10, 2f, 0.001f);
        atkInfoList.add(CircleAttack.getInitializer(simult_atk1, 1f/3, 0f));
        atkInfoList.add(CircleAttack.getInitializer(simult_atk1, 1f/2, 0f));
        atkInfoList.add(CircleAttack.getInitializer(simult_atk1, 1f/4, 0f));

        LevelStorage.saveToDatabase(atkInfoList, "Level03 test", "Aaron", "1");
    }
    public static void testmethod_addlinetesttodatabase() {
        AttackInfoList atkInfoList = new AttackInfoList();
        AttackInfoList subList = new AttackInfoList();
        subList.add(5, new AttackInfo[]{
                LineAttack.getInitializer(new BaseAttackInfo(2, 5, 0.3f), 0),
                LineAttack.getInitializer(new BaseAttackInfo(3, 10, 0.3f), 0.1f)
        });
        subList.add(FallAttack.getInitializer(new BaseAttackInfo(2, 10, 0.5f),false, 1));
        subList.add(FallAttack.getInitializer(new BaseAttackInfo(3, 15, 0.5f), false, -1));

        atkInfoList.add(5, new AttackInfoList[]{subList});
        LevelStorage.saveToDatabase(atkInfoList, "Line test", "Aaron", "0");
    }
    public static void testmethod_addellipsetesttodatabase() {
        AttackInfoList atkInfoList = new AttackInfoList();

        atkInfoList.add(5, new AttackInfo[] {
                EllipseAttack.getInitializer(new BaseAttackInfo(3, 2, 0.1f),
                        new Line((float)(Screen.width / 2) - 100, new Range(0, 0)),
                        new Line(new Range(0, (Screen.width / 2) + 200), Screen.height))});

        LevelStorage.saveToDatabase(atkInfoList, "Ellipse test", "Aaron", "2");
    }
}
