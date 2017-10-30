package com.rjsoft.resourcelib.utils;


/**
 * Desc
 * Author feng
 * Date   2017/7/25.
 */

public class ParseUtil {
    public static int parseInt(String s) {
        int i = 0;
        try {
            i = Integer.parseInt(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    public static double parseDouble(String s) {
        double d = 0;
        try {
            d = Double.parseDouble(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return d;
    }

    public static long parseLong(String s) {
        long d = 0;
        try {
            d = Long.parseLong(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return d;
    }
}
