package com.hdyl.m2048;

import android.support.annotation.IntDef;

import com.hdyl.baselib.utils.log.LogUitls;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.hdyl.m2048.GameLogic.xCOUNT;
import static com.hdyl.m2048.GameLogic.yCOUNT;


/**
 * Created by Administrator on 2017/4/16.
 */

public class GameData implements Cloneable {
    public Cell[][] arrData = new Cell[yCOUNT][xCOUNT];
    public int gameState;//游戏状态0开始，1结束,2赢了
    public int scoreCur;//当前得分
    public int step;//步数
    public long time;//游戏时长，每玩一秒加一次


    public int scoreHigh;//最高得分
    public int num2Count;//2的数量
    public int num4Count;//4的数量
    public int leftCount;//有效移动左的数量
    public int rightCount;//有效移动右的数量
    public int upCount;//有效移动上的数量
    public int downCount;//有效移动下的数量

    @Override
    protected Object clone() {
        try {
            GameData newData = (GameData) super.clone();
            newData.arrData = new Cell[yCOUNT][xCOUNT];
            for (int i = 0; i < yCOUNT; i++) {
                for (int j = 0; j < xCOUNT; j++) {
                    Cell cell = new Cell();
                    cell.setX(j);
                    cell.setY(i);
                    newData.arrData[i][j] = cell;
                    cell.setValue(arrData[i][j].getValue());
                }
            }
            return newData;
        } catch (Exception e) {
            return null;
        }

    }

    //    public List<PlayTime>
//
//    public static class PlayTime{
//        public long startTime;
//        public long endTime;
//    }
    @IntDef({GAME_STATE_START, GAME_STATE_OVER, GAME_STATE_WIN})
    @Retention(RetentionPolicy.SOURCE)
    private @interface GameStateMode {
    }

    public final static int GAME_STATE_START = 0;
    public final static int GAME_STATE_OVER = 1;
    public final static int GAME_STATE_WIN = 2;


    public int getGameState() {
        return gameState;
    }

    public boolean isGameOver() {
        return gameState == GAME_STATE_OVER;
    }

    public void setGameState(@GameStateMode int gameState) {
        this.gameState = gameState;
    }

    public int getScoreCur() {
        return scoreCur;
    }

    public void setScoreCur(int scoreCur) {
        this.scoreCur = scoreCur;
    }

    public void addScore(int addScore) {
        setScoreCur(addScore + scoreCur);
    }


    public int getScoreHigh() {
        return scoreHigh;
    }

    public void setScoreHigh() {
        if (scoreCur > scoreHigh)
            this.scoreHigh = scoreCur;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public void addStep() {
        this.step++;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }


    public void init(int gameInitVar) {
        arrData = new Cell[yCOUNT][xCOUNT];
        for (int i = 0; i < yCOUNT; i++) {
            for (int j = 0; j < xCOUNT; j++) {
                Cell cell = new Cell();
                cell.setX(j);
                cell.setY(i);
                cell.setValue(gameInitVar);
                arrData[i][j] = cell;
            }
        }
        LogUitls.print("cell","创建所有的Cell");
        gameState = GameData.GAME_STATE_START;
        scoreCur = 0;
        step = 0;
        time = 0;

        //scoreHigh最高分不清零

        time = 0;//游戏时长，每玩一秒加一次
        num2Count = 0;//2的数量
        num4Count = 0;//4的数量
        leftCount = 0;//有效移动左的数量
        rightCount = 0;//有效移动右的数量
        upCount = 0;//有效移动上的数量
        downCount = 0;//有效移动下的数量
    }

    //得到技术统计的string
    public String getCountString() {
        int total24Count = num2Count + num4Count;
        int totalDirCount = leftCount + rightCount + upCount + downCount;

        return "2出现的次数：" + num2Count
                + "\n占比为：" + 100f * num2Count / total24Count + "%"
                + "\n4出现的次数：" + num4Count
                + "\n占比为：" + 100f * num4Count / total24Count + "%"
                + "\n左：" + leftCount
                + "占比为：" + 100f * leftCount / totalDirCount + "%"
                + "\n右：" + rightCount
                + "占比为：" + 100f * rightCount / totalDirCount + "%"
                + "\n上：" + upCount
                + "占比为：" + 100f * upCount / totalDirCount + "%"
                + "\n下：" + downCount
                + "占比为：" + 100f * downCount / totalDirCount + "%";
    }

    public void addNum2Count() {
        num2Count++;
    }

    public void addNum4Count() {
        num4Count++;
    }

    public void addTime() {
        this.time++;
    }


    public void addDirectionCount(int direction) {
        switch (direction) {
            case GameLogic.GAME_DIRECTION_LEFT:
                leftCount++;
                break;
            case  GameLogic.GAME_DIRECTION_DOWN:
                downCount++;
                break;
            case  GameLogic.GAME_DIRECTION_RIGHT:
                rightCount++;
                break;
            case  GameLogic.GAME_DIRECTION_UP:
                upCount++;
                break;
        }
    }
}
