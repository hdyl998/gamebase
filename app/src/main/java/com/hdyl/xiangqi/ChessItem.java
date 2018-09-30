package com.hdyl.xiangqi;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.hdyl.baselib.utils.log.LogUitls;
import com.hdyl.xiangqi.goway.GoWayManager;

/**
 * <p>Created by Administrator on 2018/9/28.<p>
 * <p>佛祖保佑，永无BUG<p>
 */

public class ChessItem {
    public final static int PLAYER_TYPE_RED = 0;
    public final static int PLAYER_TYPE_BLACK = 1;


    public int playerType = PLAYER_TYPE_RED;


    public int chessType = ChessType.JU;

    public int tempX, tempY;

    public boolean isFocus = false;

    public boolean isFocus() {
        return isFocus;
    }


    public void setFocus(boolean focus) {
        isFocus = focus;
    }

    public void toggleFocus() {
        isFocus = !isFocus;
    }


    public ChessItem() {
    }

    public boolean isPlayerDifferent(ChessItem chessItem) {
        return chessItem.playerType != playerType;
    }

    /***
     * 是否向上进攻
     * @return
     */
    public boolean isAttackTowardUp() {
        return XiangqiConsts.isRedBottom && playerType == PLAYER_TYPE_RED || !XiangqiConsts.isRedBottom && playerType == PLAYER_TYPE_BLACK;
    }


    public ChessItem(int chessType, int playerType) {
        this.chessType = chessType;
        this.playerType = playerType;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public int getChessType() {
        return chessType;
    }

    public int getPlayerType() {
        return playerType;
    }


    public void setChessType(int chessType) {
        this.chessType = chessType;
    }

    public void setPlayerType(int playerType) {
        this.playerType = playerType;
    }


    public Bitmap getDrawBitmap() {
        return XiangqiResourcesManager.getInstance().getBitmapInChess(playerType * 7 + chessType);
    }


    /***
     * 是否可以去到指定位置
     * @param chessItems
     * @param toX
     * @param toY
     * @return
     */
    public boolean isGoPositon(ChessItem[][] chessItems, int toX, int toY) {
        return GoWayManager.getInstance().getGoWay(chessType).canGo(chessItems, tempX, tempY, toX, toY);
    }

    /***
     * 绘制
     * @param canvas
     * @param rect
     */
    public void draw(Canvas canvas, RectF rect) {
        canvas.drawBitmap(getDrawBitmap(), null, rect, null);
        if (isFocus) {
            canvas.drawBitmap(XiangqiResourcesManager.getInstance().getBitmapFocus(), null, rect, null);
        }
    }
}
