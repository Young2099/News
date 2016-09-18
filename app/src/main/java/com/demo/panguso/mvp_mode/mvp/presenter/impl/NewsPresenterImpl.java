package com.demo.panguso.mvp_mode.mvp.presenter.impl;

import com.demo.panguso.mvp_mode.interactor.NewsInteractor;
import com.demo.panguso.mvp_mode.interactor.NewsInteractorImpl;
import com.demo.panguso.mvp_mode.mvp.presenter.NewsPresenter;
import com.demo.panguso.mvp_mode.mvp.presenter.impl.base.BasePresenterImpl;
import com.demo.panguso.mvp_mode.mvp.view.NewsView;

import java.util.List;

import greendao.NewsChannelTable;

/**
 * Created by ${yangfang} on 2016/9/9.
 */
public class NewsPresenterImpl extends BasePresenterImpl<NewsView, List<NewsChannelTable>> implements NewsPresenter {
    private NewsInteractor<List<NewsChannelTable>> mNewsInteractor;

    public NewsPresenterImpl(NewsView mNewsView) {
        mView = mNewsView;
        mNewsInteractor = new NewsInteractorImpl();
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mSubscription = mNewsInteractor.loadChannel(this);
    }

    @Override
    public void success(List<NewsChannelTable> data) {
        super.success(data);
        mView.initViewPager(data);
    }

    @Override
    public void onItemClicked(int position) {

    }
}
