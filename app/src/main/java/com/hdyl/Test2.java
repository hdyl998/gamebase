package com.hdyl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Test2 {


    static class LevelInfo {
        double rate;//提升倍率
        int cardTotal;
        int cardCurrent;
        int level;
        int times;//倍率

        public LevelInfo(String data) {
            String[] arr = data.split(",");
            level = Integer.parseInt(arr[0]);
            times = Integer.parseInt(arr[1]);
            rate = Double.parseDouble(arr[2]);
            cardCurrent = Integer.parseInt(arr[3]);
            cardTotal = Integer.parseInt(arr[4]);
        }

        public String getInfo(double earn) {
            double result = earn * (1f * rate - 1);
            return String.format("%d 级建筑升级，扩建%d,收益为%.2f，收益比为%.0f，总收益比为%.0f",
                    level-1,cardCurrent,result, result / cardCurrent*100000,
                    result / cardTotal*100000);
        }

    }


    public final static HashMap<Integer, LevelInfo> hashMap = new HashMap<>();

    static {
        List<String> listItems = new ArrayList<>();
        listItems.add("2,10,10,10,10");
        listItems.add("3,50,5,14,24");
        listItems.add("4,200,4,16,40");
        listItems.add("5,600,3,20,60");
        listItems.add("6,1500,2.5,40,100");
        listItems.add("7,3000,2,52,152");
        listItems.add("8,6000,2,64,216");
        listItems.add("9,12000,2,81,297");
        listItems.add("10,24000,2,108,405");
        listItems.add("11,40000,1.66666667,150,555");
        listItems.add("12,56000,1.4,240,795");
        listItems.add("13,72000,1.285714286,360,1155");
        listItems.add("14,88000,1.2222222,510,1665");
        listItems.add("15,104000,1.181818182,690,2355");
        listItems.add("16,120000,1.153846154,900,3255");


        for (String listItem : listItems) {
            LevelInfo levelInfo = new LevelInfo(listItem);
            hashMap.put(levelInfo.level, levelInfo);
        }
    }


    private static void printInfo(int level, double earn) {
        LevelInfo info = hashMap.get(level+1);
        System.out.println(info.getInfo(earn));
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        printInfo(14,166.1);
        printInfo(14,152.7);
        printInfo(13,110.5);
        printInfo(13,99.6);
        printInfo(12,53);
        printInfo(10,15.3);
        printInfo(6,0.5081);
    }


}
