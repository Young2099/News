package com.demo.panguso.mvp_mode.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.demo.panguso.mvp_mode.common.Constants;
import com.demo.panguso.mvp_mode.common.PrefKey;

/**
 * SharedPreferences公共类
 * Created by ${yangfang} on 2016/9/14.
 */
public class SharedPreferencesUtil {
    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
    }

    /**
     * 设置Boolean值
     *
     * @param fileName
     * @param key
     * @param value
     */
    public static void setBoolean(String fileName, String key, boolean value) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(fileName, Context.MODE_PRIVATE).edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * 设置String类型的值
     *
     * @param fileName
     * @param key
     * @param value
     */
    public static void setString(String fileName, String key, String value) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(fileName, Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.commit();
    }


    /**
     * 设置Int型键值
     *
     * @param key
     * @param value
     */
    public static void setInteger(String filename, String key, int value) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(filename, Context.MODE_PRIVATE).edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * 设置Long型键值
     *
     * @param key
     * @param value
     */
    public static void setLong(String filename, String key, long value) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(filename, Context.MODE_PRIVATE).edit();
        editor.putLong(key, value);
        editor.commit();
    }

    /**
     * 获取int型的值
     *
     * @param fileName
     * @param key
     * @param defaultValue
     * @return
     */
    public static int getInteger(String fileName, String key, Integer defaultValue) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, defaultValue);
    }

    /**
     * 获取String型值
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getString(String filename, String key, String defaultValue) {
        SharedPreferences preferences = mContext.getSharedPreferences(filename, Context.MODE_PRIVATE);
        return preferences.getString(key, defaultValue);
    }


    /**
     * 获取Boolean型值
     *
     * @param key
     * @return
     */
    public static boolean getBoolean(String filename, String key, boolean defaultValue) {
        SharedPreferences preferences = mContext.getSharedPreferences(filename, Context.MODE_PRIVATE);
        return preferences.getBoolean(key, defaultValue);
    }

    /**
     * 获取Long型值
     *
     * @param key
     * @return
     */
    public static long getLong(String filename, String key, long defaultValue) {
        SharedPreferences preferences = mContext.getSharedPreferences(filename, Context.MODE_PRIVATE);
        return preferences.getLong(key, defaultValue);
    }

    /**
     * 设置夜间模式的样式
     *
     * @param value
     */
    public static void setIsNightMode(boolean value) {
        setBoolean(PrefKey.APP_USER, Constants.NIGHT_THEME_MODE, value);
    }

    public static boolean getIsNightMode() {
        return getBoolean(PrefKey.APP_USER, Constants.SHARES_COLOURFUL_NEWS, false);
    }

    public static boolean getIsBoolean(){
        return getBoolean(PrefKey.APP_USER,Constants.INIT_DB,false);
    }

    public static void setIsBoolean(boolean value){
        setBoolean(PrefKey.APP_USER,Constants.INIT_DB,value);
    }

}
