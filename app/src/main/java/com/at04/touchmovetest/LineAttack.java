package com.at04.touchmovetest;

public class LineAttack extends Attack {

    private float hzInsetRatio;
    @Override
    public Point calcInitialPosition(int idx, int count) {
        float hzInset = hzInsetRatio * (float)(DisplaySize.screenWidth - 200);
        float offsetFromLeftPerIndex = ((float)(DisplaySize.screenWidth - 200) - (hzInset * 2)) / (count - 1);
        return new Point(hzInset + offsetFromLeftPerIndex * idx, 0 - GameAssets.arrow.getHeight());
    }

    public LineAttack(BaseAttackInfo atk_init, float hzInsetRatio) {
        super(atk_init);
        this.hzInsetRatio = hzInsetRatio;
    }
    public void initialize() {
        for(int i = 0; i < count; i++) {
            Point p = calcInitialPosition(i, count);
            Bullet b = AttackManager.initializeBullet(GameAssets.arrow, p, spd, (float)Math.toRadians(90));
            bullets.add(b);
        }
    }
    public void attackUpdate() {
        if(bullets != null) {
            for(int i = 0; i < bullets.size(); i++) {
                Bullet b = bullets.get(i);
                b.move();
                if(checkOffscreen(i)) {
                    b.unloadAndReset();
                    bullets.remove(i);
                    i--;
                }
                b.update();
                if (bullets.isEmpty()) {
                    attackManager.notifyAttackOffscreen(this);
                }
            }
        }
    }
    public boolean checkOffscreen(int i) {
        Bullet b = bullets.get(i);
        if (b.pos.y > (b.radius * 2) + DisplaySize.screenHeight) {
            return true;
        }
        return false;
    }

    public Attack copy(){
        LineAttack copy = new LineAttack(new BaseAttackInfo(count, spd, offsetSec), hzInsetRatio);
        copy.registerPlayerPosition(null);
        copy.registerAttackManager(attackManager);
        return copy;
    }
}