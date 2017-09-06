package com.hdyl.tetris;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import com.hdyl.tetris2.GameColor;

/**
 * 游戏小格子，有颜色，是否填满两个属性
 * Created by liugd on 2017/5/5.
 */

public class Cell {
    protected int resIndex;
    protected boolean isFull;


    public Cell setFull(boolean full) {
        isFull = full;
        return this;
    }

    public void exchangeValue(Cell cell) {
        int tempColor = cell.getResIndex();
        cell.setResIndex(this.getResIndex());
        this.setResIndex(tempColor);
        boolean tempBoolean = cell.isFull();
        cell.setFull(this.isFull());
        this.setFull(tempBoolean);
    }

    public void setValue(Cell cell) {
        this.setResIndex(cell.getResIndex());
        this.setFull(cell.isFull());
    }


    public boolean isFull() {
        return isFull;
    }

    public Cell setResIndex(int color) {
        this.resIndex = color;
        return this;
    }

    public void clear() {
        setResIndex(0).setFull(false);
    }

    public int getResIndex() {
        return resIndex;
    }


    public void draw(Canvas canvas, RectF rect) {
        Bitmap bitmap = GameColor.getBitmapAtIndex(this.getResIndex());
        canvas.drawBitmap(bitmap, null, rect, null);
    }
}
