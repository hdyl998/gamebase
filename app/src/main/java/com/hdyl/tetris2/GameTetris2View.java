package com.hdyl.tetris2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by liugd on 2017/9/4.
 */

public class GameTetris2View extends View {

    GameBoard gameBoard;

    public GameTetris2View(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    RectF rectF = new RectF();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getWidth() == 0) {
            return;
        }
        for (int i = 0; i < GameBoard.HGIGHT; i++) {
            for (int j = 0; j < GameBoard.WIDTH; j++) {
                rectF.left = j * oneSize;
                rectF.right = rectF.left + oneSize;
                rectF.top = i * oneSize;
                rectF.bottom = rectF.top + oneSize;
                this.gameBoard.getMaps()[i][j].draw(canvas, rectF);
            }
        }
    }

    int oneSize;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        oneSize = getWidth() / GameBoard.WIDTH;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
