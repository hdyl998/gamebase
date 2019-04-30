package com.hdyl.tetris;

import android.graphics.Canvas;
import android.graphics.RectF;

import com.hdyl.baselib.utils.log.LogUitls;

import java.util.List;

/**
 * Created by Administrator on 2017/9/9.
 */

public class LineDispearAnim extends BaseAnim {

    List<Integer> animLines;

    public void addAnim(Cell cellArrs[][], List<Integer> animLines) {
        this.animLines = animLines;
        copy(cellArrs);
        startDraw = true;
    }

    boolean startDraw = false;

    @Override
    public int getDuring() {
        return 300;
    }

    private static final String TAG = "LineDispearAnim";


    @Override
    public void draw(Canvas canvas, float size) {
        if (startDraw) {
            startDraw = false;
            resetEndTime();
        }
        super.draw(canvas, size);
    }

    @Override
    public Cell[][] getCellArrs() {
        boolean isPositive = true;
        float percent = getPercent();

        LogUitls.print(TAG, "percent" + percent);
        for (Integer index : animLines) {
            setPercentValue(cellArrs[index], percent, isPositive);
            isPositive = !isPositive;
        }
        return cellArrs;
    }

    private void setPercentValue(Cell cells[], float percent, boolean isPositive) {
        int count = (int) (cells.length * percent);
        for (int i = 0; i < count; i++) {
            if (isPositive) {
                cells[i].setFull(false);
            } else {
                cells[cells.length - i - 1].setFull(false);
            }
        }
    }

    @Override
    public boolean isAnim() {
        if (startDraw) {
            return true;
        }
        return super.isAnim();
    }
}
