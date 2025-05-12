package com.at04.touchmovetest;

/**
 * UNUSED:
 *          programmatically shift Attacks left and right across an AttackSequence
 */
public class XOffsetModifier {
    public int magnitudeEach;
    public int initialDirection;
    public int numberOfSteps;
    public int currentDir;
    public int stepsTaken;

    public XOffsetModifier(int magnitudeEach, int initialDirection, int numberOfSteps) {
        this.initialDirection = initialDirection;
        this.magnitudeEach = magnitudeEach;
        this.numberOfSteps = numberOfSteps;
        currentDir = initialDirection;
        stepsTaken = numberOfSteps / 2;
    }

    public void takeOneStep(){
        stepsTaken += 1;
        if(stepsTaken > numberOfSteps) {
            currentDir *= -1;
            stepsTaken = 0;
        }
    }
}
