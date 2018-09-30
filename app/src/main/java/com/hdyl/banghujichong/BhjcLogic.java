package com.hdyl.banghujichong;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;

import com.hdyl.baselib.utils.log.LogUitls;
import com.hdyl.xiangqi.ChessItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <p>Created by liugd on 2018/4/4.<p>
 * <p>佛祖保佑，永无BUG<p>
 */

public class BhjcLogic {

    int xCount;
    int yCount;
    public QiItem[][] qiItems;
    int gameState;

    public boolean isOnePlayTurn = true;


    Point pointLast;//上一个
    Point pointCur;//当前个

    /***
     * 交换轮次
     * @return
     */
    public boolean togglePlayTurn() {
        return isOnePlayTurn = !isOnePlayTurn;
    }

    public boolean isOnePlayTurn() {
        return isOnePlayTurn;
    }

    public int getxCount() {
        return xCount;
    }

    public int getyCount() {
        return yCount;
    }

    public final static int GAME_STATE_GAMEOVER = 2;
    public final static int GAME_STATE_PAUSE = 1;
    public final static int GAME_STATE_PLAYING = 0;


    public boolean isGameOver() {
        return gameState == GAME_STATE_GAMEOVER;
    }

    public void setGameState(int gameState) {
        this.gameState = gameState;
    }


    public boolean isGamePause() {
        return gameState == GAME_STATE_PAUSE;
    }

    public boolean isGamePlaying() {
        return gameState == GAME_STATE_PLAYING;
    }


    public BhjcLogic() {
        this(4, 6);
    }


    public BhjcLogic(int width, int height) {
        xCount = width;
        yCount = height;
    }


    public void newGame() {
        createQiItems();
        shuffle();
        setGameState(GAME_STATE_PLAYING);
        resetReference();
        onGameEvent.invalidate();
    }

    /***
     *打乱
     */
    public void shuffle() {
        Random random = new Random();
        for (int i = 0; i < yCount; i++) {
            for (int j = 0; j < xCount; j++) {
                int tempi = random.nextInt(yCount);
                int tempj = random.nextInt(xCount);

                int tempi2 = random.nextInt(yCount);
                int tempj2 = random.nextInt(xCount);

                swap(tempi, tempj, tempi2, tempj2);
            }
        }
    }

    /***
     *  交换
     */
    public void swap(int tempi, int tempj, int tempi2, int tempj2) {
        QiItem qiItem = qiItems[tempi][tempj];
        qiItems[tempi][tempj] = qiItems[tempi2][tempj2];
        qiItems[tempi2][tempj2] = qiItem;
    }


    private void createQiItems() {
        int total = xCount * yCount;
        if (total < 8) {
            throw new RuntimeException("xCount*yCount<8");
        }
        qiItems = new QiItem[yCount][xCount];

        int len = total / 8;
        List<QiItem> list = new ArrayList<>(len * 8);

        for (int i = 0; i < len; i++) {
            list.add(new QiItem(QiItem.ITEM_TYPE_BANG, QiItem.USER_TYPE_ONE));
            list.add(new QiItem(QiItem.ITEM_TYPE_HU, QiItem.USER_TYPE_ONE));
            list.add(new QiItem(QiItem.ITEM_TYPE_JI, QiItem.USER_TYPE_ONE));
            list.add(new QiItem(QiItem.ITEM_TYPE_CHONG, QiItem.USER_TYPE_ONE));

            list.add(new QiItem(QiItem.ITEM_TYPE_BANG, QiItem.USER_TYPE_TWO));
            list.add(new QiItem(QiItem.ITEM_TYPE_HU, QiItem.USER_TYPE_TWO));
            list.add(new QiItem(QiItem.ITEM_TYPE_JI, QiItem.USER_TYPE_TWO));
            list.add(new QiItem(QiItem.ITEM_TYPE_CHONG, QiItem.USER_TYPE_TWO));
        }
        int count = 0;
        tag:
        for (int i = 0; i < yCount; i++) {
            for (int j = 0; j < xCount; j++) {
                qiItems[i][j] = list.get(count);
                count++;
                if (count >= list.size()) {
                    break tag;
                }
            }
        }
    }

    public QiItem[][] getQiItems() {
        return qiItems;
    }


    public void drawBoard(Canvas canvas, float size, Paint paint, int divider, int xOffset, int yOffset) {
        RectF rect = new RectF();
        QiItem[][] qiItems = this.getQiItems();
        for (int i = 0; i < getyCount(); i++) {
            for (int j = 0; j < getxCount(); j++) {
                QiItem cell = qiItems[i][j];
                if (cell != null) {
                    rect.left = j * size + divider / 2 + xOffset;
                    rect.right = rect.left + size - divider;
                    rect.top = i * size + divider / 2 + yOffset;
                    rect.bottom = rect.top + size - divider;
                    cell.draw(canvas, rect, paint);
                }
            }
        }

        if (pointLast != null) {
            paint.setColor(Color.YELLOW);
            paint.setStyle(Paint.Style.STROKE);
            rect.left = pointLast.x * size + divider / 2 + xOffset;
            rect.right = rect.left + size - divider;
            rect.top = pointLast.y * size + divider / 2 + yOffset;
            rect.bottom = rect.top + size - divider;
            canvas.drawRoundRect(rect, 10, 10, paint);
        }
        if (pointCur != null) {
            paint.setColor(Color.YELLOW);
            paint.setStyle(Paint.Style.STROKE);
            rect.left = pointCur.x * size + divider / 2 + xOffset;
            rect.right = rect.left + size - divider;
            rect.top = pointCur.y * size + divider / 2 + yOffset;
            rect.bottom = rect.top + size - divider;
            canvas.drawRoundRect(rect, 10, 10, paint);
        }
    }


    IOnGameEvent onGameEvent;


    public void setOnGameEvent(IOnGameEvent onGameEvent) {
        this.onGameEvent = onGameEvent;
    }


    /**
     * 重置引用
     */
    private void resetReference() {
        pointLast = new Point(-1, -1);
        pointCur = new Point(-1, -1);
    }

    public QiItem getQiItem(int x, int y) {
        return getQiItems()[y][x];
    }

    public QiItem getLastFocusItem(Point point) {
        if (point == null) {
            return null;
        }
        return getQiItem(point.y, point.x);
    }


    public void clickPosition(int x, int y) {
        //当前点击的item
        QiItem chessItem = getQiItem(x, y);
        if (!chessItem.isShow()) {
            chessItem.toggleShow();
            onGameEvent.invalidate();
            return;
        }
        QiItem curFocusItem = getLastFocusItem(pointCur);
        //相同就不改变原状态
        if (chessItem == curFocusItem) {
            return;
        }
        if (curFocusItem != null) {
            //行进,或者去吃子
            if (chessItem == null || curFocusItem.eat(chessItem)) {
                boolean isSuccess = goNextPositon(curFocusItem, x, y);
                LogUitls.print("isSuccess" + isSuccess + x + " " + y);
                curFocusItem.setFocus(false);
                curFocusItem = null;
                onGameEvent.invalidate();
                return;
            } else {//又点了自已方的棋
                curFocusItem.setFocus(false);
            }
        }
        curFocusX = x;
        curFocusY = y;
        curFocusItem.setFocus(true);
        onGameEvent.invalidate();
    }

    public void setChessItem(int y, int x, QiItem item) {
        qiItems[y][x] = item;
    }

    private boolean goNextPositon(QiItem item, int toX, int toY) {
        LogUitls.print("from " + curFocusX + "," + curFocusY + " to " + toX + "," + toY);
        boolean isOneStep = Math.abs(toX - curFocusX) + Math.abs(toY - curFocusY) == 1;
        if (isOneStep) {//每次一步
            setChessItem(curFocusY, curFocusX, null);
            setChessItem(toY, toX, item);
            return true;
        }
        return false;
    }

    public interface IOnGameEvent {
        void onGameOver();//游戏结束

        void invalidate();//绘制当前面版

        void onNewGame();

        void onGamePause();

        void onGamePauseResume();
    }

}
