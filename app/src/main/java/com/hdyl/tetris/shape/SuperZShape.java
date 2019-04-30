package com.hdyl.tetris.shape;

/**
 * Created by liugd on 2017/5/5.
 */
public class SuperZShape extends TetrisShape {

    @Override
    public String[] getCellsDatas() {
        return new String[]{
                "110,010,011",
                "001,111,100"
        };
    }

}
