package com.hdyl.mine.stage;

import com.alibaba.fastjson.JSON;
import com.hdyl.mine.tools.Tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Date:2017/10/25 18:20
 * Author:liugd
 * Modification:
 **/


public class StageList {


    private final static List<StageItem> lists;

    /**
     *
     */
    public static int passIndex = -1;


    public static int getPassIndex() {
        return passIndex;
    }

    static {

        List<StageItem> listTemp = readData();
        if (listTemp == null) {
            listTemp = new ArrayList<>();
            //100关
            for (int i = 0; i < 100; i++) {
                StageItem item = new StageItem();
                item.setMineRate(GameDataCalcTool.getNumF(i, 0.1f, 0.3f, 0, 100));//设定成固定值
                item.calcData((i + 1) * 100);
                listTemp.add(item);
            }
            lists = listTemp;
            saveData();
        } else {
            lists = listTemp;
        }
        calcPassIndex();
    }

    public static void calcPassIndex() {
        int index = -1;
        for (StageItem item : lists) {
            if (!item.isPass) {
                break;
            }
            index++;
        }
        passIndex = index;
    }


    final static String fileName = "mine1.txt";

    /***
     * 读档
     *
     * @return
     */
    private static List<StageItem> readData() {
        List<StageItem> lists = null;
        String string = com.hdyl.llk.utils.Tools.readFileByLines(fileName);
        if (string != null) {
            try {
                lists = JSON.parseArray(string, StageItem.class);
            } catch (Exception e) {
            }
        }
        return lists;
    }

    /***
     * 存档
     */
    public static void saveData() {
        String jsString = JSON.toJSONString(lists);
        com.hdyl.llk.utils.Tools.writeFileByLines(fileName, jsString);
    }


    public static List<StageItem> getLists() {
        return lists;
    }

    public static class StageItem {
        public int width;
        public int height;
        public int mineNum;
        public float mineRate;
        public boolean isPass;
        //        public String info;
        public int solveTime;//解决时间
        public String solveDate;

        public void setMineNum(int mineNum) {
            this.mineNum = mineNum;
        }

        public void setMineRate(float mineRate) {
            this.mineRate = mineRate;
        }

        public void setPass(boolean pass) {
            isPass = pass;
        }

        public boolean isPass() {
            return isPass;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public void setHeight(int height) {
            this.height = height;
        }


        private void calcData(int total) {
            int var = (int) Math.sqrt(total);
            this.height = (int) (var * 1.3);
            this.width = (int) (var * 0.7);
            this.mineNum = (int) (this.width * this.height * this.mineRate);
        }

        @Override
        public String toString() {
            String append = "";
            if (isPass) {
                append = "\nPassed! " + Tools.getPostTime(solveDate);
            }
            return String.format("%dx%d d:%.2f n:%d", width, height, mineRate, mineNum) + append;
        }
    }
}
