package com.hdyl.mine.stage;

/**
 * Date:2017/10/27 16:08
 * Author:liugd
 * Modification:
 **/


public class GameDataCalcTool {

    public static int getNum(int curlevel, float minNum, float maxNum, int startLev, int endLev) {
        if (curlevel >= endLev) {
            return (int) maxNum;
        }
        if (curlevel <= startLev) {
            return (int) minNum;
        }
        return (int) (minNum + (maxNum - minNum) * (curlevel - startLev) * 1f / (endLev - startLev));
    }

    public static float getNumF(int curlevel, float minNum, float maxNum, int startLev, int endLev) {
        if (curlevel >= endLev) {
            return maxNum;
        }
        if (curlevel <= startLev) {
            return minNum;
        }
        return (minNum + (maxNum - minNum) * (curlevel - startLev) * 1f / (endLev - startLev));
    }
}
