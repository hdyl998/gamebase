package com.hdyl.tetris;

import android.graphics.Canvas;

import com.hdyl.tetris.shape.TetrisShape;

/**
 * Created by Administrator on 2017/9/9.
 */

public class DownAnim extends BaseAnim {

    int distence;
    TetrisShape shape;
    int xOffset;
    int yOffset;

    public void addAnim(Cell cells[][], TetrisShape shape, int distence, int xOffset, int yOffset) {
        setDuring(200);
        this.shape = shape;
        this.distence = distence;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.costTime = 0;
        copy(cells);
    }

    public int getXOffset() {
        return xOffset;
    }

    public int getYOffset() {
        return yOffset;
    }

    public boolean isDitenece1() {
        return 1 == distence;
    }

    public TetrisShape getShape() {
        return shape;
    }

    @Override
    public void draw(Canvas canvas, float size) {
        super.draw(canvas, size);//画面板，
        shape.drawPx(canvas, size, (int) (xOffset * size), (int) (yOffset * size + distence * getPercent() * size));
    }

}
