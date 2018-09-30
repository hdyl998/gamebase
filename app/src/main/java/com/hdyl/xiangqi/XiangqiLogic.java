package com.hdyl.xiangqi;

import android.graphics.Canvas;
import android.graphics.RectF;

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
        resetReference();
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


    public void drawBoard(Canvas canvas, int size, int xOffset, int yOffset, int qiSpace) {
        RectF rect = new RectF();
        ChessItem[][] qiItems = this.getChessItems();
        for (int i = 0; i < getyCount(); i++) {
            for (int j = 0; j < getxCount(); j++) {
                ChessItem cell = qiItems[i][j];
                if (cell != null) {
                    rect.left = j * size + xOffset + qiSpace/2;
                    rect.right = rect.left + size - qiSpace;
                    rect.top = i * size + yOffset + qiSpace/2;
                    rect.bottom = rect.top + size - qiSpace;
                    cell.draw(canvas, rect);
                }
            }
        }
    }


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

    private int lastFocusX;
    private int lastFocusY;
    private ChessItem curFocusItem;

    /**
     * 重置引用
     */
    private void resetReference() {
        lastFocusX = -1;
        lastFocusY = -1;
        curFocusItem = null;
    }


    //一定要选中
    public void clickPosition(int x, int y) {
        //当前点击的item
        ChessItem chessItem = getChessItem(x, y);
        //相同就不改变原状态
        if (chessItem == curFocusItem) {
            return;
        }
        if (curFocusItem != null) {
            //行进,或者去吃子
            if (chessItem == null || chessItem.isPlayerDifferent(curFocusItem)) {
                boolean isSuccess = goNextPositon(curFocusItem, x, y);
                LogUitls.print("isSuccess" + isSuccess + x + " " + y);
                curFocusItem.setFocus(false);
                curFocusItem = null;
                xiangqiGameEvent.invalidate();
                return;
            } else {//又点了自已方的棋
                curFocusItem.setFocus(false);
            }
        }
        curFocusItem = chessItem;
        curFocusItem.tempX = x;
        curFocusItem.tempY = y;
        curFocusItem.setFocus(true);
        xiangqiGameEvent.invalidate();
    }
}
