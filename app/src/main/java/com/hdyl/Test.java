package com.hdyl;

import com.hdyl.mine.newgame.MineCellUtils;

public class Test {
    public static void main(String[] args) {
        int var=MineCellUtils.COVER_TYPE_COVER;

        System.out.println(Integer.toBinaryString(var=MineCellUtils.toggleCover(var)));
        System.out.println(Integer.toBinaryString(var=MineCellUtils.toggleCover(var)));
        System.out.println(Integer.toBinaryString(var=MineCellUtils.toggleCover(var)));
        System.out.println(Integer.toBinaryString(var=MineCellUtils.toggleCover(var)));

    }
}
