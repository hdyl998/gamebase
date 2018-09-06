package com.hdyl.banghujichong;

/**
 * <p>Created by liugd on 2018/4/4.<p>
 * <p>佛祖保佑，永无BUG<p>
 */

public class BhjcLogic {

    int WIDTH;
    int HEIGHT;

    public BhjcLogic() {
        this(4, 6);
    }


    public BhjcLogic(int width, int height) {
        WIDTH = width;
        HEIGHT = height;
        createQiItems();
    }

    public QiItem[][] qiItems;


    private void createQiItems() {
        qiItems = new QiItem[HEIGHT][WIDTH];
    }

}
