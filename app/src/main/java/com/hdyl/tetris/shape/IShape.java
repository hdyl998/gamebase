package com.hdyl.tetris.shape;

/**
 * Created by liugd on 2017/5/5.
 */
public class IShape extends TetrisShape {

    @Override
    public String[] getCellsDatas() {
        return new String[]{
                "0000,1111", "01,01,01,01"
        };
    }
}
