package com.demo.panguso.mvp_mode.mvp.presenter.impl;

import com.demo.panguso.mvp_mode.mvp.interactor.NewsDetailInteractor;
import com.demo.panguso.mvp_mode.mvp.interactor.impl.NewsDetailInteractorImpl;
import com.demo.panguso.mvp_mode.mvp.bean.NewsDetail;
import com.demo.panguso.mvp_mode.mvp.presenter.NewsDetailPresenter;
import com.demo.panguso.mvp_mode.mvp.presenter.base.BasePresenterImpl;
import com.demo.panguso.mvp_mode.mvp.view.NewsDetailView;

import javax.inject.Inject;

/**
 * Created by ${yangfang} on 2016/9/20.
 */
public class NewsDetailPresenterImpl extends BasePresenterImpl<NewsDetailView, NewsDetail> implements NewsDetailPresenter {
    private NewsDetailInteractor mNewsDetailInteractor;

    private String postId;
    private Boolean mIsLoad;

    @Inject
    public NewsDetailPresenterImpl(NewsDetailInteractorImpl mNewsDetailInteractor) {
        this.mNewsDetailInteractor = mNewsDetailInteractor;
    }

    @Override
    public void onCreate() {
        mSubscription = mNewsDetailInteractor.loadDetailNews(this, postId);
    }

    @Override
    public void onDestory() {

    }

    @Override
    public void success(NewsDetail data) {
        super.success(data);
        if (mView != null) {
            mView.initDetailNews(data);
            mView.hideProgress();
        }
        mIsLoad = true;
    }

    @Override
    public void beforeRequest() {
        super.beforeRequest();
        if (!mIsLoad) {
            mView.showProgress();
        }
    }

    @Override
    public void postId(String postId) {
        this.postId = postId;
    }
}
