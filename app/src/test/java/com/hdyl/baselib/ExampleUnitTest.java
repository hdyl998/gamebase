package com.hdyl.baselib;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {

//        System.out.println(Integer.toBinaryString(VAR_OPEN));
//        System.out.println(Integer.toBinaryString(VAR_COVER));
//        System.out.println(Integer.toBinaryString(VAR_FLAG));
//
//        System.out.println((VAR_COVER & VAR_FLAG) == VAR_FLAG);


        //0xE001,0xE05A

        QiItem qiItem = new QiItem(QiItem.ITEM_TYPE_BANG, QiItem.USER_TYPE_ONE);

        QiItem qiItem2 = new QiItem(QiItem.ITEM_TYPE_JI, QiItem.USER_TYPE_TWO);


        System.out.println(qiItem.eat(qiItem2));


        System.out.println(qiItem2.eat(qiItem));

//        EmojiCharacterUtil.filter("");
    }
}