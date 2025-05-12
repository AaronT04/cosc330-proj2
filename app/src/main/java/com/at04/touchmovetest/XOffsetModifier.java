package com.at04.touchmovetest;

/**
 * UNUSED:
 *          programmatically shift Attacks left and right across an AttackSequence.
 */
public class XOffsetModifier {
    public static final int MIDDLE = 0;
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    private final Range xRange;
    private final int numSteps;
    int curIdx;
    int dir;


    public XOffsetModifier(Range xRange, int numSteps, int start, int dir) {
        this.xRange = xRange;
        this.numSteps = numSteps;
        switch(start) {
            case XOffsetModifier.MIDDLE:
                curIdx = numSteps / 2;
                break;
            case XOffsetModifier.LEFT:
                curIdx = 0;
                break;
            case XOffsetModifier.RIGHT:
                curIdx = numSteps;
                break;
            default:
                curIdx = 0;
                break;
        }
        this.dir = (int)Math.copySign(1, dir);
    }

    public float get() {
        int calledIdx = curIdx;
        curIdx += 1;
        curIdx %= numSteps;
        return xRange.getStep(calledIdx, numSteps, dir);
    }


}
