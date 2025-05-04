package com.at04.touchmovetest;
import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.lang.Math;
import java.util.ArrayList;

public class CircleAttack extends Attack{
    //radius is the distance from the center point to the edges
    float radius;
    // this is the degree of rotation that the first plotted bullet will start from the bottom
    float startOffSet;
    //this designates the direction the bullets are rotated from the first plotted bullet
    boolean clockWise;
    float centerScreen;
    //this keeps track of the angle between points based on count
    double degreesBetween;
    private float spd;

    @Override
    public Attack copy() {
        CircleAttack copy = new CircleAttack(count, offsetSec);
        copy.registerPlayerPosition(null);
        copy.registerAttackManager(attackManager);
        return copy;
    }

    public CircleAttack(int count, float offsetSec) {
        super(count, offsetSec);
        this.count = count;
        this.offsetSec = offsetSec;
        if(offsetSec > 0) {
            timer = new CountdownTimer((long) (offsetSec * 1000));
            timer.setActive();
        }
        radius =(DisplaySize.screenWidth)/6;
        centerScreen=(DisplaySize.screenWidth)/2;
        startOffSet=0;
        clockWise=false;
        degreesBetween=2*PI/count;
        spd=10;
        bullets = new ArrayList<>();

    }
    //radius is the fraction denomator of the screen that the attack should take up
    public CircleAttack(int count, float offsetSec,int radius){
        super(count, offsetSec);
        this.count = count;
        this.offsetSec = offsetSec;
        if(offsetSec > 0) {
            timer = new CountdownTimer((long) (offsetSec * 1000));
            timer.setActive();
        }
        this.radius =(DisplaySize.screenWidth)/(float)radius;
        centerScreen=(DisplaySize.screenWidth)/2;
        startOffSet=0;
        clockWise=false;
        degreesBetween=2*PI/count;
        spd=10;
        bullets = new ArrayList<>();

    }
    public CircleAttack(int count, float offsetSec,float radius,float offSetCenter){
        super(count, offsetSec);
        this.count = count;
        this.offsetSec = offsetSec;
        if(offsetSec > 0) {
            timer = new CountdownTimer((long) (offsetSec * 1000));
            timer.setActive();
        }
        this.radius =(DisplaySize.screenWidth)/radius;
        centerScreen=((DisplaySize.screenWidth)/2f)+(DisplaySize.screenWidth/offSetCenter);
        startOffSet=0;
        clockWise=false;
        degreesBetween=2*PI/count;
        spd=10;
        bullets = new ArrayList<>();

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
        for(int i = 0; i < count; i++) {
            Position p = calcInitialPosition(i, count);
            Bullet b = AttackManager.initializeBullet(GameAssets.pinkStar, p, spd, (float)Math.toRadians(90));
               bullets.add(b);


        }
    }

    @Override
    public Position calcInitialPosition(int idx, int count) {
        float center = centerScreen;
        // xcos theta - ysin theta =rotated x about center
        float  xOffset=(float) (center-(radius*sin(degreesBetween*idx)));
        //xsin theta + ycostheta
        float  yOffset=(float) (radius-(radius*cos(degreesBetween*idx)));
        return new Position(xOffset, yOffset);
    }
    public boolean checkOffscreen(int i) {
        Bullet b = bullets.get(i);
        if (b.pos.y > (b.radius * 2) + DisplaySize.screenHeight) {
            return true;
        }
        return false;
    }
}
