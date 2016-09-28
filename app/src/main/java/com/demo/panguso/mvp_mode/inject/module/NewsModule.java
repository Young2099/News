package com.demo.panguso.mvp_mode.inject.module;

import com.demo.panguso.mvp_mode.inject.module.base.BaseModule;
import com.demo.panguso.mvp_mode.mvp.presenter.NewsPresenter;
import com.demo.panguso.mvp_mode.mvp.presenter.impl.NewsPresenterImpl;
import com.demo.panguso.mvp_mode.mvp.view.NewsView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ${yangfang} on 2016/9/19.
 */
@Module
public class NewsModule extends BaseModule<NewsView> {

    public NewsModule(NewsView view) {
        mView = view;

    }

    @Provides
    public NewsPresenter providerNewsChannelPresenter() {
        return new NewsPresenterImpl(mView);
    }

}
