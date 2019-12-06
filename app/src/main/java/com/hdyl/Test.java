package com.hdyl;

import com.hdyl.mine.newgame.MineCellUtils;
import com.hdyl.mine.newgame.MineItem;

public class Test {
    public static void main(String[] args) {


        boolean b=false;
        Object obj=b;
        System.out.println(obj.getClass()==Boolean.class);

        obj=11L;
        System.out.println(obj.getClass());
        System.out.println(obj.getClass()==long.class);
    }
}
