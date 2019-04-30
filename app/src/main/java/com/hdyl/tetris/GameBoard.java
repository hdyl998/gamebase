package com.hdyl.tetris;

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.SparseArray;

import com.alibaba.fastjson.JSON;
import com.hdyl.baselib.base.App;
import com.hdyl.tetris.common.GameConfig;
import com.hdyl.tetris.shape.CellArray;
import com.hdyl.tetris.shape.TetrisShape;
import com.hdyl.tetris.shape.TetrisShapeFactory;
import com.hdyl.tetris.sound.SoundManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 游戏面板，即游戏逻辑类
 * Created by liugd on 2017/5/8.
 */

public class GameBoard {
    public final static int xCount = 10;
    public final static int yCount = 20;


    LineDispearAnim lineDispearAnim = new LineDispearAnim();
    DownAnim downAnim = new DownAnim();
    private OnGameEvent onGameEvent;

    GameData gameData = new GameData();
    private TetrisShape curShape;
    private TetrisShape nextShape;
    public Cell cellArrs[][];

    public final static int GAME_STATE_GAMEOVER = 2;
    public final static int GAME_STATE_PAUSE = 1;
    public final static int GAME_STATE_PLAYING = 0;


    public GameBoard() {
        init();
    }

    public void drawBoard(Canvas canvas, float size) {
        RectF rect = new RectF();
        Cell[][] cellArrs = this.getCellArrs();
        for (int i = 0; i < GameBoard.yCount; i++) {
            for (int j = 0; j < GameBoard.xCount; j++) {
                Cell cell = cellArrs[i][j];
                if (cell.isFull()) {
                    rect.left = j * size;
                    rect.right = rect.left + size;
                    rect.top = i * size;
                    rect.bottom = rect.top + size;
                    cell.draw(canvas, rect);
                }
            }
        }
    }

    public void drawCurTetrisShadow(Canvas canvas, TetrisShape shape, float size, Paint paint) {
        int yOffset1 = getDistenceToBottom(shape);
        if (yOffset1 >= 5)//超过5再绘制
            shape.draw(canvas, size, getXOffset(), getYOffset() + yOffset1, paint);
    }

    public void drawCurTetris(Canvas canvas, float size) {
        TetrisShape shape = this.getCurShape();
        shape.draw(canvas, size, this.gameData.xOffset, this.gameData.yOffset);
//        CellArray array = shape.getCellArray();
//        RectF rectF = new RectF();
//        for (PositionCell cell : array.cells) {
//            if (cell.isFull()) {
//                rectF.left = (cell.getX() + this.gameData.xOffset) * size;
//                rectF.right = rectF.left + size;
//                rectF.top = (cell.getY() + this.gameData.yOffset) * size;
//                rectF.bottom = rectF.top + size;
//                cell.draw(canvas, rectF);
//            }
//        }
    }


    public void setOnGameEvent(OnGameEvent onGameEvent) {
        this.onGameEvent = onGameEvent;
    }

    /***
     * 初始化
     */
    private void init() {
        gameData.initArrays();
        cellArrs = gameData.cellArrs;
    }

    /****
     * 新游戏
     */
    public void newGame() {
        lineDispearAnim.clearAnim();
        gameData.initDatas();
        nextShape = TetrisShapeFactory.createRandomShape();
        nextTetrisShape();
        newGameInitAll();
    }

    private void newGameInitAll() {
        onGameEvent.onScoreChanged(gameData.curScore, 0, gameData.highScore, gameData.xiaoLine);
        onGameEvent.invalidateGameBoard();
        onGameEvent.invalidateNextBoard();
        onGameEvent.onNewGame();
    }

    public void nextTetrisShape() {
        curShape = nextShape;
        countTetrisShape(curShape);
        nextShape = TetrisShapeFactory.createRandomShape();
        gameData.xOffset = (xCount - curShape.getXLen()) / 2;//图形的宽度是2
        gameData.yOffset = -curShape.getMinYData();
    }

    private TetrisShape countTetrisShape(TetrisShape shape) {
        Class<? extends TetrisShape> clazz = shape.getClass();
        Integer integer = gameData.hashMapCounts.get(clazz);
        if (integer != null) {
            integer++;
            gameData.hashMapCounts.put(clazz, integer);
        } else {
            gameData.hashMapCounts.put(clazz, 1);
        }
        System.out.println(gameData.hashMapCounts.get(clazz) + "" + clazz);
        return shape;
    }

    /****
     * 手动移动
     *
     * @param direction
     */
    public void move(int direction) {
        if (isGamePlaying() == false) {
            return;
        }
        downAnim.clearAnim();
        switch (direction) {
            case DIRECTION_LEFT:
                SoundManager.getInstance().playSound(SoundManager.SOUND_LEFT_RIGHT);
                moveLeft();
                break;
            case DIRECTION_RIGHT:
                SoundManager.getInstance().playSound(SoundManager.SOUND_LEFT_RIGHT);
                moveRight();
                break;
            case DIRECTION_DOWN:
                int distence0 = getDistenceToBottom(curShape);
                int downCount = 3;
                if (distence0 < downCount) {
                    fastDown();
                } else {
                    SoundManager.getInstance().playSound(SoundManager.SOUND_DOWN);
                    downALine(downCount);
                }
                break;
            case DIRECTION_FAST_DOWN:
                fastDown();
                break;
            case DIRECTION_ROTATE:
                rotate();
                break;
        }
    }


    private void fastDown() {
        SoundManager.getInstance().playSound(SoundManager.SOUND_FASTDOWN);
        //把当前的方块添加到面板上
        int distence = getDistenceToBottom(curShape);
        if (GameConfig.getInstance().isAnimDown()) {
            downAnim.addAnim(cellArrs, curShape, distence, gameData.xOffset, gameData.yOffset);
        }
        addTetrisShape();
    }


    /***
     * 获得图形到底部方块的距离的最小值，用于下落
     *
     * @param shape
     * @return
     */
    private int getDistenceToBottom(TetrisShape shape) {
        HashMap<Integer, Integer> map = shape.getAllBottomData();
        Iterator iter = map.entrySet().iterator();
        int min = yCount;
        while (iter.hasNext()) {
            Map.Entry<Integer, Integer> entry = (Map.Entry) iter.next();
            int x = entry.getKey() + gameData.xOffset;
            int y = entry.getValue() + gameData.yOffset;
            min = Math.min(min, getYHeight(x, y));//获得的距离
        }
        min--;//这里和返回的固定值之间存在1的差值
        return min;
    }

    /**
     * 获取一个点到下面有格点的距离
     *
     * @param x
     * @param y
     * @return
     */
    private int getYHeight(int x, int y) {

        for (int y1 = y + 1; y1 < yCount; y1++) {
            if (cellArrs[y1][x].isFull()) {
                return y1 - y;
            }
        }
        return yCount - y;
    }


    private void moveLeft() {
        if (!checkCanMove(DIRECTION_LEFT)) {
            return;
        }
        gameData.xOffset--;
        onGameEvent.invalidateGameBoard();
    }

    private boolean checkCanMove(int direction) {
        tempX = 0;
        tempY = 0;
        switch (direction) {
            case DIRECTION_LEFT:
                tempX = -1;
                break;
            case DIRECTION_RIGHT:
                tempX = 1;
                break;
            case DIRECTION_DOWN:
                tempY = 1;
                break;
        }
        return canMove();
    }

    int tempX;
    int tempY;

    /***
     * 是否能移动的通用提取函数
     *
     * @return
     */
    private boolean canMove() {
        int curX = tempX + gameData.xOffset;
        int curY = tempY + gameData.yOffset;
//        if (!isIillagleXY(curX, curY)) {
//            return false;
//        }
        CellArray cellArray = curShape.getCellArray();
        for (PositionCell cell : cellArray.getCells()) {
            int x = cell.getX() + curX;
            int y = cell.getY() + curY;
            if (!isIillagleXY(x, y)) {
                return false;
            }
            if (this.cellArrs[y][x].isFull()) {
                return false;
            }
        }
        return true;
    }


    private void moveRight() {
        if (!checkCanMove(DIRECTION_RIGHT)) {
            return;
        }
        gameData.xOffset++;
        onGameEvent.invalidateGameBoard();
    }

    //是否是正确的XY
    private boolean isIillagleXY(int x, int y) {
        return x > -1 && y > -1 && x < xCount && y < yCount;
    }


    /***
     * 方块向下移动一格,外部计时器调用
     */
    public void downALine(int count) {
        if (isGamePlaying() == false) {
            return;
        }
        for (int i = 0; i < count; i++) {
            if (!checkCanMove(DIRECTION_DOWN)) {
                //不能再向下了，则已到极限了，则把方块加到面板上
                addTetrisShape();
                return;
            }
        }
        if (GameConfig.getInstance().isAnimDown())
            downAnim.addAnim(cellArrs, curShape, count, gameData.xOffset, gameData.yOffset);
        gameData.yOffset += count;
        onGameEvent.invalidateGameBoard();
    }

    /***
     * 把俄罗斯方块的形状添加到面版上
     */
    public void addTetrisShape() {
        CellArray cellArray = curShape.getCellArray();
        int distence = getDistenceToBottom(curShape);
        for (PositionCell cell : cellArray.getCells()) {
            int x = cell.getX() + gameData.xOffset;
            int y = cell.getY() + gameData.yOffset + distence;
            Cell cell1 = this.cellArrs[y][x];
            cell1.setFull(true);
            cell1.setResIndex(cell.getResIndex());
        }
        List<Integer> list = checkFullLine();
        System.out.println("消的行数" + list);
        //计分逻辑，1行100分，2行300分，3行700分，4行1500分
        int addScore = 0;
        switch (list.size()) {
            case 0:
                SoundManager.getInstance().playSound(SoundManager.SOUND_DOWN);
                break;
            case 1:
                SoundManager.getInstance().playSound(SoundManager.SOUND_DELETE1);
                addScore = 100;
                break;
            case 2:
                SoundManager.getInstance().playSound(SoundManager.SOUND_DELETE2);
                addScore = 300;
                break;
            case 3:
                SoundManager.getInstance().playSound(SoundManager.SOUND_DELETE3);
                addScore = 700;
                break;
            default:
                SoundManager.getInstance().playSound(SoundManager.SOUND_DELETE4);
                addScore = 1500;
                break;
        }
        if (GameConfig.getInstance().isAnimXiaohang()) {
            lineDispearAnim.addAnim(cellArrs, list);
        }
        if (list.size() > 0) {
            //清掉满的一行
            clearFullLine(list);
            this.addScore(addScore);
            this.addXiaoLine(list.size());
            if (gameData.curScore > gameData.highScore) {
                gameData.highScore = gameData.curScore;
            }
            onGameEvent.onScoreChanged(getCurScore(), addScore, getHighScore(), getXiaoLine());
        }

        //下一个方块 <>
        nextTetrisShape();
        boolean isGameOver = checkGameOver();
        if (isGameOver) {
            setGameState(GAME_STATE_GAMEOVER);
            onGameEvent.onGameOver();
        }
        onGameEvent.invalidateGameBoard();
        onGameEvent.invalidateNextBoard();
    }

    /***
     * 是否可以旋转
     *
     * @return
     */
    public boolean canRotate() {
        //方块自身不能旋转
        if (!getCurShape().canRotate()) {
            return false;
        }
        CellArray cellArray = getCurShape().peekRotate(GameConfig.getInstance().isNishi());
        for (PositionCell cell : cellArray.getCells()) {
            int x = cell.getX() + gameData.xOffset;
            int y = cell.getY() + gameData.yOffset;

            if (!isIillagleXY(x, y)) {
                //修正偏移，解决因为出界不能旋转的问题，即靠墙不能旋转的问题
                System.out.println(x);
                if (x < 0) {
                    gameData.xOffset += -x;
                    x = cell.getX() + gameData.xOffset;
                } else if (x >= xCount) {
                    gameData.xOffset = gameData.xOffset - (x - xCount + 1);
                    x = cell.getX() + gameData.xOffset;
                } else if (y < 0) {
                    gameData.yOffset += -y;
                    y = cell.getY() + gameData.yOffset;
                } else {
                    return false;
                }
            }
            if (this.cellArrs[y][x].isFull()) {
                return false;
            }
        }
        return true;
    }

    /***
     * 旋转
     */
    private void rotate() {
        SoundManager.getInstance().playSound(SoundManager.SOUND_ROTATION);
        if (canRotate()) {
            getCurShape().rotate(GameConfig.getInstance().isNishi());
            onGameEvent.invalidateGameBoard();
        }
    }

    public boolean checkGameOver() {
        CellArray cellArray = curShape.getCellArray();
        for (PositionCell cell : cellArray.getCells()) {
            int x = cell.getX() + gameData.xOffset;
            int y = cell.getY() + gameData.yOffset;
            if (!isIillagleXY(x, y)) {
                return true;
            }
            if (this.cellArrs[y][x].isFull()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 清掉满的一行
     *
     * @param list
     */
    private void clearFullLine(List<Integer> list) {
        //反序
        Collections.reverse(list);
        //清掉
        for (Integer y : list) {
            for (int k = 0; k < xCount; k++) {
                cellArrs[y][k].setFull(false);
            }
        }
        int bottomY = list.get(0);
        SparseArray<Integer> map = new SparseArray<>();
        for (int i = 0; i < bottomY; i++) {
            if (!list.contains(i)) {
                map.put(i, countMinCounts(list, i));
            }
        }


//        System.out.println(bottomY);
//        System.out.println(JSON.toJSONString(map));
        for (int i = bottomY - 1; i >= 0; i--) {
            Integer integer = map.get(i);
            if (integer != null) {
                moveY(i, i + integer);
            }
        }
        //根据行数，清掉不能下落的1-4行，不加这段将会是BUG，如果总高度达到了1-4位置了，再清除下面的行，会导致1-4行位置不能被清掉，所以要根据行数进行置空操作
        for (int i = 0; i < list.size(); i++) {
            for (int k = 0; k < xCount; k++) {
                cellArrs[i][k].setFull(false);
            }
        }
    }

    private int countMinCounts(List<Integer> list, int num) {
        int count = 0;
        for (Integer integer : list) {
            if (num < integer) {
                count++;
            }
        }
        return count;
    }

    /***
     * 从上面一行把 y移到下面一行
     *
     * @param fromY 从原行，原行一般不为空
     * @param toY   目标行，目标行前提是空行
     */
    private void moveY(int fromY, int toY) {
        //同上面一行交换
        for (int j = 0; j < xCount; j++) {
            cellArrs[toY][j].setValue(cellArrs[fromY][j]);
        }
    }


    /***
     * 返回已满的行数
     *
     * @return
     */
    public List<Integer> checkFullLine() {
        List<Integer> list = new ArrayList<>(4);
        for (int i = 0; i < yCount; i++) {
            if (isFullLine(i)) {
                list.add(i);
            }
        }
        return list;
    }

    private boolean isFullLine(int y) {
        for (int j = 0; j < xCount; j++) {
            if (!cellArrs[y][j].isFull()) {
                return false;
            }
        }
        return true;
    }

    public final static int DIRECTION_LEFT = 0;
    public final static int DIRECTION_RIGHT = 1;
    public final static int DIRECTION_DOWN = 2;
    public final static int DIRECTION_FAST_DOWN = 3;
    public final static int DIRECTION_ROTATE = 4;

    public Cell[][] getCellArrs() {
        return cellArrs;
    }

    public int getCurScore() {
        return gameData.curScore;
    }

    public void addScore(int addScore) {
        this.setCurScore(gameData.curScore + addScore);
    }

    public void setXiaoLine(int xiaoLine) {
        this.gameData.xiaoLine = xiaoLine;
    }

    public int getXiaoLine() {
        return gameData.xiaoLine;
    }

    public void addXiaoLine(int addXiaoLine) {
        this.setXiaoLine(addXiaoLine + gameData.xiaoLine);
    }

    public int getHighScore() {
        return gameData.highScore;
    }

    public void setCurScore(int curScore) {
        this.gameData.curScore = curScore;
    }

    public void setHighScore(int highScore) {
        this.gameData.highScore = highScore;
    }

    public boolean isGameOver() {
        return gameData.gameState == GAME_STATE_GAMEOVER;
    }

    public void setGameState(int gameState) {
        this.gameData.gameState = gameState;
    }


    public boolean isGamePause() {
        return gameData.gameState == GAME_STATE_PAUSE;
    }

    public boolean isGamePlaying() {
        return gameData.gameState == GAME_STATE_PLAYING;
    }

    public void doPauseGame() {
        if (isGamePlaying()) {
            exchangePausePlayingGameState();
        }
    }

    /***
     * 交换游戏状态，用于控件暂停及恢复游戏状态
     */
    public void exchangePausePlayingGameState() {
        if (!isGameOver()) {
            if (isGamePause()) {
                setGameState(GAME_STATE_PLAYING);
                onGameEvent.onGamePauseResume();
                onGameEvent.invalidateGameBoard();
            } else {
                setGameState(GAME_STATE_PAUSE);
                onGameEvent.onGamePause();
                onGameEvent.invalidateGameBoard();
            }
        }
    }


    public int getXOffset() {
        return gameData.xOffset;
    }

    public int getYOffset() {
        return gameData.yOffset;
    }

    public int getGameState() {
        return gameData.gameState;
    }

    public TetrisShape getCurShape() {
        return curShape;
    }


    public TetrisShape getNextShape() {
        return nextShape;
    }

    public interface OnGameEvent {
        void onScoreChanged(int curScore, int addScore, int highScore, int curLine);//当得分改变时

        void onGameOver();//游戏结束

        void invalidateGameBoard();//绘制当前面版

        void invalidateNextBoard();//绘制下一个方块的小面板

        void onNewGame();

        void onGamePause();

        void onGamePauseResume();
    }


    public void save() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(App.getContext());
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(KEY_CACHE_TEMP, JSON.toJSONString(gameData));
        editor.putString(KEY_CACHE_SHAPE_CUR, JSON.toJSONString(new TetrisShape.TetrisShapeSaveBean(this.curShape)));
        editor.putString(KEY_CACHE_SHAPE_NEXT, JSON.toJSONString(new TetrisShape.TetrisShapeSaveBean(this.nextShape)));
        editor.commit();
    }

    private GameData readData() {
        GameData data = null;
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(App.getContext());
        String string = settings.getString(KEY_CACHE_TEMP, null);
        String stringCur = settings.getString(KEY_CACHE_SHAPE_CUR, null);
        String stringNext = settings.getString(KEY_CACHE_SHAPE_NEXT, null);

        if (string != null) {
            try {
                data = JSON.parseObject(string, GameData.class);
                curShape = JSON.parseObject(stringCur, TetrisShape.TetrisShapeSaveBean.class).createTetrisShape();
                Log.e("aaaa", JSON.toJSONString(curShape));
                nextShape = JSON.parseObject(stringNext, TetrisShape.TetrisShapeSaveBean.class).createTetrisShape();
                Log.e("aaaa", JSON.toJSONString(nextShape));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    public final static String KEY_CACHE_TEMP = "gamelogic_tetris";
    public final static String KEY_CACHE_SHAPE_CUR = "gamelogic_shap1_tetris";
    public final static String KEY_CACHE_SHAPE_NEXT = "gamelogic_shap2_tetris";

    private void setGameData(GameData gameData) {
        this.gameData = gameData;
        this.cellArrs = gameData.cellArrs;
        newGameInitAll();
    }

    public void read() {
        GameData data = readData();

        if (data != null) {
            //设置读到的数据
            setGameData(data);
        } else {
            //新游戏
            newGame();
            System.out.println("新游戏");
        }
    }
}
