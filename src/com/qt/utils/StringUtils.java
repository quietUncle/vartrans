package com.qt.utils;

public class StringUtils {
    //首字母小写
    public  static String lowerCase(String str) {
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }
    //首字母大写
    public static String upperCase(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
