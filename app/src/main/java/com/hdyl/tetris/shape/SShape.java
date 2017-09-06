package com.hdyl.tetris.shape;

/**
 * Created by liugd on 2017/5/5.
 */
public class SShape extends TetrisShape {

    @Override
    public String[] getCellsDatas() {
        return new String[]{
                "011,110",
                "10,11,01"
        };
    }
}
