package com.demo.panguso.mvp_mode.module;

import com.demo.panguso.mvp_mode.module.base.BaseModule;
import com.demo.panguso.mvp_mode.mvp.presenter.NewsListPresenter;
import com.demo.panguso.mvp_mode.mvp.presenter.impl.NewsListPresenterImpl;
import com.demo.panguso.mvp_mode.mvp.ui.adapter.NewsRecyclerViewAdapter;
import com.demo.panguso.mvp_mode.mvp.view.NewsListView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ${yangfang} on 2016/9/14.
 */
@Module
public class NewsListModule extends BaseModule<NewsListView>{
    private String mNewsType;
    private String mNewsId;

    public NewsListModule(NewsListView newsListView, String newsType, String newsId) {
        mView = newsListView;
        mNewsType = newsType;
        mNewsId = newsId;
    }

    @Provides
    public NewsListPresenter provideNewsListPresenter() {
        return new NewsListPresenterImpl(mView, mNewsType, mNewsId);
    }

    @Provides
    public NewsRecyclerViewAdapter provideNewsRecyclerViewAdapter() {
        return new NewsRecyclerViewAdapter();
    }
}
