package com.hdyl.tetris.shape;

/**
 * Created by liugd on 2017/5/5.
 */
public class TShape extends TetrisShape {
    @Override
    public String[] getCellsDatas() {
        return new String[]{
                "010,111",
                "010,011,010",
                "000,111,010",
                "01,11,01"
        };
    }
}
