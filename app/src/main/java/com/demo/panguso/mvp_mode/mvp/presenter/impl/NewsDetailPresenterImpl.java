package com.demo.panguso.mvp_mode.mvp.presenter.impl;

import com.demo.panguso.mvp_mode.mvp.interactor.NewsDetailInteractor;
import com.demo.panguso.mvp_mode.mvp.interactor.impl.NewsDetailInteractorImpl;
import com.demo.panguso.mvp_mode.mvp.bean.NewsDetail;
import com.demo.panguso.mvp_mode.mvp.presenter.NewsDetailPresenter;
import com.demo.panguso.mvp_mode.mvp.presenter.base.BasePresenterImpl;
import com.demo.panguso.mvp_mode.mvp.view.NewsDetailView;

/**
 * Created by ${yangfang} on 2016/9/20.
 */
public class NewsDetailPresenterImpl extends BasePresenterImpl<NewsDetailView, NewsDetail> implements NewsDetailPresenter {
    private NewsDetailInteractor mNewsDetailInteractor;

    private String postId;
    private Boolean mIsLoad;

    public NewsDetailPresenterImpl(NewsDetailView view, String postId) {
        mView = view;
        this.postId = postId;
        mNewsDetailInteractor = new NewsDetailInteractorImpl();
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
}
