package com.hdyl.banghujichong;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

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
                    rect.left = j * size + divider/2 + xOffset;
                    rect.right = rect.left + size - divider;
                    rect.top = i * size + divider/2 + yOffset;
                    rect.bottom = rect.top + size - divider;
                    cell.draw(canvas, rect, paint);
                }
            }
        }
    }


    IOnGameEvent onGameEvent;


    public void setOnGameEvent(IOnGameEvent onGameEvent) {
        this.onGameEvent = onGameEvent;
    }

    public interface IOnGameEvent {
        void onGameOver();//游戏结束

        void invalidate();//绘制当前面版

        void onNewGame();

        void onGamePause();

        void onGamePauseResume();
    }

}
