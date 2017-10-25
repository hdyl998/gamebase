package com.hdyl.mine.stage;

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

    static {
        lists = new ArrayList<>();
        for (int i = 100; i < 10000; i += 100) {
            StageItem item = new StageItem();
            item.setMineRate(0.25f);//设定成固定值
            item.calcData(i);
            lists.add(item);
        }
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


        public void calcData(int total) {
            List<Integer> lists = fenjie(total);
            
        }

        public List<Integer> fenjie(int n) {
            List<Integer> lists = new ArrayList<>();
            for (int i = 2; i <= n; i++) {
                while (n != i) {
                    if (n % i == 0) {
                        n = n / i;
                        lists.add(i);
                    } else break;
                }
            }
            lists.add(n);
            Collections.sort(lists);
            return lists;
        }
    }
}
