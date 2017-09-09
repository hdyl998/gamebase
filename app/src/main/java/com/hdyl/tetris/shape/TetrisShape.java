package com.hdyl.tetris.shape;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import com.hdyl.tetris.PositionCell;
import com.hdyl.tetris2.GameColor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

/**
 * 所有的形状抽象类
 * Created by liugd on 2017/5/5.
 */

public abstract class TetrisShape {
    public int resIndex;
    public int curentType = 0;//当前滚动到第几个来了

    public CellArray[] cellArrays;

    public TetrisShape() {
        //创建cellArrays
        createCellArrays();
        setResIndex(GameColor.getRandomResIndex());
        curentType = new Random().nextInt(cellArrays.length);
    }

    private void createCellArrays() {
        String arrStrings[] = getCellsDatas();
        cellArrays = new CellArray[arrStrings.length];
        int count = 0;
        for (String string : arrStrings) {
            cellArrays[count++] = new CellArray(string);
        }
    }


    private void setCellArraysColor() {
        for (CellArray array : cellArrays) {
            array.setCellsResIndex(resIndex);
        }
    }

    /***
     * 取下一个旋转的值，但并不真的旋转
     *
     * @param shunshi
     * @return
     */
    public CellArray peekRotate(boolean shunshi) {
        int temp = curentType;
        if (shunshi) {
            temp++;
        } else {
            temp += cellArrays.length - 1;
        }
        temp = temp % cellArrays.length;
        return cellArrays[temp];
    }

    /***
     * 旋转
     *
     * @return
     */
    public CellArray rotate(boolean shunshi) {
        //只有一种图型的直接返回去
        if (cellArrays.length == 1) {
            return cellArrays[0];
        }
        if (shunshi) {
            curentType++;
        } else {
            curentType += cellArrays.length - 1;
        }

        curentType = curentType % cellArrays.length;
        return cellArrays[curentType];
    }


    protected abstract String[] getCellsDatas();

    public CellArray getCellArray() {
        return cellArrays[curentType];
    }


    public void draw(Canvas canvas, float size, int xOffSet, int yOffset) {
        CellArray array = this.getCellArray();
        RectF rectF = new RectF();
        for (PositionCell cell : array.cells) {
            rectF.left = (xOffSet + cell.getX()) * size;
            rectF.right = rectF.left + size;
            rectF.top = (yOffset + cell.getY()) * size;
            rectF.bottom = rectF.top + size;
            cell.draw(canvas, rectF);
        }
    }

    public void drawPx(Canvas canvas, float size, int xOffSet, int yOffset) {
        CellArray array = this.getCellArray();
        RectF rectF = new RectF();
        for (PositionCell cell : array.cells) {
            rectF.left =  cell.getX() * size+xOffSet;
            rectF.right = rectF.left + size;
            rectF.top = yOffset + cell.getY() * size;
            rectF.bottom = rectF.top + size;
            cell.draw(canvas, rectF);
        }
    }

    public void draw(Canvas canvas, float size) {
        draw(canvas, size, 0, 0);
    }

    /***
     * 长度大于1的才能旋转
     *
     * @return
     */
    public boolean canRotate() {
        return cellArrays.length > 1;
    }

    /***
     * 获得图形的底部方块的集合，这里做了优化，不必取所有的方块
     * 比如I 图型只取底部部的一个方块，O取底部两个，
     *
     * @return
     */
    public HashMap<Integer, Integer> getAllBottomData() {
        HashMap<Integer, Integer> array = new HashMap<>();
        for (PositionCell cell : getCellArray().getCells()) {
            Integer integer = array.get(cell.getX());
            if (integer == null || integer < cell.getY()) {
                array.put(cell.getX(), cell.getY());
            }
        }
        return array;
    }

    /***
     * 锋获得取小的Y Data
     *
     * @return
     */
    public int getMinYData() {
        int y = Integer.MAX_VALUE;
        for (PositionCell cell : getCellArray().getCells()) {
            if (y > cell.getY()) {
                y = cell.getY();
            }
        }
        return y;
    }

    public void setResIndex(int resIndex) {
        this.resIndex = resIndex;
        setCellArraysColor();
    }

    public void setCurentType(int curentType) {
        this.curentType = curentType;
    }

    //    public void draw(Canvas canvas){
//        canvas.drawR
//    }
    @Override
    public String toString() {
        return "TetrisShape{" +
                "resIndex=" + resIndex +
                ", curentType=" + curentType +
                ", cellArrays=" + Arrays.toString(cellArrays) +
                '}';
    }

    public final static class TetrisShapeSaveBean {
        public Class<? extends TetrisShape> clazz;
        public int resIndex;
        public int curentType;

        public TetrisShapeSaveBean() {

        }

        public TetrisShapeSaveBean(TetrisShape shape) {
            this.clazz = shape.getClass();
            this.resIndex = shape.resIndex;
            this.curentType = shape.curentType;
        }

        public TetrisShape createTetrisShape() {
            Log.e("aa", this.clazz.getSimpleName());
            try {
                TetrisShape shape = this.clazz.newInstance();
                shape.setResIndex(this.resIndex);
                shape.setCurentType(this.curentType);
                return shape;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
