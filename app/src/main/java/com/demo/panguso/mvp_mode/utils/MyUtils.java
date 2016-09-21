package com.demo.panguso.mvp_mode.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ${yangfang} on 2016/9/14.
 */
public class MyUtils {

    public static String formatDate(String before) {

        String after;
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                    .parse(before);
            after = new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault()).format(date);
        } catch (ParseException e) {
            Log.e("MyUtils", "日期转换格式异常");
            return before;
        }
        return after;
    }


}
