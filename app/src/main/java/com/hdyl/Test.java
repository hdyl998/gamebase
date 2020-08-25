package com.hdyl;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.View;

import com.hdyl.mine.R;
import com.hdyl.mine.newgame.MineCellUtils;
import com.hdyl.mine.newgame.MineItem;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

public class Test {
    public static void main(String[] args) {


        String path="C:\\Users\\HanDong\\Desktop\\新建文件夹";

        File file=new File(path);

//        for (File ff : file.listFiles()) {
//            String name=ff.getName();
//            ff.renameTo(new File(path,"ss_"+name));
//        }

        System.out.println("{");
        for (File ff : file.listFiles()) {

            System.out.println("R.drawable."+ff.getName().substring(0,ff.getName().length()-4)+",");
        }

        System.out.println("}");
//        System.out.println(Integer.toBinaryString(16842919));
//
//        System.out.println(Integer.MAX_VALUE >> 2);
//
//        System.out.println(Integer.MAX_VALUE);
//        System.out.println(Integer.MAX_VALUE/4);
//
//        System.out.println(Integer.toBinaryString(fun(Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST)));
//
//        System.out.println(Integer.toBinaryString(MODE_MASK));
//        System.out.println(Integer.toBinaryString(View.MeasureSpec.AT_MOST & MODE_MASK));
//
//        System.out.println(Integer.toBinaryString(Integer.MAX_VALUE));
//        System.out.println(Integer.toBinaryString(0xffffffff));
//        System.out.println(0xffffffff);
//        System.out.println(Integer.toBinaryString(-1));
//        System.out.println(Long.toBinaryString(-1));
//        System.out.println(Long.toBinaryString(-2));
//        System.out.println(Long.toBinaryString(-3));
//        System.out.println();

    }
    private static final int MODE_SHIFT = 30;
    private static final int MODE_MASK  = 0x3 << MODE_SHIFT;
    public static int fun(int size,int mode){
        return (size & ~MODE_MASK) | (mode & MODE_MASK);
    }

//    private StateListDrawable getBtnBg() {
//        ColorDrawable normalColor = new ColorDrawable(this.mConfig.mCancelBtnBgColor);
//        ColorDrawable pressedColor = new ColorDrawable(this.mConfig.mCancelBtnBgPressedColor);
//        StateListDrawable bg = new StateListDrawable();
//        int pressed = 16842919;
//        bg.addState(new int[]{pressed}, pressedColor);
//        bg.addState(new int[0], normalColor);
//        return bg;
//    }
}
