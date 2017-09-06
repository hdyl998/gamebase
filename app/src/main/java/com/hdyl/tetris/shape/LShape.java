package com.hdyl.tetris.shape;

/**
 * Created by liugd on 2017/5/5.
 */
public class LShape extends TetrisShape {

    @Override
    public String[] getCellsDatas() {
        return new String[]{
                "010,010,011",
                "000,111,100",
                "11,01,01",
                "001,111"
        };
    }
}
