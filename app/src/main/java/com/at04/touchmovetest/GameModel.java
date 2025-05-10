package com.at04.touchmovetest;
import static com.at04.touchmovetest.GameLoop.FPS;

import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.view.View;
import android.util.Log;

import java.util.ArrayList;

public class GameModel {
    public Player player;

    public AttackManager attackManager;
    public Level context;
    private Timer profileTimer = new Timer();
    private HealthBar healthBar;

    public static final long hitbuffer_ms = 500;
    public int hitsLeft;
    public int hitCount = 6;
    private CountdownTimer hitTimer = new CountdownTimer(hitbuffer_ms);

    public GameModel(Level l) {
        this.context = l;
        initialize(LevelStorage.getLevelInitializer(l.levelID));
    }

    public void startGame() {
        context.startGame();
        attackManager.startAttacks();
    }

    private void initialize(LevelInitializer li) {
        player = li.setPlayer(this.context);
        player.registerHitTimer(this.hitTimer);
        attackManager = new AttackManager();
        AttackSequence mainSequence = li.getAttackSequence();
        //If a level has not been properly downloaded from the database,
        //"li.getAttackSequence()" may return an empty ArrayList
        //in that case, quit and go back to level menu
        if(mainSequence.isEmpty()) {
            context.end();
        }
        attackManager.setSequence(mainSequence);
        attackManager.registerPlayerPosition(player.pos);
        attackManager.registerModel(this);
        hitsLeft = hitCount;
        healthBar = new HealthBar(30, 30, 100, hitCount);
    }
    public void update() {
        //Log.d("gameModel.update()", "");
        handleCollision();
        healthBar.update(hitsLeft);
        player.update();
        attackManager.update();
    }
    public void draw(Canvas canvas) {

        if(canvas!= null) {
            canvas.drawColor(0, PorterDuff.Mode.CLEAR);
            canvas.drawBitmap(GameAssets.bg_sky, 0, 0, null);
            healthBar.draw(canvas);
            player.draw(canvas);
            //profileTimer.start();
            attackManager.draw(canvas);
            //profileTimer.debugStop("attackManager.draw(canvas)");
        }
    }

    public void handleCollision() {
        hitTimer.update((1000 / FPS));
        boolean collision = checkCollision();
        if(collision) {
            Log.d("Collision", "HitsLeft: " + hitsLeft);
            if(!(hitTimer.isActive())) {
                hitsLeft--;
                hitTimer.setActive();
            }
            if(hitsLeft == 0) {
                Log.d("hitsLeft < 0", "end()");
                this.context.end();
            }
        }
    }
    private boolean checkCollision() {

        for(int i = 0; i < AttackManager.MAX_BULLETS; i++) {
            Bullet b = AttackManager.bullets[i];
            if(b.isLoaded) {
                if (!(player.bounds.left > b.bounds.right || player.bounds.right < b.bounds.left
                        || player.bounds.bottom > b.bounds.top || player.bounds.top < b.bounds.bottom))
                    return true;
            }
        }
        return false;
    }
}