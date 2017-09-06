package com.hdyl.tetris.shape;

/**
 * Created by liugd on 2017/5/5.
 */
public class SuperSShape extends TetrisShape {

    @Override
    public String[] getCellsDatas() {
        return new String[]{
                "011,010,110",
                "100,111,001"
        };
    }
}
