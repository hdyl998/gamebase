package com.hdyl.tetris2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.hdyl.baselib.utils.log.LogUitls;
import com.hdyl.mine.R;
import com.hdyl.tetris2.shape.GameShape;

/**
 * Created by liugd on 2017/9/4.
 */

public class GameTetris2View extends View {

    GameBoard gameBoard;
    Paint paint = new Paint();

    public GameTetris2View(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setAlpha(128);
    }


    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getWidth() == 0) {
            return;
        }
        gameBoard.drawBoard(canvas);
        if (!isMoveOne) {
            initLocation();
        } else {
            curGameShape.setOneSize(oneSize);
        }
        drawDeleteIcon(canvas);
        drawShadow(canvas);
        gameBoard.drawGameShapes(canvas);


    }

    private void drawShadow(Canvas canvas) {
        if (curGameShape != null) {
            if (gameBoard.canLocation(curGameShape)) {
                curGameShape.drawWithLocate(canvas, paint);
            }
        }
    }

    private Bitmap bitmap;

    Rect rectDelete = new Rect();

    private void drawDeleteIcon(Canvas canvas) {
        canvas.drawBitmap(bitmap, null, rectDelete, null);
    }


    int oneSize;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        oneSize = getWidth() / GameBoard.WIDTH;

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.delete_press);
        rectDelete.top = oneSize * (GameBoard.HEIGHT + 4);
        rectDelete.left = (getWidth() - bitmap.getWidth()) / 2;
        rectDelete.right = rectDelete.left + bitmap.getWidth();
        rectDelete.bottom = rectDelete.top + bitmap.getHeight();
        gameBoard.setOneSize(oneSize);
        gameBoard.newGame();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    boolean isMoveOne = false;
    int startX, startY;


    public void initLocation() {
        int count = 0;
        for (GameShape gameShape : gameBoard.gameShapes) {
            if (gameShape != null) {
                gameShape.locateX = getWidth() / 3 * count + getWidth() / 12;
                gameShape.locateY = (GameBoard.HEIGHT + 1) * oneSize;
                gameShape.setOneSize(getWidth() / 12);
            }
            count++;
        }
    }


    GameShape curGameShape;

    int moveIndex;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if ((moveIndex = gameBoard.contains(x, y)) != -1) {
                    isMoveOne = true;
                    startX = x;
                    startY = y;
                    curGameShape = gameBoard.getGameShape(moveIndex);
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (isMoveOne) {
                    int dx = x - startX;
                    int dy = y - startY;
                    curGameShape.move(dx, dy);
                    startX = x;
                    startY = y;
                    invalidate();
                }
                break;
            default:
                if (moveIndex != -1) {
                    //判定是否有交集
                    if (Rect.intersects(rectDelete, curGameShape.getLocateRect())) {
                        gameBoard.deleteGameShape(moveIndex);
                    } else {
                        gameBoard.addShapeOnBoard(moveIndex);
                    }
                }
                moveIndex = -1;
                isMoveOne = false;
                curGameShape = null;
                invalidate();
                break;

        }
        return true;
    }
}
