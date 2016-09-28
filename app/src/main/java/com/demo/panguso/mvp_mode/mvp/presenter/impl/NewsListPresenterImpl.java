package com.demo.panguso.mvp_mode.mvp.presenter.impl;

import android.util.Log;

import com.demo.panguso.mvp_mode.mvp.interactor.impl.NewsListInteractorImpl;
import com.demo.panguso.mvp_mode.mvp.bean.NewsSummary;
import com.demo.panguso.mvp_mode.mvp.presenter.NewsListPresenter;
import com.demo.panguso.mvp_mode.mvp.presenter.base.BasePresenterImpl;
import com.demo.panguso.mvp_mode.mvp.view.NewsListView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by ${yangfang} on 2016/9/20.
 * 在这里去做和activity，fragment做数据处理的交互
 */
public class NewsListPresenterImpl extends BasePresenterImpl<NewsListView, List<NewsSummary>> implements NewsListPresenter {
    private NewsListInteractorImpl mNewsInteractor;
    private String channelId;
    private String channelType;
    private int startPage;
    /**
     * 新闻页面首次加载
     */
    private boolean mIsLoaded;

    @Inject
    public NewsListPresenterImpl(NewsListInteractorImpl newsListInteractor) {
        mNewsInteractor = newsListInteractor;

    }

    /**
     * 去请求数据
     */
    @Override
    public void onCreate() {
        if (mView != null) {
            Log.e("NewsListPresenterImpl", "//////+++++");
            mSubscription = mNewsInteractor.setListItem(this, channelType, channelId, startPage);
        }
    }

    @Override
    public void onDestory() {
        mView = null;
    }

    @Override
    public void success(List<NewsSummary> data) {
        mIsLoaded = true;
        if (mView != null) {
            mView.setItems(data);
            mView.hideProgress();
        }

    }

    @Override
    public void onError(String errorMsg) {

    }

    @Override
    public void beforeRequest() {
        if (!mIsLoaded) {
            mView.showProgress();
        }
    }

    @Override
    public void onItemClicked(String newsType, String newsId) {
        this.channelType = newsType;
        this.channelId = newsId;
        Log.e("NewsListPresenterImpl", "channelType" + channelType);
    }

}
