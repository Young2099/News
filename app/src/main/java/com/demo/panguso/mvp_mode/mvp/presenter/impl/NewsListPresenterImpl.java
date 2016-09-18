package com.demo.panguso.mvp_mode.mvp.presenter.impl;

import com.demo.panguso.mvp_mode.interactor.NewsListInteractor;
import com.demo.panguso.mvp_mode.interactor.NewsListInteractorImpl;
import com.demo.panguso.mvp_mode.mvp.bean.NewsSummary;
import com.demo.panguso.mvp_mode.mvp.presenter.NewsListPresenter;
import com.demo.panguso.mvp_mode.mvp.presenter.impl.base.BasePresenterImpl;
import com.demo.panguso.mvp_mode.mvp.view.NewsListView;
import com.demo.panguso.mvp_mode.response.RequestCallBack;

import java.util.List;

/**
 * Created by ${yangfang} on 2016/9/18.
 */
public class NewsListPresenterImpl extends BasePresenterImpl<NewsListView,List<NewsSummary>> implements NewsListPresenter,RequestCallBack<List<NewsSummary>> {
    private NewsListInteractor<List<NewsSummary>> mNewsListInteractor;
    private String mNewsType;
    private String mNewsId;
    private int mStartPage;
    private boolean misLoad;
    public NewsListPresenterImpl(NewsListView mNewsListView, String mNewsType, String mNewsId) {
        mView = mNewsListView;
        mNewsListInteractor  = new NewsListInteractorImpl();
        this.mNewsType = mNewsType;
        this.mNewsId = mNewsId;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if(mView != null){
            mSubscription = mNewsListInteractor.loadNews(this,mNewsType,mNewsId,mStartPage);
        }
    }

    @Override
    public void onDestory() {
        mView = null;
    }

    @Override
    public void success(List<NewsSummary> data) {
        misLoad = true;
        if(mView != null){
            mView.setItems(data);
            mView.hideProgress();
        }
    }

    @Override
    public void onError(String errorMsg) {
        mView.showErrorMsg(errorMsg);
    }

    @Override
    public void beforeRequest() {
        if(!misLoad){
            mView.showProgress();
        }
    }

    @Override
    public void onItemClicked(int position) {

    }
}
