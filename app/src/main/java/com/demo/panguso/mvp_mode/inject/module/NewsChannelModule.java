package com.demo.panguso.mvp_mode.inject.module;

import com.demo.panguso.mvp_mode.inject.module.base.BaseModule;
import com.demo.panguso.mvp_mode.mvp.presenter.NewsChannelPresenter;
import com.demo.panguso.mvp_mode.mvp.presenter.impl.NewsChannelPresenterImpl;
import com.demo.panguso.mvp_mode.mvp.view.NewsChannelView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ${yangfang} on 2016/9/19.
 */
@Module
public class NewsChannelModule extends BaseModule<NewsChannelView> {

    public NewsChannelModule(NewsChannelView view) {
        mView = view;

    }

    @Provides
    public NewsChannelPresenter providerNewsChannelPresenter() {
        return new NewsChannelPresenterImpl(mView);
    }

}
