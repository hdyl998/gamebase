package com.hdyl.baselib.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.hdyl.baselib.base.App;


public class SpUtils {

    public static String getString(String fileName, String key) {
        return getString(fileName, key, null);
    }

    public static String getString(String fileName, String key, String defaultValue) {
        SharedPreferences preferences = App.getContext().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return preferences.getString(key, defaultValue);
    }


    public static int getInt(String fileName, String key) {
        return getInt(fileName, key, 0);
    }

    public static int getInt(String fileName, String key, int defaultvalue) {
        SharedPreferences preferences = App.getContext().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return preferences.getInt(key, defaultvalue);
    }


    public static long getLong(String fileName, String key) {
        SharedPreferences preferences = App.getContext().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return preferences.getLong(key, 0);
    }


    public static float getFloat(String fileName, String key) {
        return getFloat(fileName, key, 0f);
    }

    public static float getFloat(String fileName, String key, float defaultvalue) {
        SharedPreferences preferences = App.getContext().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return preferences.getFloat(key, defaultvalue);
    }


    public static boolean getBoolean(String fileName, String key) {
        return getBoolean(fileName, key, false);
    }


    public static boolean getBoolean(String fileName, String key, boolean flag) {
        SharedPreferences preferences = App.getContext().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return preferences.getBoolean(key, flag);
    }


    public static void putString(String fileName, String key, String value) {
        SharedPreferences preferences = App.getContext().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }


    public static void putInt(String fileName, String key, int value) {
        SharedPreferences preferences = App.getContext().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }


    public static void putLong(String fileName, String key, long value) {
        SharedPreferences preferences = App.getContext().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }


    public static void putFloat(String fileName, String key, float value) {
        SharedPreferences preferences = App.getContext().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putFloat(key, value);
        editor.commit();
    }

    public static void putBoolean(String fileName, String key, boolean value) {
        SharedPreferences preferences = App.getContext().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

}
