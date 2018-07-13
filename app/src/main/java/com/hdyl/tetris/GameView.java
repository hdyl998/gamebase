package com.hdyl.tetris;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.hdyl.baselib.utils.log.LogUitls;
import com.hdyl.tetris.common.GameConfig;
import com.hdyl.tetris.shape.TetrisShape;
import com.hdyl.tetris2.GameColor;

/**
 * 游戏视图，根据画板，绘制游戏
 * Created by liugd on 2017/5/8.
 */

public class GameView extends View {
    GameBoard gameBoard = new GameBoard();
    float size;
    RectF rectF = new RectF();


    public GameBoard getGameBoard() {
        return gameBoard;
    }

    LineDispearAnim lineDispearAnim;
    DownAnim downAnim;

    public GameView(Context context) {
        super(context);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        gameBoard.setOnGameEvent((GameBoard.OnGameEvent) context);
        lineDispearAnim = gameBoard.lineDispearAnim;
        downAnim = gameBoard.downAnim;
    }

    Bitmap bitmapBg = null;

    private void checkBg() {
        LogUitls.print("tag", size);
        bitmapBg = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.RGB_565);
        Canvas canvasBitmap = new Canvas(bitmapBg);
        Bitmap bitmap = GameColor.getBitmapBlack();
        for (int i = 0; i < GameBoard.yCount; i++) {
            for (int j = 0; j < GameBoard.xCount; j++) {
                rectF.left = j * size;
                rectF.right = rectF.left + size;
                rectF.top = i * size;
                rectF.bottom = rectF.top + size;
                canvasBitmap.drawBitmap(bitmap, null, rectF, null);
            }
        }
    }

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    @Override
    protected void onDraw(Canvas canvas) {
        if (getWidth() == 0) {
            return;
        }

        canvas.drawBitmap(bitmapBg, 0, 0, null);

        if (downAnim.isAnim()) {
            downAnim.draw(canvas, size);
            downAnim.ticker(30);
            //画阴影
            if (downAnim.isDitenece1()) {
                drawShadow(canvas, downAnim.getShape());
            }
            invalidate();
        } else if (lineDispearAnim.isAnim()) {
            lineDispearAnim.draw(canvas, size);
            lineDispearAnim.ticker(30);
            invalidate();
        } else {
            gameBoard.drawBoard(canvas, size);
            gameBoard.drawCurTetris(canvas, size);
            if (gameBoard.isGamePause()) {
                paint.setAlpha(256);
                paint.setTextSize(dip2px(40));
                paint.setColor(Color.WHITE);
                paint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText("游戏暂停", getWidth() / 2, getHeight() / 2, paint);
            } else {
                drawShadow(canvas, gameBoard.getCurShape());
            }
        }
    }

    private void drawShadow(Canvas canvas, TetrisShape shape) {
        //画阴影
        if (GameConfig.getInstance().isShadow()) {
            paint.setAlpha(100);
            gameBoard.drawCurTetrisShadow(canvas, shape, size, paint);
        }
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        float hsize = 1f * h / GameBoard.yCount;
        float wsize = 1f * w / GameBoard.xCount;
        size = Math.min(hsize, wsize);
        checkBg();
        sensibility = w / 13;//灵敏度
        //新游戏
//        gameBoard.newGame();
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int size = MeasureSpec.getSize(widthMeasureSpec);
////        int size2= (int) (this.size*GameBoard.xCount);
//        setMeasuredDimension(size, size * 2);
//    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dip2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    float x, y;
    float tempx, tempy;
    /***
     * 灵敏度
     */
    int sensibility;

//
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent event) {
//        return onTouchEvent(event);
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                tempx = x = event.getX();
                tempy = y = event.getY();

                return true;
            case MotionEvent.ACTION_MOVE: {
                float upx = event.getX();
                float upy = event.getY();
                float senX = Math.abs(upx - tempx);
                float senY = Math.abs(upy - tempy);
                if (senX > sensibility || senY > sensibility) {
                    if (senX > senY) {
                        gameBoard.move(tempx > upx ? GameBoard.DIRECTION_LEFT : GameBoard.DIRECTION_RIGHT);
                    } else {
//                        gameBoard.move(tempy > upy ? GameBoard.DIRECTION_ROTATE : GameBoard.DIRECTION_FAST_DOWN);
                    }
                    tempx = upx;
                    tempy = upy;
                }
            }

            break;
            default:
                if (gameBoard.isGamePause()) {
                    gameBoard.exchangePausePlayingGameState();
                    return true;
                }
//            case MotionEvent.ACTION_UP:
                float upx = event.getX();
                float upy = event.getY();
                float senX = Math.abs(upx - x);
                float senY = Math.abs(upy - y);
                if (senX > sensibility || senY > sensibility) {
                    if (senX > senY) {
//                        gameBoard.move(x > upx ? GameBoard.DIRECTION_LEFT : GameBoard.DIRECTION_RIGHT);
                    } else {
                        gameBoard.move(y > upy ? GameBoard.DIRECTION_DOWN : GameBoard.DIRECTION_FAST_DOWN);
                    }
                } else {//单击
                    if (Math.abs(tempx - upx) < sensibility / 2 && Math.abs(tempy - upy) < sensibility / 2) {
                        gameBoard.move(GameBoard.DIRECTION_ROTATE);
                    }
                }
                break;
        }
        return false;
    }

}
