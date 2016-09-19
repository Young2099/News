package com.demo.panguso.mvp_mode.mvp.view;

import com.demo.panguso.mvp_mode.mvp.view.base.BaseView;

import java.util.List;

import greendao.NewsChannelTable;

/**
 * Created by ${yangfang} on 2016/9/19.
 */
public interface NewsChannelView extends BaseView {
    void initViewPager(List<NewsChannelTable> list);
}
