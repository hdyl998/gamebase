package com.hdyl.tetris;

/**
 * Created by liugd on 2017/5/5.
 */

public class PositionCell extends Cell {

    public int x;
    public int y;

    public PositionCell() {
    }


    public PositionCell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public PositionCell setFull(boolean full) {
        isFull = full;
        return this;
    }

    /**
     * 交换XY
     */
    public void exchangeXY() {
        int temp = this.x;
        this.x = this.y;
        this.y = temp;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
