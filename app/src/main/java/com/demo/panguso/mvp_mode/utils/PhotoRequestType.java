package com.demo.panguso.mvp_mode.utils;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by ${yangfang} on 2016/11/11.
 */

public class PhotoRequestType {

    public static final int TYPE_SHARE = 1;
    public static final int TYPE_SAVE = 2;
    public static final int TYPE_SET_WALLPAPER = 3;


    @IntDef({TYPE_SHARE, TYPE_SAVE, TYPE_SET_WALLPAPER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface PhotoRequestTypeChecker {
    }
}
