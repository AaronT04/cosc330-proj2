package com.at04.touchmovetest;

import android.view.View;

import java.util.ArrayList;

public abstract class LevelInitializer {
    public Player setPlayer(Level context) {
        Player player = new Player(GameAssets.playerSprite);// = context.findViewById(R.id.square);
        return player;
    }
    public abstract AttackSequence getAttackSequence();

}
