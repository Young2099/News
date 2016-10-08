package com.demo.panguso.mvp_mode.mvp.presenter.impl;

import com.demo.panguso.mvp_mode.common.Constants;
import com.demo.panguso.mvp_mode.mvp.interactor.impl.NewsChannelInteractorImpl;
import com.demo.panguso.mvp_mode.mvp.presenter.NewsChannelPresenter;
import com.demo.panguso.mvp_mode.mvp.presenter.base.BasePresenterImpl;
import com.demo.panguso.mvp_mode.mvp.view.NewsChannelView;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import greendao.NewsChannelTable;

/**
 * Created by ${yangfang} on 2016/10/8.
 */

public class NewsChannelPresenterImpl extends BasePresenterImpl<NewsChannelView,
        Map<Integer,List<NewsChannelTable>>> implements NewsChannelPresenter {
    private NewsChannelInteractorImpl mNewsChannelInteractor;

    @Inject
    public NewsChannelPresenterImpl (NewsChannelInteractorImpl  newsChannelInteractor){
        mNewsChannelInteractor = newsChannelInteractor;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mNewsChannelInteractor.loadNewsChannels(this);
    }

    @Override
    public void success(Map<Integer, List<NewsChannelTable>> data) {
        super.success(data);
        mView.initRecyclerViews(data.get(Constants.NEWS_CHANNEL_MINE),data.get(Constants.NEWS_CHANNEL_MORE));
    }
}
