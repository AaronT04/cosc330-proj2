package com.at04.touchmovetest;

import java.util.ArrayList;
import java.util.List;

public class EllipseAttack extends Attack{

    private Line startLine;
    private Line stopLine;
    private BulletInfo bulletInfo;
    private float t_param = (float)Math.toRadians(-90);
    private Point origin;

    public EllipseAttack(BaseAttackInfo atk_init, Line startLine, Line stopLine) {
        super(atk_init);
        this.startLine = startLine;
        this.stopLine = stopLine;
        bulletInfo = new BulletInfo(count);
    }
    public EllipseAttack(BaseAttackInfo atk_init, List<AttackParameter> params) {
        super(atk_init);
        this.startLine = AttackParameter.unflattenLine(params.subList(0, 4));
        this.stopLine = AttackParameter.unflattenLine(params.subList(4, 8));
        bulletInfo = new BulletInfo(count);
    }
    @Override
    public Attack copy() {
        EllipseAttack copy = new EllipseAttack(new BaseAttackInfo(count, spd, offsetSec), startLine, stopLine);
        copy.registerPlayerPosition(null);
        copy.registerAttackManager(attackManager);
        return copy;
    }

    @Override
    protected void attackUpdate() {
        for(int i = 0; i < count; i++) {
            if(!bulletInfo.removed[i]) {
                Bullet b = bullets.get(i);
                moveParametric(origin, b.pos, bulletInfo.hRadius[i], bulletInfo.vRadius[i]);
                checkBulletOffscreen(i);
                b.update();
                checkAttackOffscreen();
            }
        }
        t_param +=  (spd * 0.005f);//(spd / (bulletInfo.hRadius[0]));
        t_param %= (float)Math.toRadians(360);
    }
    private void moveParametric(Point origin, Point pos, float hRad, float vRad) {
        pos.x = origin.x + hRad * (float)Math.cos(t_param);
        pos.y = origin.y + vRad * (float)Math.sin(t_param);
    }

    private void checkAttackOffscreen() {
        int numRemoved = 0;
        for(int j = 0; j < count; j++) {
            if(bulletInfo.removed[j])
                numRemoved++;
        }
        if(numRemoved == count) {
            attackManager.notifyAttackOffscreen(this);
        }
    }
    private void checkBulletOffscreen(int i) {
        Bullet b = bullets.get(i);
        if(b.pos.y >=DisplaySize.screenHeight) {
            bulletInfo.removed[i] = true;
            b.unloadAndReset();
        }
    }

    @Override
    protected void initialize() {
        Point[] startLinePoints = startLine.getSegmentPoints(count);
        Point[] stopLinePoints = stopLine.getSegmentPoints(count);
        for(int i = 0; i < count; i++) {
            origin = new Point(startLinePoints[0].x, stopLinePoints[0].y);
            bulletInfo.hRadius[i] = stopLinePoints[i].x - startLinePoints[i].x;
            bulletInfo.vRadius[i] = stopLinePoints[i].y - startLinePoints[i].y;
            Bullet b = AttackManager.initializeBullet(GameAssets.pinkStar,
                                                            startLinePoints[i], spd, 0);
            bullets.add(b);
        }
    }

    public static AttackInfo getInitializer(BaseAttackInfo atk_init, Line startLine, Line stopLine) {
        ArrayList<AttackParameter> params = new ArrayList<>();
        params.addAll(AttackParameter.flattenLine(startLine));
        params.addAll(AttackParameter.flattenLine(stopLine));
        return new AttackInfo(AttackInfo.ELLIPSE_ATTACK, atk_init, params);
    }

    @Override
    public Point calcInitialPosition(int idx, int count) {
        return null;
    }

    static class BulletInfo {
        private float[] hRadius;
        private float[] vRadius;
        private boolean[] removed;

        public BulletInfo(int count) {
            hRadius = new float[count];
            vRadius = new float[count];
            removed = new boolean[count];
        }
    }

}
