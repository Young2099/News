package com.demo.panguso.mvp_mode.mvp.view;

import com.demo.panguso.mvp_mode.mvp.bean.NewsDetail;
import com.demo.panguso.mvp_mode.mvp.view.base.BaseView;

/**
 * Created by ${yangfang} on 2016/9/20.
 */
public interface NewsDetailView extends BaseView {
    void initDetailNews(NewsDetail newsDetail);
}
