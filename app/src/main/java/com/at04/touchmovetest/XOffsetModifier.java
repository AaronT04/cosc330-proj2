package com.at04.touchmovetest;

/**
 * UNUSED:
 *          programmatically shift Attacks left and right across an AttackSequence.
 *          The logic in move() is wrong.
 */
public class XOffsetModifier {
    private final int magnitudeEach;
    private final int numberOfSteps;
    private int currentDir;
    private int stepsTaken;
    private boolean oscillate;

    public XOffsetModifier(int totalWidth, int initialDirection, int numberOfSteps, int startIdx, boolean oscillate) {
        this.magnitudeEach = totalWidth / numberOfSteps;
        this.numberOfSteps = numberOfSteps;
        this.oscillate = oscillate;
        currentDir = initialDirection;
        stepsTaken = startIdx;
    }

    public float get(){
                return currentDir * stepsTaken * magnitudeEach;
    }
    public void move() {
        if(oscillate) {
            stepsTaken += currentDir;
            if(Math.abs(stepsTaken) > numberOfSteps * 2) {
                currentDir *= -1;
                stepsTaken = numberOfSteps;
            }
        }
        else {
            stepsTaken += 1;
            if (stepsTaken > numberOfSteps) {
                stepsTaken = 0;
            }
        }
    }
}
