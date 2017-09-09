package com.hdyl.tetris;

import android.graphics.Canvas;
import android.graphics.RectF;

import java.util.List;

/**
 * Created by Administrator on 2017/9/9.
 */

public class LineDispearAnim extends BaseAnim{


    public void addAnim(Cell cellArrs[][], List<Integer>animLines){
        this.costTime=0;
        this.animLines=animLines;
        copy(cellArrs);
        setDuring(500);
    }

    public Cell[][]getCellArrs(){
        boolean isPositive=true;
        float percent=getPercent();
        for(Integer index:animLines){
            setPercentValue(cellArrs[index],percent,isPositive);
            isPositive=!isPositive;
        }
       return cellArrs;
    }

    private void setPercentValue(Cell cells[],float percent,boolean isPositive){
        int count= (int) (cells.length*percent);
        for(int i=0;i<count;i++){
            if(isPositive){
                cells[i].setFull(false);
            }
            else {
                cells[cells.length-i-1].setFull(false);
            }
        }
    }
}
