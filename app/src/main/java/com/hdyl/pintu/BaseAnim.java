package com.hdyl.pintu;

import android.graphics.Canvas;
import android.graphics.RectF;

import com.hdyl.tetris.Cell;
import com.hdyl.tetris.GameBoard;

import java.util.List;

/**
 * Created by Administrator on 2017/9/9.
 */

public class BaseAnim {
    public int during = 120;
    public int costTime = during;

    public int cellArrs[][];

    public int aimX;//去的x的地方
    public int aimY;//去的y的地方
    public  int value;


    public void setValue(int value) {
        this.value = value;
    }

    public int startX;
    public int startY;


    public void setStartX(int startX) {
        this.startX = startX;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }


    public void setAimX(int aimX) {
        this.aimX = aimX;
    }

    public void setAimY(int aimY) {
        this.aimY = aimY;
    }

    public void setDuring(int during) {
        this.during = during;
    }

//    public void init() {
//        cellArrs = new int[4][4];
//    }

    public BaseAnim() {
//        init();
    }


//    protected void copy(int cellArrs[][]) {
//        for (int i = 0; i < 4; i++) {
//            for (int j = 0; j < 4; j++) {
//                this.cellArrs[i][j] = cellArrs[i][j];
//            }
//        }
//    }

//    public void draw(Canvas canvas, float size) {
//        RectF rect = new RectF();
//        Cell[][] cellArrs = this.getCellArrs();
//        for (int i = 0; i < 4; i++) {
//            for (int j = 0; j < 4; j++) {
//                Cell cell = cellArrs[i][j];
//                if (cell.isFull()) {
//                    rect.left = j * size;
//                    rect.right = rect.left + size;
//                    rect.top = i * size;
//                    rect.bottom = rect.top + size;
//                    cell.draw(canvas, rect);
//                }
//            }
//        }
//    }


    public int[][] getCellArrs() {
        return cellArrs;
    }

    public void clearAnim() {
        costTime = during;
    }


    public void start() {
        costTime = 0;
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
