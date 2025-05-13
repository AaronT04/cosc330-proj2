package com.at04.touchmovetest;
import static com.at04.touchmovetest.GameLoop.FPS;

import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.util.Log;

public class GameModel {
    public Player player;

    public AttackManager attackManager;
    public Level context;
    private Timer profileTimer = new Timer();
    private HealthBar healthBar;

    public static final long hitbuffer_ms = 500;
    public int playerHealth;
    public int hitCount = 6;
    private final CountdownTimer invincibilityTimer = new CountdownTimer(hitbuffer_ms);

    public GameModel(Level l) {
        this.context = l;
        createPlayer();
        createHealthBar();
        LevelInitializer li = LevelStorage.getLevelInitializer(l.levelID);
        getBackground(li);
        AttackSequence mainSequence = li.getAttackSequence();
        setupAttackManager(mainSequence);
    }

    public GameModel(Level l, AttackInfoList atkList, int bgID) {
        this.context = l;
        createPlayer();
        createHealthBar();
        getBackground(bgID);
        AttackSequence mainSequence = LevelStorage.createSequenceFromInfoList(atkList);
        setupAttackManager(mainSequence);
    }
    private void setupAttackManager(AttackSequence mainSequence) {
        attackManager = new AttackManager();
        if(mainSequence.isEmpty()) {
            context.end();
        }
        attackManager.setSequence(mainSequence);
        attackManager.registerPlayerPositionWithAttacks(player.pos);
        attackManager.registerModel(this);
    }
    private void createPlayer() {
        player = new Player(GameAssets.playerSprite);
        player.registerInvincibilityTimer(this.invincibilityTimer);
    }
    private void createHealthBar() {
        playerHealth = hitCount;
        healthBar = new HealthBar(75, 75, 100, hitCount);
    }
    private void getBackground(int bgID) {
        switch(bgID) {
            case CustomLevelListEntry.BG_LIGHT:
                GameAssets.bg = GameAssets.bg_sky;
                break;
            case CustomLevelListEntry.BG_DARK:
                GameAssets.bg = GameAssets.bg_sky_dark;
                GameAssets.health_border = GameAssets.health_border_dark;
                break;
            default: //By default, the background is already set to "bg_sky"
                break;
        }
    }
    private void getBackground(LevelInitializer li) {
        li.setBackground();
    }

    public void startGame() {
        attackManager.startAttacks();
        context.startGame(); //Has to be called *after* startAttacks -
                            //Otherwise, there's a small random chance of a nullPointerException
    }
    public void update() {
        //Log.d("gameModel.update()", "");
        handleCollision();
        healthBar.update(playerHealth);
        player.update();
        attackManager.update();
    }
    public void draw(Canvas canvas) {

        if(canvas!= null) {
            canvas.drawColor(0, PorterDuff.Mode.CLEAR); //Erases the entire canvas
            canvas.drawBitmap(GameAssets.bg, 0, 0, null); //BG is drawn first
            player.draw(canvas);
            attackManager.draw(canvas);
            healthBar.draw(canvas); //Draw on top layer
        }
    }

    /**
     * Checks for collision between player and bullets on every frame.
     * It only reduces the hit count (health) if the hitTimer (invincibility frames) is not active
     */
    public void handleCollision() {
        //invincibilityTimer.updateTimeElapsed((1000 / FPS));
        boolean collision = checkCollision();
        if(collision) {
            //Log.d("Collision", "HitsLeft: " + hitsLeft);
            if(!(invincibilityTimer.isActive())) {
                playerHealth--;
                invincibilityTimer.startTimer();
            }
            if(playerHealth == 0) {
                //Log.d("hitsLeft < 0", "end()");
                this.context.end();
            }
        }
    }

    /**
     * Checks for collision between the player and all bullets
     * @return true if the player is touching a bullet
     */
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