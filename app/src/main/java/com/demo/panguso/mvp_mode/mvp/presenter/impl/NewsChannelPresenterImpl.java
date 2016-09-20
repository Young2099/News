package com.demo.panguso.mvp_mode.mvp.presenter.impl;

import com.demo.panguso.mvp_mode.interactor.NewsChannelInteractor;
import com.demo.panguso.mvp_mode.interactor.impl.NewsChannelInteractorImpl;
import com.demo.panguso.mvp_mode.mvp.presenter.base.BasePresenterImpl;
import com.demo.panguso.mvp_mode.mvp.presenter.NewsChannelPresenter;
import com.demo.panguso.mvp_mode.mvp.view.NewsChannelView;

import java.util.List;

import greendao.NewsChannelTable;

/**
 * Created by ${yangfang} on 2016/9/19.
 */
public class NewsChannelPresenterImpl extends BasePresenterImpl<NewsChannelView,List<NewsChannelTable>> implements NewsChannelPresenter {

    public NewsChannelInteractor<List<NewsChannelTable>> channelInteractor;

    public NewsChannelPresenterImpl(NewsChannelView view) {
        mView = view;
        channelInteractor = new NewsChannelInteractorImpl();
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mSubscription = channelInteractor.loadNewsChannels(this);
    }

    @Override
    public void onDestory() {
        mView = null;
    }

    @Override
    public void success(List<NewsChannelTable> data) {
        super.success(data);
        mView.initViewPager(data);
    }
}
