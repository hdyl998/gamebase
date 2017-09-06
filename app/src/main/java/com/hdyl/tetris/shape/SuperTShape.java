package com.hdyl.tetris.shape;

/**
 * Created by liugd on 2017/5/5.
 */
public class SuperTShape extends TetrisShape {
    @Override
    public String[] getCellsDatas() {
        return new String[]{
                "001,111,001",
                "010,010,111",
                "100,111,100",
                "111,010,010"
        };
    }
}
