package com.hdyl.tetris2;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.hdyl.tetris2.shape.Cell;
import com.hdyl.tetris2.shape.GameShape;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liugd on 2017/9/4.
 */

public class GameBoard {

    public final static int WIDTH = 12;
    public final static int HEIGHT = 12;


    public GameShape gameShapes[] = new GameShape[3];


    public int mScore;
    public Cell maps[][] = new Cell[HEIGHT][WIDTH];

    public int oneSize;

    public GameBoard() {
        init();
    }

    public void setOneSize(int oneSize) {
        this.oneSize = oneSize;
    }


//    public void setGameShapesOneSize(int oneSize) {
//        for (GameShape gameShape : gameShapes) {
//            if (gameShape != null) {
//                gameShape.setOneSize(oneSize);
//            }
//        }
//    }

    public int contains(int x, int y) {
        int index = 0;
        for (GameShape gameShape : gameShapes) {
            if (gameShape != null && gameShape.contains(x, y)) {
                return index;
            }
            index++;
        }
        return -1;
    }


    public GameShape getGameShape(int index) {
        return gameShapes[index];
    }

    public void drawGameShapes(Canvas canvas) {
        for (GameShape gameShape : gameShapes) {
            if (gameShape != null)
                gameShape.draw(canvas);
        }
    }

    public void initLocation() {

    }

    private boolean isAllEmpty() {
        for (GameShape shape : gameShapes) {
            if (shape != null)
                return false;
        }
        return true;
    }

    public void createNewGameShape() {
        if (isAllEmpty()) {
            gameShapes[0] = GameShape.createRandomShape();
            gameShapes[1] = GameShape.createRandomShape();
            gameShapes[2] = GameShape.createRandomShape();
        }
    }


    private void init() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                maps[i][j] = new Cell();
            }
        }
    }

    public Cell[][] getMaps() {
        return maps;
    }


    public void drawBoard(Canvas canvas) {
        Rect rect = new Rect();
        for (int i = 0; i < GameBoard.HEIGHT; i++) {
            for (int j = 0; j < GameBoard.WIDTH; j++) {
                rect.left = j * oneSize;
                rect.right = rect.left + oneSize;
                rect.top = i * oneSize;
                rect.bottom = rect.top + oneSize;
                getMaps()[i][j].draw(canvas, rect);
            }
        }

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
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                maps[i][j].reset();
//                Random random = new Random();
//                maps[i][j].value = (byte) random.nextInt(2);
//                maps[i][j].color = GameColor.getRandomResIndex();
            }
        }
    }

    public void deleteGameShape(int index) {
        gameShapes[index] = null;
        createNewGameShape();//创建一个新的
    }


    public boolean addShapeOnBoard(int index) {
        GameShape shape = getGameShape(index);
        if (!canLocation(shape)) {
            return false;
        }
        //放置这个图形

        int x, y = 0;
        int addX = shape.getX();
        int addY = shape.getY();
        for (Cell cells[] : shape.getArr()) {
            x = 0;
            for (Cell cell : cells) {
                int xx = x + addX;
                int yy = y + addY;
                if (cell.isValueFull()) {
                    Cell base = getMaps()[yy][xx];
                    base.setValueFull();
                    base.setColor(shape.getColorIndex());
                }
                x++;
            }
            y++;
        }
        deleteGameShape(index);
        xiaohang();
        onGameListener.invalidateGameBoard();
        return true;
    }

//
//    public int shape2Index(GameShape shape) {
//        int count = 0;
//        for (GameShape shape1 : gameShapes) {
//            if (shape.equals(shape1)) {
//                return count;
//            }
//            count++;
//        }
//        return -1;
//    }

    /***
     * 是否可以放置这个图形
     *
     * @param shape
     * @return
     */
    public boolean canLocation(GameShape shape) {
        int x, y = 0;

        int addX = shape.getX();
        int addY = shape.getY();
        for (Cell cells[] : shape.getArr()) {
            x = 0;
            for (Cell cell : cells) {
                boolean xx1 = isRightX(x + addX);
                boolean yy1 = isRightY(y + addY);
                if (xx1 == false || yy1 == false) {
                    return false;
                }
                int xx = x + addX;
                int yy = y + addY;
                if (getMaps()[yy][xx].isValueFull() && cell.isValueFull()) {
                    return false;
                }
                x++;
            }
            y++;
        }
        return true;
    }

    private boolean isRightX(int x) {
        if (x >= WIDTH) {
            return false;
        }
        if (x < 0) {
            return false;
        }
        return true;
    }

    private boolean isRightY(int y) {
        if (y >= HEIGHT) {
            return false;
        }
        if (y < 0) {
            return false;
        }
        return true;
    }

    private int getRightX(int x) {
        if (x >= WIDTH) {
            return WIDTH - 1;
        }
        if (x < 0) {
            return 0;
        }
        return x;
    }

    private int getRightY(int y) {
        if (y >= HEIGHT) {
            return HEIGHT - 1;
        }
        if (y < 0) {
            return 0;
        }
        return y;
    }


    public void xiaohang() {
        List<Integer> listY = new ArrayList<>(HEIGHT);
        for (int y = 0; y < HEIGHT; y++) {
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
            int score = (listX.size() + listY.size()) * 100;
            mScore += score;
            onGameListener.onScoreChange(score, mScore);
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
        for (int y = 0; y < HEIGHT; y++) {
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
        for (int y = 0; y < HEIGHT; y++) {
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
