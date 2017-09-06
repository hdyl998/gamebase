package com.hdyl.m2048;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.IntDef;

import com.alibaba.fastjson.JSON;
import com.hdyl.baselib.base.App;
import com.hdyl.baselib.utils.log.LogUitls;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

/**
 * Created by Administrator on 2017/4/16.
 */

public class GameLogic {
    public final static int xCOUNT = 4;
    public final static int yCOUNT = 4;
    GameData gameData = new GameData();

    Stack<GameData> stacks = new Stack<>();
    //游戏动画面板
    GameAnimBoard animBoard = new GameAnimBoard();
    public Cell[][] arrData;


    Random rand = new Random();


    OnGameStateChangedLinstener linstener;

    public GameLogic() {
    }

    public GameAnimBoard getAnimBoard() {
        return animBoard;
    }

    public void setOnGameStateChangedLinstener(OnGameStateChangedLinstener linstener) {
        this.linstener = linstener;
    }

    public interface OnGameStateChangedLinstener {
        void onGameOver();

        void onGameScoreChanged(int addScore, int endScore, int hightScore);

        void onGameStepChanged(int endStep);
    }

    public void initData() {
        //初始化
        gameData.init(gameInitVar);
        this.arrData = gameData.arrData;
        if (linstener != null) {
            linstener.onGameScoreChanged(0, gameData.getScoreCur(), gameData.getScoreHigh());
            linstener.onGameStepChanged(gameData.getStep());
        }

    }


    public void setGameData(GameData gameData) {
        this.gameData = gameData;
        this.arrData = gameData.arrData;
        if (linstener != null) {
            linstener.onGameScoreChanged(0, gameData.getScoreCur(), gameData.getScoreHigh());
            linstener.onGameStepChanged(gameData.getStep());
        }
    }

    private int gameInitVar = 0;

    public void setGameInitVar(int gameInitVar) {
        this.gameInitVar = gameInitVar;
    }

    public void newGame() {
        stacks.clear();
        initData();
        LogUitls.print("cell", "新游戏");
        if (gameInitVar == 0) {
            createNewTile();
            createNewTile();
        }
    }

    /***
     * 创建一个新的数据
     */
    public void createNewTile() {
        List<Cell> list = getListCells();
        if (list.size() == 0) {
            gameData.setGameState(GameData.GAME_STATE_OVER);
            if (linstener != null)
                linstener.onGameOver();
            return;
        }
        int random = rand.nextInt(list.size());
        Cell cell = list.get(random);
        cell.setValue(getRandomNum());
        if (isGameOver()) {
            gameData.setGameState(GameData.GAME_STATE_OVER);
            if (linstener != null)
                linstener.onGameOver();
        }
        //创建格子的动画
        animBoard.getAnimManager(cell.getX(), cell.getY()).addCreateAnim();
    }

    private int getRandomNum() {
        if (rand.nextFloat() >= 0.9) {
            gameData.addNum4Count();
            return 4;
        }
        gameData.addNum2Count();
        return 2;
    }

    @IntDef({GAME_DIRECTION_LEFT, GAME_DIRECTION_UP, GAME_DIRECTION_RIGHT, GAME_DIRECTION_DOWN})
    @Retention(RetentionPolicy.SOURCE)
    private @interface GameDirection {
    }

    public final static int GAME_DIRECTION_LEFT = 0;
    public final static int GAME_DIRECTION_UP = 1;
    public final static int GAME_DIRECTION_RIGHT = 2;
    public final static int GAME_DIRECTION_DOWN = 3;


    //var!=0 比如2 0 0 2返回 第二个2的索引(3)，如2000 返回-1
    private Cell findNextTheSame(int var, int startIndex, int xOrY, @GameDirection int direction) {
        boolean isFirst = false;
        switch (direction) {
            case GAME_DIRECTION_LEFT:// <- toleft
                for (int j = startIndex + 1; j < xCOUNT; j++) {
                    int curValue = arrData[xOrY][j].getValue();
                    if (curValue == var) {
                        return arrData[xOrY][j];
                    } else if (curValue == 0) {
                        continue;
                    } else return null;
                }
                break;
            case GAME_DIRECTION_RIGHT://->toright
                for (int j = startIndex - 1; j >= 0; j--) {
                    int curValue = arrData[xOrY][j].getValue();
                    if (curValue == var) {
                        return arrData[xOrY][j];
                    } else if (curValue == 0) {
                        continue;
                    } else return null;
                }
                break;
            case GAME_DIRECTION_UP://
                for (int k = startIndex + 1; k < yCOUNT; k++) {
                    int curValue = arrData[k][xOrY].getValue();
                    if (curValue == var) {
                        return arrData[k][xOrY];
                    } else if (curValue == 0) {
                        continue;
                    } else return null;
                }
                break;
            case GAME_DIRECTION_DOWN:
                for (int k = startIndex - 1; k >= 0; k--) {
                    int curValue = arrData[k][xOrY].getValue();
                    if (curValue == var) {
                        return arrData[k][xOrY];
                    } else if (curValue == 0) {
                        continue;
                    } else return null;
                }
                break;
        }
        return null;
    }

    //var=0 如0030 返回第一不为0的索引，如0000返回-1
    private Cell findNextNubmerZero(int startIndex, int xOrY, @GameDirection int direction) {
        switch (direction) {
            case GAME_DIRECTION_LEFT:// <- toleft
                for (int j = startIndex + 1; j < xCOUNT; j++) {
                    if (!arrData[xOrY][j].isValueZero()) {
                        return arrData[xOrY][j];
                    }
                }
                break;
            case GAME_DIRECTION_RIGHT://->toright
                for (int j = startIndex - 1; j >= 0; j--) {
                    if (!arrData[xOrY][j].isValueZero()) {
                        return arrData[xOrY][j];
                    }
                }
                break;
            case GAME_DIRECTION_UP://
                for (int k = startIndex + 1; k < yCOUNT; k++) {
                    if (!arrData[k][xOrY].isValueZero()) {
                        return arrData[k][xOrY];
                    }
                }
                break;
            case GAME_DIRECTION_DOWN:
                for (int k = startIndex - 1; k >= 0; k--) {
                    if (!arrData[k][xOrY].isValueZero()) {
                        return arrData[k][xOrY];
                    }
                }
                break;
        }
        return null;
    }

    public boolean push(@GameDirection int direction) {

        if (gameData.isGameOver()) {
            linstener.onGameOver();
            return false;
        }
        GameData gameDataClone = (GameData) gameData.clone();

        animBoard.clearAllAnimation();

        boolean isMoved = false;
        int currentScore = 0;
        Cell cellFaraway;
        //添加方向计数
        gameData.addDirectionCount(direction);
        switch (direction) {
            case GAME_DIRECTION_LEFT:

                for (int i = 0; i < yCOUNT; i++) {
                    for (int j = 0; j < xCOUNT; j++) {
                        Cell cell = arrData[i][j];
                        if (cell.isValueZero()) {
                            //需要交换的cell
                            cellFaraway = findNextNubmerZero(j, i, direction);
                            if (cellFaraway != null) {//0020 ->变化之后就是2000
                                addCellAnimation(cellFaraway, cell, false);
                                cell.exChangeValue(cellFaraway);
                                isMoved = true;
                            } else {//说明后是0，则进入下一重循环
                                break;
                            }
                        }
                    }
                    for (int j = 0; j < xCOUNT; j++) {
                        Cell cell = arrData[i][j];
                        if (!cell.isValueZero()) {//2021   ->4001 0202->2002 ->2200 ->4000  0222->2022->2202->2220 ->add 4020 4200
                            cellFaraway = findNextTheSame(cell.getValue(), j, i, direction);
                            if (cellFaraway != null) {
                                addCellAnimation(cellFaraway, cell, true);
                                currentScore += cell.addValue();//近处数字X2
                                cellFaraway.setValue(0);//远处值设置为0
                                isMoved = true;
                                j++;
                            }
                        }
                    }
                    for (int j = 0; j < xCOUNT; j++) {
                        Cell cell = arrData[i][j];
                        if (cell.isValueZero()) {
                            //需要交换的cell
                            cellFaraway = findNextNubmerZero(j, i, direction);
                            if (cellFaraway != null) {//0020 ->变化之后就是2000
                                addCellAnimation(cellFaraway, cell, false);
                                cell.exChangeValue(cellFaraway);
                                isMoved = true;
                            } else {//说明后是0，则进入下一重循环
                                break;
                            }
                        }
                    }
                }
                break;

            case GAME_DIRECTION_RIGHT:
                for (int i = 0; i < yCOUNT; i++) {
                    for (int j = xCOUNT - 1; j >= 0; j--) {
                        Cell cell = arrData[i][j];
                        if (cell.isValueZero()) {
                            //需要交换的cell
                            cellFaraway = findNextNubmerZero(j, i, direction);
                            if (cellFaraway != null) {//0020 ->变化之后就是2000
                                addCellAnimation(cellFaraway, cell, false);
                                cell.exChangeValue(cellFaraway);
                                isMoved = true;
                            } else {//说明后是0，则进入下一重循环
                                break;
                            }
                        }
                    }
                    for (int j = xCOUNT - 1; j >= 0; j--) {
                        Cell cell = arrData[i][j];
                        if (!cell.isValueZero()) {//2021   ->4001 0202->2002 ->2200 ->4000  0222->2022->2202->2220 ->add 4020 4200
                            cellFaraway = findNextTheSame(cell.getValue(), j, i, direction);
                            if (cellFaraway != null) {
                                addCellAnimation(cellFaraway, cell, true);
                                currentScore += cell.addValue();//近处数字X2
                                cellFaraway.setValue(0);//远处值设置为0
                                isMoved = true;
                                j--;
                            }
                        }
                    }
                    for (int j = xCOUNT - 1; j >= 0; j--) {
                        Cell cell = arrData[i][j];
                        if (cell.isValueZero()) {
                            //需要交换的cell
                            cellFaraway = findNextNubmerZero(j, i, direction);
                            if (cellFaraway != null) {//0020 ->变化之后就是2000
                                addCellAnimation(cellFaraway, cell, false);
                                cell.exChangeValue(cellFaraway);
                                isMoved = true;
                            } else {//说明后是0，则进入下一重循环
                                break;
                            }
                        }
                    }
                }
                break;
            case GAME_DIRECTION_UP:
                //left zero
                for (int j = 0; j < xCOUNT; j++) {
                    for (int i = 0; i < yCOUNT; i++) {
                        Cell cell = arrData[i][j];
                        if (cell.isValueZero()) {
                            //需要交换的cell
                            cellFaraway = findNextNubmerZero(i, j, direction);
                            if (cellFaraway != null) {//0020 ->变化之后就是2000
                                addCellAnimation(cellFaraway, cell, false);
                                cell.exChangeValue(cellFaraway);
                                isMoved = true;
                            } else {//说明后是0，则进入下一重循环
                                break;
                            }
                        }
                    }
                    for (int i = 0; i < yCOUNT; i++) {
                        Cell cell = arrData[i][j];
                        if (!cell.isValueZero()) {//2021   ->4001 0202->2002 ->2200 ->4000  0222->2022->2202->2220 ->add 4020 4200
                            cellFaraway = findNextTheSame(cell.getValue(), i, j, direction);
                            if (cellFaraway != null) {
                                addCellAnimation(cellFaraway, cell, true);
                                currentScore += cell.addValue();//近处数字X2
                                cellFaraway.setValue(0);//远处值设置为0
                                isMoved = true;
                                i++;
                            }
                        }
                    }
                    for (int i = 0; i < yCOUNT; i++) {
                        Cell cell = arrData[i][j];
                        if (cell.isValueZero()) {
                            //需要交换的cell
                            cellFaraway = findNextNubmerZero(i, j, direction);
                            if (cellFaraway != null) {//0020 ->变化之后就是2000
                                addCellAnimation(cellFaraway, cell, false);
                                cell.exChangeValue(cellFaraway);
                                isMoved = true;
                            } else {//说明后是0，则进入下一重循环
                                break;
                            }
                        }
                    }
                }

                break;


            case GAME_DIRECTION_DOWN:
                //left zero
                for (int j = 0; j < xCOUNT; j++) {
                    for (int i = yCOUNT - 1; i >= 0; i--) {
                        Cell cell = arrData[i][j];
                        if (cell.isValueZero()) {
                            //需要交换的cell
                            cellFaraway = findNextNubmerZero(i, j, direction);
                            if (cellFaraway != null) {//0020 ->变化之后就是2000
                                addCellAnimation(cellFaraway, cell, false);
                                cell.exChangeValue(cellFaraway);
                                isMoved = true;
                            } else {//说明后是0，则进入下一重循环
                                break;
                            }
                        }
                    }
                    for (int i = yCOUNT - 1; i >= 0; i--) {
                        Cell cell = arrData[i][j];
                        if (!cell.isValueZero()) {//2021   ->4001 0202->2002 ->2200 ->4000  0222->2022->2202->2220 ->add 4020 4200
                            cellFaraway = findNextTheSame(cell.getValue(), i, j, direction);
                            if (cellFaraway != null) {
                                addCellAnimation(cellFaraway, cell, true);
                                currentScore += cell.addValue();//近处数字X2
                                cellFaraway.setValue(0);//远处值设置为0
                                isMoved = true;
                                i--;
                            }
                        }
                    }
                    for (int i = yCOUNT - 1; i >= 0; i--) {
                        Cell cell = arrData[i][j];
                        if (cell.isValueZero()) {
                            //需要交换的cell
                            cellFaraway = findNextNubmerZero(i, j, direction);
                            if (cellFaraway != null) {//0020 ->变化之后就是2000
                                addCellAnimation(cellFaraway, cell, false);
                                cell.exChangeValue(cellFaraway);
                                isMoved = true;
                            } else {//说明后是0，则进入下一重循环
                                break;
                            }
                        }
                    }
                }
                break;
        }
        if (isMoved) {
            //最多撤销10步
            if (stacks.size() >= stacks.capacity()) {
                stacks.remove(0);
            }
            stacks.push(gameDataClone);
            if (currentScore != 0) {
                gameData.addScore(currentScore);
                gameData.setScoreHigh();
                if (linstener != null)
                    linstener.onGameScoreChanged(currentScore, gameData.getScoreCur(), gameData.getScoreHigh());
            }
            gameData.addStep();
            if (linstener != null)
                linstener.onGameStepChanged(gameData.getStep());
            createNewTile();
        }
        return isMoved;

    }

    /***
     * @param move     移动的动画
     * @param to       终点动画
     * @param isHeBing 是否需要合并
     */
    private void addCellAnimation(Cell move, Cell to, boolean isHeBing) {
        if (isHeBing)
            animBoard.getAnimManager(to).addHeibingAnim(to.getValue());
        animBoard.getAnimManager(to).addMoveAnim(move, move.getValue());
    }


    private boolean isGameOver() {
        return hasEmptyData() == false && isTwoNumSame() == false;
    }

    /***
     * 有为0的数据
     *
     * @return
     */
    private boolean hasEmptyData() {
        for (int i = 0; i < yCOUNT; i++) {
            for (int j = 0; j < xCOUNT; j++) {
                if (arrData[i][j].isValueZero()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 相邻的两个数值相同
     *
     * @return
     */
    private boolean isTwoNumSame() {
        for (int i = 0; i < yCOUNT; i++) {
            for (int j = 0; j < xCOUNT - 1; j++) {
                if (arrData[i][j].equals(arrData[i][j + 1])) {
                    return true;
                }
            }
        }
        for (int j = 0; j < xCOUNT; j++) {
            for (int i = 0; i < yCOUNT - 1; i++) {
                if (arrData[i][j].equals(arrData[i + 1][j])) {
                    return true;
                }
            }
        }
        return false;
    }


    private List<Cell> getListCells() {
        List<Cell> list = new ArrayList<>();
        for (int i = 0; i < yCOUNT; i++) {
            for (int j = 0; j < xCOUNT; j++) {
                Cell cell = gameData.arrData[i][j];
                if (cell.getValue() == 0) {
                    list.add(cell);
                }
            }
        }
        return list;
    }


    public void save() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(App.getContext());
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(KEY_CACHE_TEMP, JSON.toJSONString(gameData));
        editor.putString(KEY_CACHE_STACK, JSON.toJSONString(stacks.toArray()));
        editor.commit();
    }

    private GameData readData() {
        GameData data = null;
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(App.getContext());
        String string = settings.getString(KEY_CACHE_TEMP, null);
        String stringStack = settings.getString(KEY_CACHE_STACK, null);
        if (string != null) {
            try {
                data = JSON.parseObject(string, GameData.class);
                List<GameData> list = JSON.parseArray(stringStack, GameData.class);
                //请空数据
                stacks.clear();
                //新加进来
                stacks.addAll(list);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    //左右转置
    public void leftRightZhuanzhi() {
        for (int i = 0; i < yCOUNT; i++) {
            for (int j = 0; j < xCOUNT / 2; j++) {
                arrData[i][j].exChangeValue(arrData[i][xCOUNT - j - 1]);
            }
        }
    }

    //上下转置
    public void upDownZhuanzhi() {
        for (int j = 0; j < xCOUNT; j++)
            for (int i = 0; i < yCOUNT / 2; i++) {
                {
                    arrData[i][j].exChangeValue(arrData[yCOUNT - i - 1][j]);
                }
            }
    }


    //XY转置
    public void xyZhuangzhi() {
        for (int j = 0; j < xCOUNT; j++)
            for (int i = 0; i < yCOUNT; i++) {
                {
                    if (i < j) {
                        arrData[i][j].exChangeValue(arrData[j][i]);
                    }
                }
            }
    }

    /***
     * 全部转置
     */
    public void allZhuanzhi() {
        leftRightZhuanzhi();
        upDownZhuanzhi();
        xyZhuangzhi();
    }

    public void read() {
        GameData data = readData();
        if (data != null) {
            //设置读到的数据
            setGameData(data);
        } else {
            //新游戏
            newGame();
        }
    }


    public boolean isStackEmpty() {
        return stacks.empty();
    }

    public void undo() {
        if (isStackEmpty() == false) {
            animBoard.clearAllAnimation();
            GameData gameData = stacks.pop();
            setGameData(gameData);
        }
    }

    public GameData getGameData() {
        return gameData;
    }

    public final static String KEY_CACHE_TEMP = "gamelogic_2048";
    public final static String KEY_CACHE_STACK = "gamelogicstack_2048";
}
