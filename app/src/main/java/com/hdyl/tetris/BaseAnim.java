package com.hdyl.tetris;

import android.graphics.Canvas;
import android.graphics.RectF;

import java.util.List;

/**
 * Created by Administrator on 2017/9/9.
 */

public class BaseAnim {
    public int during = 500;
    public int costTime = during;

    public Cell cellArrs[][];

    List<Integer> animLines;


    public void setDuring(int during) {
        this.during = during;
    }

    public void init() {
        cellArrs = new Cell[GameBoard.yCount][GameBoard.xCount];
        for (int i = 0; i < GameBoard.yCount; i++) {
            for (int j = 0; j < GameBoard.xCount; j++) {
                cellArrs[i][j] = new Cell();
            }
        }
    }

    public BaseAnim() {
        init();
    }


    protected void copy(Cell cellArrs[][]) {
        for (int i = 0; i < GameBoard.yCount; i++) {
            for (int j = 0; j < GameBoard.xCount; j++) {
                this.cellArrs[i][j].setValue(cellArrs[i][j]);
            }
        }
    }

    public void draw(Canvas canvas, float size) {
        RectF rect = new RectF();
        Cell[][] cellArrs = this.getCellArrs();
        for (int i = 0; i < GameBoard.yCount; i++) {
            for (int j = 0; j < GameBoard.xCount; j++) {
                Cell cell = cellArrs[i][j];
                if (cell.isFull()) {
                    rect.left = j * size;
                    rect.right = rect.left + size;
                    rect.top = i * size;
                    rect.bottom = rect.top + size;
                    cell.draw(canvas, rect);
                }
            }
        }
    }


    public Cell[][] getCellArrs() {
        return cellArrs;
    }

    public void clearAnim() {
        costTime = during;
    }


    public boolean isEnd() {
        if (costTime >= during) {
            return true;
        }
        return false;
    }


    public float getPercent() {
        if (isEnd()) {
            return 1f;
        }
        return 1f * costTime / during;
    }


    //加时间
    public void ticker(int costTime) {
        this.costTime += costTime;
    }

    public boolean isAnim() {
        if (isEnd()) {
            return false;
        }
        return true;
    }
}
