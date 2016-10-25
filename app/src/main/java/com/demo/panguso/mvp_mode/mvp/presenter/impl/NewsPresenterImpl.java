package com.demo.panguso.mvp_mode.mvp.presenter.impl;

import com.demo.panguso.mvp_mode.mvp.interactor.NewsInteractor;
import com.demo.panguso.mvp_mode.mvp.interactor.impl.NewsInteractorImpl;
import com.demo.panguso.mvp_mode.mvp.presenter.base.BasePresenterImpl;
import com.demo.panguso.mvp_mode.mvp.presenter.NewsPresenter;
import com.demo.panguso.mvp_mode.mvp.view.NewsView;

import java.util.List;

import javax.inject.Inject;

import greendao.NewsChannelTable;

/**
 * Created by ${yangfang} on 2016/9/19.
 */
public class NewsPresenterImpl extends BasePresenterImpl<NewsView,List<NewsChannelTable>> implements NewsPresenter {

    public NewsInteractor<List<NewsChannelTable>> channelInteractor;

    @Inject
    public NewsPresenterImpl(NewsInteractorImpl mNewsInteractor) {
        channelInteractor = mNewsInteractor;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        loadNewsChannels();
    }

    private void loadNewsChannels() {
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

    /**
     * 我的频道修改后，返回修复记忆的位置
     */
    @Override
    public void onChangedDb() {
        loadNewsChannels();
    }
}
