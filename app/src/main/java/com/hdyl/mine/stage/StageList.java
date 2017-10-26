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
        //100关
        for (int i = 0; i < 100; i ++) {
            StageItem item = new StageItem();
            item.setMineRate(0.15f);//设定成固定值
            item.calcData((i+1)*100);
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
        public String info;

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
            int var= (int) Math.sqrt(total);
            this.height= (int) (var*1.3);
            this.width= (int) (var*0.7);
            this.mineNum= (int) (this.width*this.height*this.mineRate);
        }

//        public List<Integer> fenjie(int n) {
//            List<Integer> lists = new ArrayList<>();
//            for (int i = 2; i <= n; i++) {
//                while (n != i) {
//                    if (n % i == 0) {
//                        n = n / i;
//                        lists.add(i);
//                    } else break;
//                }
//            }
//            lists.add(n);
//            Collections.sort(lists);
//            return lists;
//        }

        @Override
        public String toString() {
            return String.format("%dx%d d:%.2f n:%d",width,height,mineRate,mineNum);
        }
    }
}
