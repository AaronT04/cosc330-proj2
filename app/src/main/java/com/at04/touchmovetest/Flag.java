package com.at04.touchmovetest;

public class Flag {
    private boolean flag;
    private Runnable run;
    public Flag() {
        flag = false;
    }
    public Flag(boolean b, Runnable run) {
        flag = b;
        this.run = run;
    }
    public Flag(boolean b) {
        flag = b;
    }
    public void set() {
        flag = true;
    }
    public void check() {
        if(flag) {
            new Thread(run).start();
            flag = false;
        }
    }
    public void check(Runnable r) {
        if(flag) {
            new Thread(r).start();
            flag = false;
        }
    }
}
