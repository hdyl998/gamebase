package com.hdyl.tetris.shape;

/**
 * Created by liugd on 2017/5/5.
 */
public class ZShape extends TetrisShape {

    @Override
    public String[] getCellsDatas() {
        return new String[]{
                "110,011",
                "01,11,10"
        };
    }
}
