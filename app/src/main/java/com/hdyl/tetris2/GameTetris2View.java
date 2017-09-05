package com.hdyl.tetris2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.hdyl.baselib.utils.ToastUtils;
import com.hdyl.tetris2.shape.Cell;
import com.hdyl.tetris2.shape.GameShape;

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

    Rect rect = new Rect();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getWidth() == 0) {
            return;
        }
        for (int i = 0; i < GameBoard.HGIGHT; i++) {
            for (int j = 0; j < GameBoard.WIDTH; j++) {
                rect.left = j * oneSize;
                rect.right = rect.left + oneSize;
                rect.top = i * oneSize;
                rect.bottom = rect.top + oneSize;
                this.gameBoard.getMaps()[i][j].draw(canvas, rect);
            }
        }

        GameShape gameShape = this.gameBoard.getGameShape();
        Cell cells[][] = gameShape.getArr();
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {

                if (isMoveOne) {
                    rect.left = realX;
                    rect.top = realY;
                    rect.right = realX;
                    rect.bottom = realY;
                } else {
                    rect.left = 0;
                    rect.top = 0;
                    rect.right = 0;
                    rect.bottom = 0;
                }

                rect.left += j * oneSize;
                rect.right += rect.left + oneSize;
                rect.top += (i + GameBoard.HGIGHT + 1) * oneSize;
                rect.bottom += rect.top + oneSize;
                cells[i][j].draw(canvas, rect);
            }
        }

    }

    private boolean isMoveShape(GameShape gameShape, int x, int y) {
        Rect rect = new Rect();
        rect.left = 0;
        rect.top = (GameBoard.HGIGHT + 1) * oneSize;
        rect.right = gameShape.getArr()[0].length * oneSize;
        rect.bottom = (GameBoard.HGIGHT + 1 + gameShape.getArr().length) * oneSize;
        return rect.contains(x, y);
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

    boolean isMoveOne = false;
    int startX, startY;

    int mStartX, mStartY;

    int differX, differY;

    int realX, realY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (isMoveShape(this.gameBoard.getGameShape(), x, y)) {
                    isMoveOne = true;
                    mStartX = startX = x;
                    mStartY = startY = y;
                    differX = (int) (x - event.getRawX());
                    differY = (int) (x - event.getRawY());
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (isMoveOne) {
                    int dx = x - startX;
                    int dy = y - startY;

                    realX = mStartX + differX + dx;
                    realY = mStartY + differY + dy;

                    invalidate();

                    startX = x;
                    startY = y;
                }
                break;
            default:
                isMoveOne = false;
                break;

        }
        return super.onTouchEvent(event);
    }
}
