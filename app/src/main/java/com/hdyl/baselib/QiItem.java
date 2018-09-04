package com.hdyl.baselib;

/**
 * <p>Created by liugd on 2018/4/4.<p>
 * <p>佛祖保佑，永无BUG<p>
 */

public class QiItem {
    public final static int ITEM_TYPE_BANG = 0;//
    public final static int ITEM_TYPE_HU = 1;//
    public final static int ITEM_TYPE_JI = 2;//
    public final static int ITEM_TYPE_CHONG = 3;//

    public final static int USER_TYPE_ONE = 0;//
    public final static int USER_TYPE_TWO = 1;//


    public int itemType;
    public int userType = USER_TYPE_ONE;


    /***
     *棒虎鸡虫吞食逻辑
     * @param newItem
     * @return
     */
    public boolean eat(QiItem newItem) {
        System.out.println(this + "" + newItem);

        if (newItem == null) {
            return false;
        }
        if (this.userType == newItem.userType) {
            return false;
        }
        int cha = itemType - newItem.itemType;
        System.out.println(cha);
        if (cha == -1 || cha == 3) {
            return true;
        }
        return false;

    }

    public QiItem(int itemType, int userType) {
        this.itemType = itemType;
        this.userType = userType;
    }


    public final static String[] arrays = {"棒", "虎", "鸡", "虫"};

    public String getName() {
        return arrays[itemType];
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getName());
        stringBuilder.append("userType:");
        stringBuilder.append(userType);
        return stringBuilder.substring(0);
    }
}
