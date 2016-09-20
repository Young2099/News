package com.demo.panguso.mvp_mode.module;

import com.demo.panguso.mvp_mode.module.base.BaseModule;
import com.demo.panguso.mvp_mode.mvp.presenter.NewsPresenter;
import com.demo.panguso.mvp_mode.mvp.presenter.impl.NewsPresenterImpl;
import com.demo.panguso.mvp_mode.mvp.ui.adapter.NewsRecyclerViewAdapter;
import com.demo.panguso.mvp_mode.mvp.view.NewsView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ${yangfang} on 2016/9/20.
 */

@Module
public class NewsModule extends BaseModule<NewsView> {
    private String channelId;
    private String channelType;
    private int startPage;

    public NewsModule(NewsView view, String channelId, String channelType, int startPage) {
        mView = view;
        this.channelId = channelId;
        this.channelType = channelType;
        this.startPage = startPage;
    }

    @Provides
    public NewsPresenter provideNewsPresenter() {
        return new NewsPresenterImpl(mView, channelId, channelType,startPage);
    }

    @Provides
    public NewsRecyclerViewAdapter providerNewsRecyclerViewAdapter(){
        return new NewsRecyclerViewAdapter();
    }
}
