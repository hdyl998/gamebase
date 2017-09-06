package com.hdyl.tetris;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.hdyl.tetris.shape.CellArray;
import com.hdyl.tetris.shape.TetrisShape;

/**
 * Created by liugd on 2017/5/9.
 */

public class NextShapeView extends View {


    TetrisShape tetrisShape;

    float size = 0;
    RectF rectF = new RectF();


    public void setTetrisShape(TetrisShape tetrisShape) {
        this.tetrisShape = tetrisShape;
        invalidate();
    }

    public TetrisShape getTetrisShape() {
        return tetrisShape;
    }

    public NextShapeView(Context context) {
        super(context);
    }

    public NextShapeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (getWidth() == 0 || tetrisShape == null) {
            return;
        }
        this.getTetrisShape().draw(canvas, size);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        size = w / 4;
//        setTetrisShape(TetrisShapeFactory.createRandomShape());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int size = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(size, size);
    }
}
