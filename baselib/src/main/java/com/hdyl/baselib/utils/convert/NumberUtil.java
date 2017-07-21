package com.hdyl.baselib.utils.convert;

public class NumberUtil {

    public static int convertToInt(String intStr, int defValue) {
        try {
            return Integer.parseInt(intStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return defValue;
    }

    public static long convertToLong(String longStr, long defValue) {
        try {
            return Long.parseLong(longStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return defValue;
    }

    public static float convertToFloat(String fStr, float defValue) {
        try {
            return Float.parseFloat(fStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return defValue;
    }

    public static double convertToDouble(String dStr, double defValue) {
        try {
            return Double.parseDouble(dStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return defValue;
    }


    public static int convertToInteger(String intStr) {
        return convertToInt(intStr, 0);
    }

    public static long convertToLong(String longStr) {
        return convertToLong(longStr, 0l);
    }

    public static float convertToFloat(String fStr) {
        return convertToFloat(fStr, 0f);
    }

    public static double convertToDouble(String dStr) {
        return convertToDouble(dStr, 0d);
    }

}
