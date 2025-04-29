package com.at04.touchmovetest;

public abstract class Path {
    public abstract void move();
    public abstract void update(Bullet b);
    public abstract void updateTarget(Position p);
}
