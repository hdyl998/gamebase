package com.hdyl.tetris;

import com.hdyl.tetris.shape.TetrisShape;

import java.util.HashMap;

/**
 * Created by liugd on 2017/6/2.
 */

public class GameData {
    public Cell cellArrs[][];
    public int curScore;
    public int highScore;
    public int xiaoLine;//消掉的line，计算行数
    public int xOffset;//当前方块的X偏移
    public int yOffset;//当前方块的y偏移

    /***
     * 统计出现的图型类型
     */
    public HashMap<Class<? extends TetrisShape>, Integer> hashMapCounts = new HashMap<>();
    public int gameState = GameBoard.GAME_STATE_GAMEOVER;

    public void initArrays() {
        cellArrs = new Cell[GameBoard.yCount][GameBoard.xCount];
        for (int i = 0; i < GameBoard.yCount; i++) {
            for (int j = 0; j < GameBoard.xCount; j++) {
                cellArrs[i][j] = new Cell();
            }
        }
    }

    public void initDatas() {
        for (int i = 0; i < GameBoard.yCount; i++) {
            for (int j = 0; j < GameBoard.xCount; j++) {
                cellArrs[i][j].clear();
            }
        }
        curScore = 0;
        xiaoLine = 0;
        hashMapCounts.clear();
        gameState = GameBoard.GAME_STATE_PLAYING;
    }
}
