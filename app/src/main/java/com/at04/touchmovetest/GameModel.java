package com.at04.touchmovetest;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.view.View;
import android.util.Log;

import java.util.ArrayList;

public class GameModel {
    public Player player;

    public ArrayList<Bullet> bullets;
    public GameLoop gameLoop;
    public AttackManager attackManager;
    public Level context;
    public View[] display;
    private Timer profileTimer = new Timer();
    public GameModel(Level l) {
        //Log.d("l", String.valueOf(l));
        //Log.d("l.levelID", String.valueOf(l.levelID));
        this.context = l;

        LevelInitializer test_LI;
        if(l.levelID == 0) {
            test_LI = new Level00Initializer();
        }
        else if(l.levelID == 1) {
            test_LI = new Level01Initializer();
        }
        else {
            test_LI = new Level00Initializer();
        }

        initialize(test_LI);//[level.levelID]);
    }

    public void startGame() {
        context.startGame();
    }

    private void initialize(LevelInitializer li) {
        player = li.setPlayer(this.context);
        attackManager = new AttackManager();
        attackManager.setSequence(li.getAttackSequence());
        attackManager.registerPlayerPosition(player.pos);
        bullets = attackManager.getActiveBullets();
        display = li.setViews(this.context);
    }
    public void update() {
        //Log.d("gameModel.update()", "");
        player.update();
        attackManager.update();
    }
    public void draw(Canvas canvas) {

        if(canvas!= null) {
            canvas.drawColor(0, PorterDuff.Mode.CLEAR);
            player.draw(canvas);
            //profileTimer.start();
            attackManager.draw(canvas);
            //profileTimer.debugStop("attackManager.draw(canvas)");
        }
    }
    public boolean checkCollision() {
        //bullets = attackManager.getActiveBullets();

        for(int i = 0; i < AttackManager.MAX_BULLETS; i++) {
            Bullet b = AttackManager.bullets[i];
            if(b != null) {
                if (!(player.bounds.left > b.bounds.right || player.bounds.right < b.bounds.left
                        || player.bounds.bottom > b.bounds.top || player.bounds.top < b.bounds.bottom))
                    return true;
            }
        }
        return false;
    }
}