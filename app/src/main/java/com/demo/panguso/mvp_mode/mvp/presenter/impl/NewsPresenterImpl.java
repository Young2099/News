package com.demo.panguso.mvp_mode.mvp.presenter.impl;

import com.demo.panguso.mvp_mode.interactor.NewsInteractor;
import com.demo.panguso.mvp_mode.interactor.NewsInteractorImpl;
import com.demo.panguso.mvp_mode.mvp.bean.NewsSummary;
import com.demo.panguso.mvp_mode.mvp.presenter.NewsPresenter;
import com.demo.panguso.mvp_mode.mvp.presenter.impl.base.BasePresenterImpl;
import com.demo.panguso.mvp_mode.mvp.view.NewsView;

import java.util.List;

/**
 * Created by ${yangfang} on 2016/9/9.
 */
public class NewsPresenterImpl extends BasePresenterImpl<NewsView, List<NewsSummary>> implements NewsPresenter {
    private NewsInteractor<List<NewsSummary>> mNewsInteractor;
    private String channelId;
    private String channelType;
    private int startPage;
    private boolean mIsLoaded;

    public NewsPresenterImpl(NewsView mNewsView, String channelId, String channelType, int startPage) {
        mView = mNewsView;
        mNewsInteractor = new NewsInteractorImpl();
        this.channelId = channelId;
        this.startPage = startPage;
        this.channelType = channelType;
    }


    @Override
    public void success(List<NewsSummary> data) {
        mIsLoaded = true;
        mView.setItems(data);
        super.success(data);
        mView.hideProgress();

    }

    @Override
    public void onError(String errorMsg) {
        mView.showErrorMsg(errorMsg);
    }

    @Override
    public void beforeRequest() {
        if (!mIsLoaded) {
//            mView.showProgress();
        }
    }


    @Override
    public void onCreate() {
        mSubscription = mNewsInteractor.loadNews(this, channelId, channelType, startPage);
    }

    @Override
    public void onItemClicked(int position) {

    }
}
