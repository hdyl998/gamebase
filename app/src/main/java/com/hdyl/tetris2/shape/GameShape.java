package com.hdyl.tetris2.shape;


import com.hdyl.tetris2.GameColor;

/**
 * Created by liugd on 2017/7/25.
 */

public class GameShape {
    public byte resIndex;//资源编号

    public Cell arr[][];

    private GameShape() {
        setResIndex(GameColor.getRandomResIndex());
        String str = AllShapes.getRandomShape();
        String arrs[] = str.split(",");
        arr = new Cell[arrs.length][arrs[0].length()];
        for (int i = 0; i < arrs.length; i++) {
            String tt = arrs[i];
            for (int j = 0; j < tt.length(); j++) {
                Cell cell = new Cell();
                arr[i][j] = cell;
                cell.setValue((byte) (tt.charAt(j) - '0'));
                cell.setColor(resIndex);
            }
        }
    }

    public Cell[][] getArr() {
        return arr;
    }

    public void setResIndex(byte resIndex) {
        this.resIndex = resIndex;
    }

    /***
     * 随机产生方块
     *
     * @return
     */
    public static GameShape createRandomShape() {
        return new GameShape();
    }


}
