package com.demo.panguso.mvp_mode.inject.module;

import com.demo.panguso.mvp_mode.inject.module.base.BaseModule;
import com.demo.panguso.mvp_mode.mvp.presenter.NewsListPresenter;
import com.demo.panguso.mvp_mode.mvp.presenter.impl.NewsListPresenterImpl;
import com.demo.panguso.mvp_mode.mvp.ui.adapter.NewsRecyclerViewAdapter;
import com.demo.panguso.mvp_mode.mvp.view.NewsListView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ${yangfang} on 2016/9/20.
 */

@Module
public class NewsListModule extends BaseModule<NewsListView> {
    private String channelId;
    private String channelType;
    private int startPage;

    public NewsListModule(NewsListView view, String channelId, String channelType, int startPage) {
        mView = view;
        this.channelId = channelId;
        this.channelType = channelType;
        this.startPage = startPage;
    }

    @Provides
    public NewsListPresenter provideNewsPresenter() {
        return new NewsListPresenterImpl(mView, channelId, channelType,startPage);
    }

    @Provides
    public NewsRecyclerViewAdapter providerNewsRecyclerViewAdapter(){
        return new NewsRecyclerViewAdapter();
    }
}
