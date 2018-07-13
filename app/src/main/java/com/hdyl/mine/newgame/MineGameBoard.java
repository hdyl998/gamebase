package com.hdyl.mine.newgame;

import com.hdyl.mine.game.MineSetting;
import com.hdyl.mine.newgame.level.MineLevel;
import com.hdyl.tetris.GameBoard;

import java.util.Random;

/**
 * <p>Created by liugd on 2018/4/4.<p>
 * <p>佛祖保佑，永无BUG<p>
 */

public class MineGameBoard {

    private MineLevel mineLevel = MineSetting.getInstance().getMineLevel();


    private int WIDTH = mineLevel.getWidth();
    private int HEIGHT = mineLevel.getHeight();
    private int mineNum = mineLevel.getMineNum();


    Cell cellArrs[][];
    int gameState = GameStateConsts.STATE_NO_START;

    int restMine;
    boolean isFirstClick;


    public MineGameBoard() {
        init();
    }

    public void newGame() {
        isFirstClick = true;
        restMine = mineNum;
//        pointsChecked.clear();// 检查过的
//        rthis.mineNum

        for (int i = 0; i < GameBoard.yCount; i++) {
            for (int j = 0; j < GameBoard.xCount; j++) {
                cellArrs[i][j].clear();
            }
        }
//        curScore = 0;
//        xiaoLine = 0;
//        hashMapCounts.clear();
        gameState = GameBoard.GAME_STATE_PLAYING;

    }


    private void init() {
        cellArrs = new Cell[WIDTH][HEIGHT];
        for (int i = 0; i < GameBoard.yCount; i++) {
            for (int j = 0; j < GameBoard.xCount; j++) {
                cellArrs[i][j] = new Cell();
            }
        }
    }

    private boolean isIn(int i, int j)// 检查是否是出界了
    {
        if (i > -1 && i < WIDTH && j > -1 && j < HEIGHT)
            return true;
        return false;
    }

    /**
     * 创建地图根据第一次按下去生成地图
     *
     * @param row
     * @param col
     */
    public void createMap(int row, int col) {
        /**
         * 以按下去为中心的9格子全黑金
         */
        for (int x = row - 1; x < row + 2; x++)
            for (int y = col - 1; y < col + 2; y++) {
                if (isIn(x, y)) {
                    cellArrs[x][y].setValue(Cell.VALUE_NONE);
                }
            }

        // 交换
        Random random1 = new Random();
        Random random2 = new Random();

        // 初始化
        int count = 0;

        int r1;
        int c1;
        while (count < mineNum) {
            r1 = random1.nextInt(WIDTH);
            c1 = random2.nextInt(HEIGHT);
            if (!cellArrs[r1][c1].isMine() && !cellArrs[r1][c1].isNone()) {
                cellArrs[r1][c1].setValue(Cell.VALUE_MINE);
                count++;
            }
        }

        for (int x = row - 1; x < row + 2; x++)
            for (int y = col - 1; y < col + 2; y++) {
                if (isIn(x, y)) {
                    cellArrs[x][y].setValue(Cell.VALUE_EMPTY);
                }
            }
        // 计算数值
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (!cellArrs[i][j].isMine()) {
                    for (int x = i - 1; x < i + 2; x++)
                        for (int y = j - 1; y < j + 2; y++) {
                            if (isIn(x, y))
                                if (cellArrs[x][y].isMine())
                                    cellArrs[i][j].addValue();
                        }
                }
            }
        }
    }


    public MineLevel getMineLevel() {
        return mineLevel;
    }


}


