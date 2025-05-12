package com.at04.touchmovetest;
import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.lang.Math;
import java.util.ArrayList;
import java.util.List;

/**
 * Sets bullets in a circle pattern just off the top of the screen, which moves straight down across the screen.
 */
public class CircleAttack extends Attack{
    //radius is the distance from the center point to the edges
    public float radius;
    // this is the degree of rotation that the first plotted bullet will start from the bottom
    public float startOffSet;
    //this designates the direction the bullets are rotated from the first plotted bullet
    public boolean clockWise;
    public float centerScreen;
    //this keeps track of the angle between points based on count
    public double degreesBetween;

    /**
     * -1 = left edge of screen, 0 = center, 1 = right
     */
    public float offsetCenter; //Only used in the constructor, but needs to be stored for copy()
    public float inputRadius; //Only used in the constructor, but needs to be stored for copy()


    public CircleAttack(BaseAttackInfo atk_init, float inputRadius, float offsetCenter){
        super(atk_init);
        this.inputRadius = inputRadius; //stored to allow for copying
        this.radius = Screen.width * inputRadius;
        this.offsetCenter = offsetCenter;
        centerScreen= (Screen.middleX)+ ((Screen.middleX /2f) * offsetCenter);
        startOffSet=0;
        clockWise=false;
        degreesBetween=2*PI/count;
        bullets = new ArrayList<>();
    }
    public CircleAttack(BaseAttackInfo baseAttackInfo, List<AttackParameter> params) {
        super(baseAttackInfo);
        this.inputRadius = (float)params.get(0).unwrap();
        this.radius = Screen.width * inputRadius;
        this.offsetCenter = (float)params.get(1).unwrap();
        centerScreen=(Screen.middleX)+((Screen.middleX /2f) * offsetCenter);
        startOffSet=0;
        clockWise=false;
        degreesBetween=2*PI/count;
        bullets = new ArrayList<>();
    }

    /**
     * getInitializer() methods are like constructors that create AttackInfo objects instead of an actual
     * Attack. This is used when creating an AttackInfoList to store to Firebase.
     */
    public static AttackInfo getInitializer(BaseAttackInfo atkInit, float inputRadius, float offsetCenter) {
        ArrayList<AttackParameter> params = new ArrayList<>();
        params.add(new AttackParameter("float", inputRadius));
        params.add(new AttackParameter("float", offsetCenter));
        return new AttackInfo(AttackInfo.CIRCLE_ATTACK, atkInit, params);
    }

    @Override
    public Attack copy() {
        CircleAttack copy = new CircleAttack(new BaseAttackInfo(count, spd, offsetSec), inputRadius, offsetCenter);
        copy.registerPlayerPosition(null);
        copy.registerAttackManager(attackManager);
        return copy;
    }



    @Override
    protected void attackUpdate() {
        if(bullets != null) {
            for(int i = 0; i < bullets.size(); i++) {
                if(bullets.get(i)!=null){
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
    }

    @Override
    protected void initialize() {
        super.initialize();
        for(int i = 0; i < count; i++) {
            Point p = calcInitialPosition(i, count);
            Bullet b = AttackManager.initializeBullet(GameAssets.pinkStar, p, spd, (float)Math.toRadians(90));
            bullets.add(b);
        }
    }

    @Override
    public Point calcInitialPosition(int idx, int count) {
        float center = centerScreen;
        // xcos theta - ysin theta =rotated x about center
        float  xOffset=(float) (center-(radius*sin(degreesBetween*idx)));
        //xsin theta + ycostheta
        float  yOffset=(float) (radius-(radius*cos(degreesBetween*idx)));
        yOffset -= Screen.height / 2f;
        return new Point(xOffset, yOffset);
    }
    public boolean checkOffscreen(int i) {
        Bullet b = bullets.get(i);
        if (b.pos.y > (b.radius * 2) + Screen.bottom) {
            return true;
        }
        return false;
    }
}