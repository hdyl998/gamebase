package com.hdyl.xiangqi;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.alibaba.fastjson.JSON;
import com.hdyl.banghujichong.BhjcLogic;
import com.hdyl.banghujichong.QiItem;
import com.hdyl.baselib.utils.log.LogUitls;

/**
 * <p>Created by Administrator on 2018/9/28.<p>
 * <p>佛祖保佑，永无BUG<p>
 */

public class XiangqiLogic {

    public XiangqiLogic() {

    }

    public final static int xCount = 9;
    public final static int yCount = 10;

    public int getxCount() {
        return xCount;
    }


    public int getyCount() {
        return yCount;
    }

    public ChessItem[][] chessItems = new ChessItem[yCount][xCount];


    public void newGame() {
        for (int i = 0; i < yCount; i++) {
            for (int j = 0; j < xCount; j++) {
                chessItems[i][j] = null;
            }
        }
        //黑方
        setChessItem(0, 0, new ChessItem(ChessType.JU, ChessItem.PLAYER_TYPE_BLACK));
        setChessItem(0, 8, new ChessItem(ChessType.JU, ChessItem.PLAYER_TYPE_BLACK));
        setChessItem(0, 1, new ChessItem(ChessType.MA, ChessItem.PLAYER_TYPE_BLACK));
        setChessItem(0, 7, new ChessItem(ChessType.MA, ChessItem.PLAYER_TYPE_BLACK));
        setChessItem(0, 2, new ChessItem(ChessType.XIANG, ChessItem.PLAYER_TYPE_BLACK));
        setChessItem(0, 6, new ChessItem(ChessType.XIANG, ChessItem.PLAYER_TYPE_BLACK));
        setChessItem(0, 3, new ChessItem(ChessType.SHI, ChessItem.PLAYER_TYPE_BLACK));
        setChessItem(0, 5, new ChessItem(ChessType.SHI, ChessItem.PLAYER_TYPE_BLACK));
        setChessItem(0, 4, new ChessItem(ChessType.JIANG, ChessItem.PLAYER_TYPE_BLACK));

        setChessItem(2, 1, new ChessItem(ChessType.PAO, ChessItem.PLAYER_TYPE_BLACK));
        setChessItem(2, 7, new ChessItem(ChessType.PAO, ChessItem.PLAYER_TYPE_BLACK));

        setChessItem(3, 0, new ChessItem(ChessType.BING, ChessItem.PLAYER_TYPE_BLACK));
        setChessItem(3, 2, new ChessItem(ChessType.BING, ChessItem.PLAYER_TYPE_BLACK));
        setChessItem(3, 4, new ChessItem(ChessType.BING, ChessItem.PLAYER_TYPE_BLACK));
        setChessItem(3, 6, new ChessItem(ChessType.BING, ChessItem.PLAYER_TYPE_BLACK));
        setChessItem(3, 8, new ChessItem(ChessType.BING, ChessItem.PLAYER_TYPE_BLACK));

        //红方
        setChessItem(9, 0, new ChessItem(ChessType.JU, ChessItem.PLAYER_TYPE_RED));
        setChessItem(9, 8, new ChessItem(ChessType.JU, ChessItem.PLAYER_TYPE_RED));
        setChessItem(9, 1, new ChessItem(ChessType.MA, ChessItem.PLAYER_TYPE_RED));
        setChessItem(9, 7, new ChessItem(ChessType.MA, ChessItem.PLAYER_TYPE_RED));
        setChessItem(9, 2, new ChessItem(ChessType.XIANG, ChessItem.PLAYER_TYPE_RED));
        setChessItem(9, 6, new ChessItem(ChessType.XIANG, ChessItem.PLAYER_TYPE_RED));
        setChessItem(9, 3, new ChessItem(ChessType.SHI, ChessItem.PLAYER_TYPE_RED));
        setChessItem(9, 5, new ChessItem(ChessType.SHI, ChessItem.PLAYER_TYPE_RED));
        setChessItem(9, 4, new ChessItem(ChessType.JIANG, ChessItem.PLAYER_TYPE_RED));

        setChessItem(7, 1, new ChessItem(ChessType.PAO, ChessItem.PLAYER_TYPE_RED));
        setChessItem(7, 7, new ChessItem(ChessType.PAO, ChessItem.PLAYER_TYPE_RED));

        setChessItem(6, 0, new ChessItem(ChessType.BING, ChessItem.PLAYER_TYPE_RED));
        setChessItem(6, 2, new ChessItem(ChessType.BING, ChessItem.PLAYER_TYPE_RED));
        setChessItem(6, 4, new ChessItem(ChessType.BING, ChessItem.PLAYER_TYPE_RED));
        setChessItem(6, 6, new ChessItem(ChessType.BING, ChessItem.PLAYER_TYPE_RED));
        setChessItem(6, 8, new ChessItem(ChessType.BING, ChessItem.PLAYER_TYPE_RED));


        xiangqiGameEvent.invalidate();
    }

    public void setChessItem(int y, int x, ChessItem item) {
        chessItems[y][x] = item;
    }


    IXiangqiGameEvent xiangqiGameEvent;


    public void setXiangqiGameEvent(IXiangqiGameEvent xiangqiGameEvent) {
        this.xiangqiGameEvent = xiangqiGameEvent;
    }


    public ChessItem[][] getChessItems() {
        return chessItems;
    }

    public ChessItem getChessItem(int x, int y) {
        return chessItems[y][x];
    }


    public void drawBoard(Canvas canvas, int size, int xOffset, int yOffset) {
        RectF rect = new RectF();
        ChessItem[][] qiItems = this.getChessItems();
        for (int i = 0; i < getyCount(); i++) {
            for (int j = 0; j < getxCount(); j++) {
                ChessItem cell = qiItems[i][j];
                if (cell != null) {
                    rect.left = j * size + xOffset;
                    rect.right = rect.left + size;
                    rect.top = i * size + yOffset;
                    rect.bottom = rect.top + size;
                    cell.draw(canvas, rect);
                }
            }
        }
    }

    ChessItem focusChessItem;

    /***
     * 把item放到x,y上,原来的位置
     * @return
     */
    public boolean goNextPositon(ChessItem item, int x, int y) {
        LogUitls.print("from " + item.tempX + "," + item.tempY + " to " + x + "," + y);
        if (item.isGoPositon(chessItems, x, y)) {
            setChessItem(item.tempY, item.tempX, null);
            setChessItem(y, x, item);
            return true;
        }
        return false;
    }


    //一定要选中
    public void clickPosition(int x, int y) {
        //当前点击的item
        ChessItem chessItem = getChessItem(x, y);
        //相同就没必要了
        if (chessItem == focusChessItem) {
            return;
        }
        if (focusChessItem != null) {
            if (chessItem == null || chessItem.isPlayerDifferent(focusChessItem)) {
                boolean isSuccess = goNextPositon(focusChessItem, x, y);
                if (isSuccess) {
                    focusChessItem.setFocus(false);
                    focusChessItem = null;
                    xiangqiGameEvent.invalidate();
                }
            }
            return;
        } else {
            chessItem.toggleFocus();
            focusChessItem = chessItem;
            focusChessItem.tempX = x;
            focusChessItem.tempY = y;
            xiangqiGameEvent.invalidate();
        }
    }
}
