package com.demo.panguso.mvp_mode.mvp.view;

import com.demo.panguso.mvp_mode.mvp.view.base.BaseView;

import java.util.List;

import greendao.NewsChannelTable;

/**
 * Created by ${yangfang} on 2016/10/8.
 */
public interface NewsChannelView extends BaseView{
    void initRecyclerViews(List<NewsChannelTable> newsChannelsMine, List<NewsChannelTable> newsChannelsMore);
}
