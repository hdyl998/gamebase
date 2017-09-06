package com.hdyl.tetris.shape;

/**
 * Created by liugd on 2017/5/5.
 */
public class JShape extends TetrisShape {

    @Override
    public String[] getCellsDatas() {
        return new String[]{
                "01,01,11",
                "100,111",
                "011,010,010",
                "000,111,001"
        };
    }
}
