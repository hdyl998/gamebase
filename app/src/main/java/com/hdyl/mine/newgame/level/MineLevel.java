package com.hdyl.mine.newgame.level;

/**
 * Created by Administrator on 2018/7/7.
 */

public class MineLevel {

    protected float mineRate;
    protected int width;//宽
    protected int height;//高
    protected int mineNum;
    protected String note;


    public MineLevel() {
    }

    public MineLevel(int width, int height, int mineNum) {
        initLevelInfo(width, height, mineNum);
    }

    protected void initLevelInfo(int width, int height, int mineNum) {
        this.width = width;
        this.height = height;
        this.mineNum = mineNum;
        this.mineRate = this.mineNum * 1f / (this.width * this.height);
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float getMineRate() {
        return mineRate;
    }

    public int getMineNum() {
        return mineNum;
    }

    public MineLevel setNote(String note) {
        this.note = note;
        return this;
    }
}
