package com.hdyl.tetris;

import android.graphics.Canvas;
import android.graphics.RectF;

/**
 * Created by Administrator on 2017/9/9.
 */

public abstract class BaseAnim {
    public int during;
    private long endTime;

    public Cell cellArrs[][];





    public abstract int getDuring();

//    public void setDuring(int during) {
//        this.during = during;
//        endTime=System.currentTimeMillis()+during;
//    }

//    public int getDuring() {
//        return during;
//    }

    public void resetEndTime() {
        this.endTime = during+System.currentTimeMillis();
    }

    private static final String TAG = "BaseAnim";



    public void init() {
        cellArrs = new Cell[GameBoard.yCount][GameBoard.xCount];
        for (int i = 0; i < GameBoard.yCount; i++) {
            for (int j = 0; j < GameBoard.xCount; j++) {
                cellArrs[i][j] = new Cell();
            }
        }
        during=getDuring();
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
    RectF rect = new RectF();
    public void draw(Canvas canvas, float size) {
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
        endTime = 0;
    }


    public boolean isEnd() {
        if (endTime < System.currentTimeMillis()) {
            return true;
        }
        return false;
    }


    public float getPercent() {
        if (isEnd()) {
            return 1f;
        }
        return 1-1f * (endTime-System.currentTimeMillis()) / during;
    }


//    //加时间
//    public void ticker(int costTime) {
////        this.endTime += endTime;
//    }

    public boolean isAnim() {
        if (isEnd()) {
            return false;
        }
        return true;
    }
}
