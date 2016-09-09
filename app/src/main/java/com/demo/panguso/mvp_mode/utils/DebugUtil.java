package com.demo.panguso.mvp_mode.utils;

import android.util.Log;

/**
 * Created by ${yangfang} on 2016/9/9.
 * log输出
 */
public class DebugUtil {

    private static final int VERBOSE = 1;
    private static final int LEVEL = VERBOSE;
    private static final int DEBUG = 2;
    private static final int WARN = 4;
    private static final int INFO = 3;
    private static final int ERROR = 5;

    private static void v(String tag, String msg) {
        if (LEVEL <= VERBOSE) {
            Log.v(tag, msg);
        }
    }

    private static void d(String tag, String msg) {
        if (LEVEL <= DEBUG) {
            Log.v(tag, msg);
        }
    }

    private static void i(String tag, String msg) {
        if (LEVEL <= INFO) {
            Log.v(tag, msg);
        }
    }

    private static void W(String tag, String msg) {
        if (LEVEL <= WARN) {
            Log.v(tag, msg);
        }
    }

    private static void e(String tag, String msg) {
        if (LEVEL <= ERROR) {
            Log.v(tag, msg);
        }
    }
}
