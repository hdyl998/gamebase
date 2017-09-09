package com.hdyl.tetris2;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;


import com.hdyl.tetris2.shape.Cell;

import java.util.List;

/**
 * Created by Administrator on 2017/9/9.
 */

public class LineDispearAnim {

    public int during=500;
    public int costTime=during;

    public Cell cellArrs[][];

    List<Integer> animXLines;

    List<Integer> animYLines;
    public void setDuring(int during) {
        this.during = during;
    }

    public void init(){
        cellArrs = new Cell[GameBoard.HEIGHT][GameBoard.WIDTH];
        for (int i = 0; i <GameBoard.HEIGHT; i++) {
            for (int j = 0; j < GameBoard.WIDTH; j++) {
                cellArrs[i][j] = new Cell();
            }
        }
    }

    public LineDispearAnim(){
        init();
    }



    protected void copy(Cell cellArrs[][]){
        for (int i = 0; i < cellArrs.length; i++) {
            for (int j = 0; j < cellArrs[0].length; j++) {
                this.cellArrs[i][j].setValue(cellArrs[i][j]);
            }
        }
    }

    public void draw(Canvas canvas, int size) {
        Rect rect = new Rect();
        Cell[][] cellArrs = this.getCellArrs();
        for (int i = 0; i < cellArrs.length; i++) {
            for (int j = 0; j < cellArrs[0].length; j++) {
                Cell cell = cellArrs[i][j];
                rect.left = j * size;
                rect.right = rect.left + size;
                rect.top = i * size;
                rect.bottom = rect.top + size;
                cell.draw(canvas, rect);
            }
        }
    }



    public void clearAnim(){
        costTime=during;
    }



    public boolean isEnd(){
        if(costTime>=during){
            return true;
        }
        return false;
    }
    public void addAnim(Cell cellArrs[][], List<Integer>animXLines, List<Integer>animYLines){
        this.costTime=0;
        this.animXLines=animXLines;
        this.animYLines=animYLines;
        copy(cellArrs);
        setDuring(400);
    }

    public Cell[][]getCellArrs(){
        float percent=getPercent();
        for(Integer index:animXLines){
            int count= (int) (cellArrs.length*percent);
            for(int i=0;i<count;i++){
                cellArrs[i][index].reset();
            }
        }
        for(Integer index:animYLines){
            int count= (int) (cellArrs[index].length*percent);
            for(int i=0;i<count;i++){
                cellArrs[index][i].reset();
            }
        }

       return cellArrs;
    }

    public float getPercent(){
        if(isEnd()){
            return 1f;
        }
        return 1f*costTime/during;
    }

    //加时间
    public void ticker(int costTime) {
        this.costTime += costTime;
    }

    public boolean isAnim(){
        if(isEnd()){
            return false;
        }
        return true;
    }
}
