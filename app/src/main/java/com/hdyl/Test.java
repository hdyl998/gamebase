package com.hdyl;

import com.hdyl.mine.newgame.MineCellUtils;
import com.hdyl.mine.newgame.MineItem;

public class Test {
    public static void main(String[] args) {



        MineItem item=new MineItem();


        item.printCoverInfo();
        item.toggleCover();
        item.printCoverInfo();
        item.toggleCover();
        item.printCoverInfo();
        item.toggleCover();

        item.printCoverInfo();

        item.toggleCover();
        item.printCoverInfo();
        item.toggleCover();
        item.printCoverInfo();
        item.toggleCover();

        item.printCoverInfo();

        System.out.println("isError"+item.isError());

        item.setError();

        System.out.println("isError"+item.isError());
    }
}
