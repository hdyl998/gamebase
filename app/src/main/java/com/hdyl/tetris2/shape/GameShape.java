package com.hdyl.tetris2.shape;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.hdyl.tetris2.GameColor;

/**
 * Created by liugd on 2017/7/25.
 */

public class GameShape {
    public byte colorIndex;//资源编号

    public Cell arr[][];


    public int locateX;
    public int locateY;

    public int oneSize;


    public int getX() {
        return (locateX + oneSize / 2) / oneSize;
    }

    public int getY() {
        return (locateY + oneSize / 2) / oneSize;
    }

    public void setLocateX(int locateX) {
        this.locateX = locateX;
    }

    public void setLocateY(int locateY) {
        this.locateY = locateY;
    }

    private GameShape() {
        setColorIndex(GameColor.getRandomResIndex());
        String str = AllShapes.getRandomShape();
        String arrs[] = str.split(",");
        arr = new Cell[arrs.length][arrs[0].length()];
        for (int i = 0; i < arrs.length; i++) {
            String tt = arrs[i];
            for (int j = 0; j < tt.length(); j++) {
                Cell cell = new Cell();
                arr[i][j] = cell;
                cell.setValue((byte) (tt.charAt(j) - '0'));
                cell.setColor(colorIndex);
            }
        }
    }


    public Cell[][] getArr() {
        return arr;
    }

    public void setColorIndex(byte colorIndex) {
        this.colorIndex = colorIndex;
    }


    public byte getColorIndex() {
        return colorIndex;
    }

    /***
     * 随机产生方块
     *
     * @return
     */
    public static GameShape createRandomShape() {
        return new GameShape();
    }


    public boolean contains(int x, int y) {
        return getLocateRect().contains(x, y);
    }

    public Rect getLocateRect() {
        Rect rect = new Rect();
        rect.left = locateX;
        rect.top = locateY;
        rect.right = locateX + getXCount() * oneSize;
        rect.bottom = locateY + getYCount() * oneSize;
        return rect;
    }


    public int getXCount() {
        return this.getArr()[0].length;
    }

    public int getYCount() {
        return this.getArr().length;
    }


    public boolean isPositionCellFull(int x, int y) {
        return getArr()[y][x].isValueFull();
    }

    public void move(int x, int y) {
        locateX += x;
        locateY += y;
    }


    public void setOneSize(int oneSize) {
        this.oneSize = oneSize;
    }

    public void draw(Canvas canvas) {
        Rect rect = new Rect();
        Cell cells[][] = this.getArr();
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                rect.left = j * oneSize + locateX;
                rect.right = rect.left + oneSize;
                rect.top = i * oneSize + locateY;
                rect.bottom = rect.top + oneSize;
                cells[i][j].drawNoZero(canvas, rect);
            }
        }
    }

    public void drawWithLocate(Canvas canvas, Paint paint) {
        Rect rect = new Rect();
        Cell cells[][] = this.getArr();
        int locateX = getX();
        int locateY = getY();
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                rect.left = (j + locateX) * oneSize;
                rect.right = rect.left + oneSize;
                rect.top = (i + locateY) * oneSize;
                rect.bottom = rect.top + oneSize;
                cells[i][j].drawNoZero(canvas, rect, paint);
            }
        }
    }
}
