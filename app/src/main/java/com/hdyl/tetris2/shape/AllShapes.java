package com.hdyl.tetris2.shape;

import java.util.Random;

/**
 * Created by liugd on 2017/8/3.
 */

public class AllShapes {
    public final static String[] shapes = new String[]{
            "1",
            "1111",
            "1,1,1,1",
            "11,11",
            "110,011",
            "011,110",
            "10,11,01",
            "01,11,10",
            "10,10,11",//L
            "01,01,11",//J
            "11,10,10",//
            "11,01,01",
            "100,111",
            "001,111",
            "111,100",
            "111,001",
            "11",
            "111",
            "1,1",
            "1,1,1",
            "11,10",
            "11,01",
            "01,11",
            "10,11",

            "111,010",
            "010,111",
            "10,11,10",
            "01,11,01",

            "10,01",
            "01,10"
    };

    public static String getRandomShape() {
        Random random = new Random();
        return shapes[random.nextInt(shapes.length)];
    }

}
