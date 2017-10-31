package com.hdyl.llk.utils;

/**
 * Date:2017/10/30 11:08
 * Author:liugd
 * Modification:
 **/


public interface IDataCache {

    String fileName = "寒冬娱乐";

    void put(String key, String value);

    String get(String key);



}
