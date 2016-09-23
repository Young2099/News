package com.demo.panguso.mvp_mode.inject.module;

import com.demo.panguso.mvp_mode.inject.module.base.BaseModule;
import com.demo.panguso.mvp_mode.mvp.presenter.NewsDetailPresenter;
import com.demo.panguso.mvp_mode.mvp.presenter.impl.NewsDetailPresenterImpl;
import com.demo.panguso.mvp_mode.mvp.view.NewsDetailView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ${yangfang} on 2016/9/20.
 */

@Module
public class NewsDetailModule extends BaseModule<NewsDetailView> {
    private String postId;

    public NewsDetailModule(NewsDetailView view, String postId) {
        mView = view;
        this.postId = postId;
    }

    @Provides
    public NewsDetailPresenter providerNewsDetailPresenter() {
        return new NewsDetailPresenterImpl(mView,postId);
    }

}
