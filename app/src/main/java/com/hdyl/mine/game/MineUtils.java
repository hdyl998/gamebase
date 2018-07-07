package com.hdyl.mine.game;

/**
 * Created by Administrator on 2018/7/8.
 */

public class MineUtils {

    public final static int[] checkCorrectUserDefineMineNum(int width, int height, int num) {
        if (width < 5) {
            width = 5;
        }
        if (width > 200) {
            width = 200;
        }
        if (height < 5) {
            height = 5;
        }
        if (height > 200) {
            height = 200;
        }

        if (num < width * height * 0.02) {
            num = (int) (width * height * 0.02);
            if (num < 3) {
                num = 3;
            }
        } else if (num > width * height * 0.5) {
            num = (int) (width * height * 0.5);
        }
        if (width * height - 9 <= num) {
            num = width * height - 9;
        }
        return new int[]{width, height, num};
    }
}
