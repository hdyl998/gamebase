package com.hdyl.tetris2.shape;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import com.hdyl.tetris2.GameColor;

import java.util.Random;

/**
 * Created by liugd on 2017/9/4.
 */

public class Cell {
    public byte value;
    public byte color;

    public Cell() {
    }

    public boolean isValueFull() {
        return VALUE_FULL == value;
    }

    public byte getColor() {
        return color;
    }

    public byte getValue() {
        return value;
    }

    public void setColor(byte color) {
        this.color = color;
    }

    public void setValue(byte value) {
        this.value = value;
    }

    public void setValueFull() {
        this.value = VALUE_FULL;
    }

    public void reset() {
        this.value = VALUE_NONE;
    }

    /**
     * 绘制方法
     *
     * @param canvas
     * @param rect
     */
    public void draw(Canvas canvas, Rect rect) {
        if (value == 0) {
            canvas.drawBitmap(GameColor.getBitmapBlack(), null, rect, null);
        } else {
            canvas.drawBitmap(GameColor.getBitmapAtIndex(color), null, rect, null);
        }
    }

    public void drawNoZero(Canvas canvas, Rect rect) {
        if (value != 0)
            canvas.drawBitmap(GameColor.getBitmapAtIndex(color), null, rect, null);
    }

    public final static byte VALUE_NONE = 0;
    public final static byte VALUE_FULL = 1;
}
