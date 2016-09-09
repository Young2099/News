package com.demo.panguso.mvp_mode.module;

import com.demo.panguso.mvp_mode.mvp.presenter.NewsPresenter;
import com.demo.panguso.mvp_mode.mvp.presenter.impl.NewsPresenterImpl;
import com.demo.panguso.mvp_mode.mvp.view.NewsView;

import dagger.Provides;

/**
 * Created by ${yangfang} on 2016/9/9.
 */
public class NewsModule {
    private NewsView mNewsView;

    public NewsModule(NewsView newsView) {
        mNewsView = newsView;
    }

    @Provides
    public NewsPresenter providerNewsPresenter(){
        return new NewsPresenterImpl(mNewsView);
    }
}
