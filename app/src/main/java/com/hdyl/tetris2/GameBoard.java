package com.hdyl.tetris2;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.hdyl.baselib.base.App;
import com.hdyl.baselib.utils.MySharepreferences;
import com.hdyl.baselib.utils.log.LogUitls;
import com.hdyl.tetris2.shape.Cell;
import com.hdyl.tetris2.shape.GameShape;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liugd on 2017/9/4.
 */

public class GameBoard {

    public final static int WIDTH = 10;
    public final static int HEIGHT = 10;

    public boolean isGaming=false;

    public GameShape gameShapes[] = new GameShape[3];

    public LineDispearAnim lineDispearAnim=new LineDispearAnim();
    public int mScore;

    public int mHighScore;
    public Cell maps[][] = new Cell[HEIGHT][WIDTH];

    public int oneSize;

    public GameBoard() {
        init();
    }

    public void setOneSize(int oneSize) {
        this.oneSize = oneSize;
    }

    {
        mHighScore= MySharepreferences.getInt(App.getContext(),"tetris2","highscore",0);
    }

    public void saveHighScore(){
        MySharepreferences.putInt(App.getContext(),"tetris2","highscore",mHighScore);
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


    private boolean isAllEmpty() {
        for (GameShape shape : gameShapes) {
            if (shape != null)
                return false;
        }
        return true;
    }

    public void createNewGameShape() {
        gameShapes[0] = GameShape.createRandomShape();
        gameShapes[1] = GameShape.createRandomShape();
        gameShapes[2] = GameShape.createRandomShape();
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
        isGaming=true;
        lineDispearAnim.clearAnim();
    }


    public boolean isGameOver() {
        for (GameShape shape : gameShapes) {
            if (shape != null)
                if (hasPlace(shape)) {
                    return false;
                }
        }
        return true;
    }

    private boolean hasPlace(GameShape gameShape) {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                int maxX = gameShape.getXCount() + j;
                int maxY = gameShape.getYCount() + i;
                a:
                if (maxY <= HEIGHT && maxX <= WIDTH) {//无越界
                    for (int y = 0; y < gameShape.getYCount(); y++)
                        for (int x = 0; x < gameShape.getXCount(); x++) {
                            //不能摆放
                            if (gameShape.isPositionCellFull(x, y) && this.maps[i + y][x + j].isValueFull()) {
                                break a;
                            }
                        }
                    return true;
                }
            }
        }
        return false;
    }


    private void newGameInitAll() {
        onGameListener.onScoreChange(0, mScore);
        onGameListener.onHighScore(true,mHighScore);
        onGameListener.invalidateGameBoard();
        onGameListener.onNewGame();
    }

    public void initArr() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                maps[i][j].reset();
            }
        }
    }

    public void deleteGameShape(int index) {
        gameShapes[index] = null;
        if (isAllEmpty()) {
            createNewGameShape();//创建一个新的
        }
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

        int addScore1=shape.countFullPoint()*10;
       int addScore= xiaohang();
        addScore+=addScore1;
        mScore += addScore;
        onGameListener.onScoreChange(addScore, mScore);
        if (isGameOver()) {
            isGaming=false;
            onGameListener.onGameOver();
            if(mScore>mHighScore){
                mHighScore=mScore;
                saveHighScore();
                onGameListener.onHighScore(false,mHighScore);
            }
        }
        onGameListener.invalidateGameBoard();
        return true;
    }

    public boolean isGaming() {
        return isGaming;
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
                if (!xx1) {
                    return false;
                }
                boolean yy1 = isRightY(y + addY);
                if (!yy1) {
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


    public int xiaohang() {
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
            lineDispearAnim.addAnim(maps,listX,listY);
            for (int y : listY) {
                clearX(y);
            }
            for (int x : listX) {
                clearY(x);
            }
            return (listX.size()+listY.size())*100;

        }
        return 0;
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

        void onHighScore(boolean isInited,int highScore );

        void invalidateGameBoard();

        void onGameOver();//游戏结束

        void onNewGame();
    }

}
