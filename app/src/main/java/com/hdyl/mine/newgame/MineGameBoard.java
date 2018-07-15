package com.hdyl.mine.newgame;

import android.graphics.Point;

import com.hdyl.mine.game.MineSetting;
import com.hdyl.mine.newgame.level.MineLevel;
import com.hdyl.tetris.GameBoard;

import java.util.Random;
import java.util.Stack;

/**
 * <p>Created by liugd on 2018/4/4.<p>
 * <p>佛祖保佑，永无BUG<p>
 */

public class MineGameBoard {

    private MineLevel mineLevel = MineSetting.getInstance().getMineLevel();


    private int WIDTH = mineLevel.getWidth();
    private int HEIGHT = mineLevel.getHeight();
    private int mineNum = mineLevel.getMineNum();


    Stack<Point> pointsReady2Checked = new Stack<Point>();// 待检查
    Stack<Point> pointsChecked = new Stack<Point>();// 已查

    MineCell cellArrs[][];
    int gameState = STATE_NO_START;

    int restMine;
    boolean isFirstClick;


    public MineGameBoard() {
        init();
    }

    public void newGame() {
        isFirstClick = true;
        restMine = mineNum;
        pointsChecked.clear();// 检查过的
        restMine = this.mineNum;

        for (int i = 0; i < GameBoard.yCount; i++) {
            for (int j = 0; j < GameBoard.xCount; j++) {
                cellArrs[i][j].clear();
            }
        }
        gameState = GameBoard.GAME_STATE_PLAYING;

    }


    private void init() {
        cellArrs = new MineCell[WIDTH][HEIGHT];
        for (int i = 0; i < GameBoard.yCount; i++) {
            for (int j = 0; j < GameBoard.xCount; j++) {
                cellArrs[i][j] = new MineCell();
            }
        }
    }

    private MineCell getCell(int x, int y) {
        return cellArrs[x][y];
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
                    cellArrs[x][y].setValue(MineCell.VALUE_NONE);
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
                cellArrs[r1][c1].setValue(MineCell.VALUE_MINE);
                count++;
            }
        }

        for (int x = row - 1; x < row + 2; x++)
            for (int y = col - 1; y < col + 2; y++) {
                if (isIn(x, y)) {
                    cellArrs[x][y].setValue(MineCell.VALUE_EMPTY);
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

    // 点击了打开的数字
    private void clickNumber(int i, int j) {
        int count = 0;
        for (int x = i - 1; x < i + 2; x++)
            for (int y = j - 1; y < j + 2; y++) {
                if (isIn(x, y) && cellArrs[x][y].isFlag()) {// 检查9格没出界,且检查标记数量
                    count++;
                }
            }
        boolean isIn = false;
        if (cellArrs[i][j].getValue() == count) {// 相同
            for (int x = i - 1; x < i + 2; x++)
                for (int y = j - 1; y < j + 2; y++) {
                    if (isIn(x, y) && cellArrs[x][y].isOpen()) {// 检查9格没出界,且检查标记数量
                        isIn = true;
                        openArea(x, y);
                    }
                }
        }
        if (isIn) {
            if (isWin()) {

            }
            onGameListener.onInvalidate();
        }
    }

    private boolean isWin() {
        if (gameState == STATE_LOSE) {
            return false;
        }
        int count = 0;
        for (int i = 0; i < WIDTH; i++)
            for (int j = 0; j < HEIGHT; j++) {
                MineCell cell = getCell(i, j);
                if (cell.isCover() || cell.isFlag()) {
                    count++;
                }
            }

        if (count == mineNum) {
            for (int i = 0; i < WIDTH; i++)
                for (int j = 0; j < HEIGHT; j++) {
                    if (cellArrs[i][j].isCover())// 剩下没标记的全部做标记
                        cellArrs[i][j].setFlag(MineCell.FLAG_FLAG);
                }
            gameState = STATE_WIN;

            onGameListener.onGamePercentChange(100);
            onGameListener.onGameWin();
            return true;
        } else {
            onGameListener.onGamePercentChange(100 - count * 100f / (WIDTH * HEIGHT));
        }
        return false;
    }


    public void check(int i, int j) {
        for (int x = i - 1; x < i + 2; x++)
            for (int y = j - 1; y < j + 2; y++) {
                MineCell cell = getCell(x, y);
                if (isIn(x, y) && cell.isFlagValue(MineCell.FLAG_COVER)) {// 检查9格没出界
                    cellArrs[x][y].setFlag(MineCell.FLAG_OPEN);
                    if (cellArrs[x][y].isValueZero()) {
                        Point point = new Point(x, y);
                        if (!pointsChecked.contains(point))
                            pointsReady2Checked.push(point);
                    } else if (cellArrs[x][y].isMine()) {
                        click9(x, y);
                    }
                }
            }
    }

    private void click9(int i1, int j1) {
        cellArrs[i1][j1].setValue(MineCell.VALUE_ERROR_MINE);
        cellArrs[i1][j1].setFlag(MineCell.FLAG_OPEN);
        gameState = STATE_LOSE;
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                MineCell cell = getCell(i, j);
                if (cell.isFlagValue(MineCell.FLAG_FLAG) && !cell.isMine()) {// 标记是雷但实际不是雷，则被打开
                    cell.setValue(MineCell.VALUE_ERROR_BLACK);
                    cell.setFlag(MineCell.FLAG_OPEN);// 打开了
                } else if (cell.isFlagValue(MineCell.FLAG_COVER) && cell.isMine()) {
                    cell.setFlag(MineCell.FLAG_OPEN);// 打开
                }
            }
        }
        onGameListener.onGameOver();
    }

    public final static int STATE_NO_START = 0;
    public final static int STATE_IN_GAME = 1;
    public final static int STATE_WIN = 2;
    public final static int STATE_LOSE = 3;

    /**
     * 打开区域
     *
     * @param i
     * @param j
     */
    private void openArea(int i, int j) {
        if (gameState == STATE_LOSE) {
            return;
        }
        cellArrs[i][j].setFlag(MineCell.FLAG_COVER);
        if (cellArrs[i][j].isValueZero()) {
            pointsReady2Checked.clear();
            pointsChecked.push(new Point(i, j));// 已查i,j
            check(i, j);// 检查i,j
            while (!pointsReady2Checked.isEmpty() && gameState != STATE_LOSE) {
                Point p = pointsReady2Checked.pop();
                check(p.x, p.y);// 检查i,j
            }
            pointsChecked.clear();
            // for (int x = i - 1; x < i + 2; x++)
            // for (int y = j - 1; y < j + 2; y++) {
            // if (isIn(x, y) && arrCover[x][y] == 0) {// 检查9格没出界
            // arrCover[x][y] = 1;
            // if (arr[x][y] == 0)
            // openArea(x, y);
            // else if (arr[x][y] == 9) {
            // click9(x, y);
            // }
            // }
            // }
        } else if (cellArrs[i][j].isMine()) {
            click9(i, j);
        }
    }


    OnGameListener onGameListener;


    public interface OnGameListener {
        void onInvalidate();

        void onGameOver();

        void onGameWin();

        void onGamePercentChange(float var);
    }
}


