package com.hdyl.tetris.common;

import java.util.Random;

/**
 * Created by liugd on 2017/5/9.
 */

public class CommonUtil {

    private static CommonUtil instence = new CommonUtil();

    public static CommonUtil getInstance() {
        return instence;
    }

    private Random random = new Random();

    private int getRandomInt(int maxRange) {
        return random.nextInt(maxRange);
    }

}
