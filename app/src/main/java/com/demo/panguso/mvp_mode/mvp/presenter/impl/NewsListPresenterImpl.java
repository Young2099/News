package com.demo.panguso.mvp_mode.mvp.presenter.impl;

import android.util.Log;

import com.demo.panguso.mvp_mode.common.LoadNewsType;
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
    private boolean mIsFirstLoaded;
    private boolean mIsRefresh = true;

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
            loadNewsData();
        }
    }

    @Override
    public void onDestory() {
        mView = null;
    }

    @Override
    public void success(List<NewsSummary> data) {
        Log.e("NewsSuccess",":"+data.size());
        mIsFirstLoaded = true;
        if (data != null) {
            startPage += 20;
        }
        int loadType = mIsRefresh ? LoadNewsType.TYPE_REFRESH_SUCCESS : LoadNewsType
                .TYPE_LOAD_MORE_SUCCESS;
        if (mView != null) {
            mView.setItems(data, loadType);
            mView.hideProgress();
        }

    }

    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
        if (mView != null) {
            int loadType = mIsRefresh ? LoadNewsType.TYPE_REFRESH_ERROR :
                    LoadNewsType.TYPE_LOAD_MORE_ERROR;
            mView.setItems(null, loadType);
        }
    }

    @Override
    public void beforeRequest() {
        if (!mIsFirstLoaded) {
            mView.showProgress();
        }
    }

    @Override
    public void onItemClicked(String newsType, String newsId) {
        this.channelType = newsType;
        this.channelId = newsId;
        Log.e("NewsListPresenterImpl", "channelType" + channelType);
    }

    @Override
    public void resfreshData() {
        startPage = 0;
        mIsRefresh = true;
        loadNewsData();
    }

    private void loadNewsData() {
        Log.e("StartPage",""+startPage);
        mSubscription = mNewsInteractor.setListItem(this,channelType,channelId,startPage);
    }

    @Override
    public void loadMore() {
        mIsRefresh  = false;
        loadNewsData();
    }

}
