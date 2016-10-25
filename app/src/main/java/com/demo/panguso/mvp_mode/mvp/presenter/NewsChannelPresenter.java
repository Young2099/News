package com.demo.panguso.mvp_mode.mvp.presenter;

import com.demo.panguso.mvp_mode.mvp.presenter.base.BasePresenter;

import greendao.NewsChannelTable;

/**
 * Created by ${yangfang} on 2016/10/8.
 */

public interface NewsChannelPresenter extends BasePresenter {
    void onItemSwap(int fromPosition,int toPosition);

    void onItemAddOrRemove(NewsChannelTable newsChannelTable,boolean isChannelMine);
}
