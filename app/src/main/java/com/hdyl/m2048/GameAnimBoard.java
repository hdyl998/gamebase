package com.hdyl.m2048;


import static com.hdyl.m2048.GameLogic.xCOUNT;
import static com.hdyl.m2048.GameLogic.yCOUNT;

/**
 * Created by liugd on 2017/4/26.
 */

public class GameAnimBoard {


    private AnimManager animManagers[][];


    public GameAnimBoard() {
        //初始化
        animManagers = new AnimManager[yCOUNT][xCOUNT];
        for (int i = 0; i < yCOUNT; i++) {
            for (int j = 0; j < xCOUNT; j++) {
                animManagers[i][j] = new AnimManager();
            }
        }
    }

    public AnimManager[][] getAnimManagers() {
        return animManagers;
    }

    public void addAnimManager(AnimCell anim, int x, int y) {
        animManagers[y][x].getListAnims().add(anim);
    }


    public AnimManager getAnimManager(int x, int y) {
        return animManagers[y][x];
    }

    public AnimManager getAnimManager(Cell cell) {
        return animManagers[cell.getY()][cell.getX()];
    }

    public void tickerAll(int costTime) {
        for (AnimManager[] array : animManagers) {
            for (AnimManager manager : array) {
                manager.ticker(costTime);
            }
        }
    }

    public void clearAllAnimation() {
        for (AnimManager[] array : animManagers) {
            for (AnimManager manager : array) {
                manager.clearAnimation();
            }
        }
    }

}
