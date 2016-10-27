package com.demo.panguso.mvp_mode.utils;

import com.demo.panguso.mvp_mode.app.App;

/**
 * Created by ${yangfang} on 2016/10/27.
 * dp和px的转换工具
 */
public class DimenUtil {
    public static float dp2px(float dp) {
        final float scale = App.getAppContext().getResources().getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }

    public static float sp2pX(float sp) {
        final float scale = App.getAppContext().getResources().getDisplayMetrics().scaledDensity;
        return sp * scale;
    }

    /**
     * 屏幕的宽度px
     *
     * @return
     */
    public static int getScreenSize() {
        return App.getAppContext().getResources().getDisplayMetrics().widthPixels;
    }

}
