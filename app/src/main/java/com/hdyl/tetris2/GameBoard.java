package com.hdyl.tetris2;

import com.hdyl.tetris2.shape.Cell;
import com.hdyl.tetris2.shape.GameShape;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liugd on 2017/9/4.
 */

public class GameBoard {

    public final static int WIDTH = 10;
    public final static int HGIGHT = 10;

    public int pointX;
    public int pointY;


    public GameShape gameShape;

    public int mScore;
    public Cell maps[][] = new Cell[HGIGHT][WIDTH];

    public GameBoard() {
        init();
    }


    public GameShape getGameShape() {
        if (gameShape == null) {
            return createNewGameShape();
        }
        return gameShape;
    }

    public GameShape createNewGameShape() {
        return gameShape = GameShape.createRandomShape();
    }


    private void init() {
        for (int i = 0; i < HGIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                maps[i][j] = new Cell();
            }
        }
    }

    public Cell[][] getMaps() {
        return maps;
    }

    public void newGame() {
        initArr();
        mScore = 0;
        createNewGameShape();
        newGameInitAll();
    }

    private void newGameInitAll() {
        onGameListener.onScoreChange(0, mScore);
        onGameListener.invalidateGameBoard();
        onGameListener.onNewGame();
    }

    public void initArr() {
        for (int i = 0; i < HGIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                maps[i][j].reset();
//                Random random = new Random();
//                maps[i][j].value = (byte) random.nextInt(2);
//                maps[i][j].color = GameColor.getRandomResIndex();
            }
        }
    }

    public void addShapeOnBoard(GameShape shape) {
        for (Cell cells[] : shape.getArr()) {

        }
    }

    public void xiaohang() {
        List<Integer> listY = new ArrayList<>(HGIGHT);
        for (int y = 0; y < HGIGHT; y++) {
            if (isFullX(y)) {
                listY.add(y);
            }
        }
        List<Integer> listX = new ArrayList<>(WIDTH);
        for (int x = 0; x < WIDTH; x++) {
            if (isFullY(x)) {
                listX.add(x);
            }
        }
        //行号大于0
        if (listX.size() > 0 || listY.size() > 0) {
            for (int y : listY) {
                clearX(y);
            }
            for (int x : listX) {
                clearY(x);
            }
            onGameListener.invalidateGameBoard();
        } else {

        }
    }

    public void clearX(int y) {
        for (int x = 0; x < WIDTH; x++) {
            maps[y][x].reset();
        }
    }

    public void clearY(int x) {
        for (int y = 0; y < HGIGHT; y++) {
            maps[y][x].reset();
        }
    }


    private boolean isFullX(int y) {
        for (int x = 0; x < WIDTH; x++) {
            if (!maps[y][x].isValueFull())
                return false;
        }
        return true;
    }

    private boolean isFullY(int x) {
        for (int y = 0; y < HGIGHT; y++) {
            if (!maps[y][x].isValueFull())
                return false;
        }
        return true;
    }


    OnGameEvent onGameListener;

    public void setOnGameEvent(OnGameEvent onGameListener) {
        this.onGameListener = onGameListener;
    }

    public interface OnGameEvent {
        void onScoreChange(int addScore, int finalScore);

        void invalidateGameBoard();

        void onGameOver();//游戏结束

        void onNewGame();
    }

}
