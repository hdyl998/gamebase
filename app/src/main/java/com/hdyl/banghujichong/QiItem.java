package com.hdyl.banghujichong;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.hdyl.baselib.utils.Tools;
import com.hdyl.baselib.utils.log.LogUitls;

import java.util.Random;

/**
 * <p>Created by liugd on 2018/4/4.<p>
 * <p>佛祖保佑，永无BUG<p>
 */

public class QiItem {
    public final static int ITEM_TYPE_BANG = 0;//棒
    public final static int ITEM_TYPE_HU = 1;//虎
    public final static int ITEM_TYPE_JI = 2;//鸡
    public final static int ITEM_TYPE_CHONG = 3;//虫

    public final static int USER_TYPE_ONE = 0;//用户1
    public final static int USER_TYPE_TWO = 1;//用户2


    public int getUserColor() {
        return userColors[userType];
    }

    public int itemType;
    public int userType = USER_TYPE_ONE;

    /***
     * 是否显示
     */
    public boolean isShow;


    public boolean isShow() {
        return isShow;
    }


    public void toggleShow() {
        isShow = !isShow;
    }

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

        isShow = true;
    }


    public final static String[] arrays = {"棒", "虎", "鸡", "虫"};

    public String getName() {
        return arrays[itemType];
    }

    public boolean isUserTypeOne() {
        return userType == USER_TYPE_ONE;
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getName());
        stringBuilder.append("userType:");
        stringBuilder.append(userType);
        return stringBuilder.substring(0);
    }

    private final static int userColors[] = {0xffDD4A4A, 0xff7777aa};

    public void draw(Canvas canvas, RectF rect, Paint paint) {
        if (isShow()) {
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(getUserColor());
            canvas.drawRoundRect(rect, 10, 10, paint);
            paint.setColor(Color.WHITE);
            canvas.drawText(getName(), rect.centerX(), rect.centerY() + Tools.getFontHeight(paint) / 2, paint);
        } else {//看不见
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.GRAY);
            canvas.drawRoundRect(rect, 10, 10, paint);
        }

    }


}
