package com.hdyl.mine.newgame;

import android.content.Context;
import android.content.SharedPreferences;

import com.hdyl.baselib.base.App;

/**
 * Created by Administrator on 2018/7/8.
 */

public class SpCacheUtils {


    private final static Context context = App.getContext();

    public static String getString(String fileName, String key) {
        SharedPreferences preferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return preferences.getString(key, null);
    }

    public static String getString(String fileName, String key, String defaultValue) {
        SharedPreferences preferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return preferences.getString(key, defaultValue);
    }

    public static int getInt(String fileName, String key) {
        SharedPreferences preferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return preferences.getInt(key, 0);
    }


    public static int getInt(String fileName, String key, int number) {
        SharedPreferences preferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return preferences.getInt(key, number);
    }


    public static long getLong(String fileName, String key) {
        SharedPreferences preferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return preferences.getLong(key, 0);
    }


    public static float getFloat(String fileName, String key) {
        SharedPreferences preferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return preferences.getFloat(key, 0);
    }

    public static float getFloat(String fileName, String key, float defaultvalue) {
        SharedPreferences preferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return preferences.getFloat(key, defaultvalue);
    }

    public static boolean getBoolean(String fileName, String key) {
        SharedPreferences preferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return preferences.getBoolean(key, false);
    }

    public static boolean getBoolean(String fileName, String key, boolean flag) {
        SharedPreferences preferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return preferences.getBoolean(key, flag);
    }

    public static void putString(String fileName, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }


    public static void putInt(String fileName, String key, int value) {
        SharedPreferences preferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }


    public static void putLong(String fileName, String key, long value) {
        SharedPreferences preferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public static void putFloat(String fileName, String key, float value) {
        SharedPreferences preferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(key, value);
        editor.commit();
    }


    public static void putBoolean(String fileName, String key, boolean value) {
        SharedPreferences preferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }


}
