package com.demo.panguso.mvp_mode.common;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by ${yangfang} on 2016/9/12.
 */
public class HostType {
    /**
     * 多少种host类型
     */
    public static final int TYPE_COUNT = 2;

    /**
     * 网易新闻视频的host
     */
    public static final int NETEASE_NEWS_VIDEO = 1;

    /**
     * 新浪图片的host
     */
    public static final int GANK_GIRL_PHOTO = 2;

    /**
     * 新闻详情html图片的host
     */
    public static final int NEWS_DETAIL_HTML_PHOTO = 3;

    /**
     * 替代枚举的方案，使用IntDef保证类型安全
     */
    @IntDef({NETEASE_NEWS_VIDEO, GANK_GIRL_PHOTO, NEWS_DETAIL_HTML_PHOTO})
    @Retention(RetentionPolicy.SOURCE)
    public @interface HostTypeChecker {
    }

}
