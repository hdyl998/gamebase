package com.hdyl.tetris.shape;

import java.util.Random;

/**
 *
 * Created by liugd on 2017/5/9.
 */

public class TetrisShapeFactory {
    public static Class<TetrisShape>[] listClazz;

    /***
     * 随机创建一种shape
     *
     * @return
     */
    public static TetrisShape createRandomShape() {
        if (listClazz == null) {
            listClazz = new Class[]{
                    IShape.class,
                    JShape.class,
                    LShape.class,
                    OShape.class,
                    SShape.class,
                    TShape.class,
                    ZShape.class,
                   /* PointShape.class,
                    SuperSShape.class,
                    SuperTShape.class,
                    SuperZShape.class*/
            };
        }
        int index = new Random().nextInt(listClazz.length);
        try {
            TetrisShape shape = listClazz[index].newInstance();
            return shape;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
