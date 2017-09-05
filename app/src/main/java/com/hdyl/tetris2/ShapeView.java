package com.hdyl.tetris2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.hdyl.tetris2.shape.Cell;
import com.hdyl.tetris2.shape.GameShape;

/**
 * Created by liugd on 2017/9/5.
 */

public class ShapeView extends View {

    GameShape gameShape;

    public void setGameShape(GameShape gameShape) {
        this.gameShape = gameShape;
        invalidate();
    }

    public ShapeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    Rect rectF = new Rect();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getWidth() == 0) {
            return;
        }
        int x = 0, y = 0;
        for (Cell[] cells : gameShape.getArr()) {
            x = 0;
            for (Cell cell : cells) {

                rectF.left=0;

                cell.draw(canvas, rectF);
                x++;
            }
            y++;
        }


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }
}
