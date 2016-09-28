package com.demo.panguso.mvp_mode.mvp.presenter.impl;

import com.demo.panguso.mvp_mode.mvp.interactor.impl.NewsListInteractorImpl;
import com.demo.panguso.mvp_mode.mvp.bean.NewsSummary;
import com.demo.panguso.mvp_mode.mvp.presenter.NewsListPresenter;
import com.demo.panguso.mvp_mode.mvp.presenter.base.BasePresenterImpl;
import com.demo.panguso.mvp_mode.mvp.view.NewsListView;

import java.util.List;

/**
 * Created by ${yangfang} on 2016/9/20.
 * 在这里去做和activity，fragment做数据处理的交互
 */
public class NewsListPresenterImpl extends BasePresenterImpl<NewsListView, List<NewsSummary>> implements NewsListPresenter {
    private NewsListView mView;
    private NewsListInteractorImpl mNewsInteractor;
    private String channelId;
    private String channelType;
    private int startPage;
    /**
     * 新闻页面首次加载
     */
    private boolean mIsLoaded;

    public NewsListPresenterImpl(NewsListView mView, String channelId, String channelType, int startPage) {
        mNewsInteractor = new NewsListInteractorImpl();
        this.mView = mView;
        this.channelType = channelType;
        this.channelId = channelId;
    }


    /**
     * 去请求数据
     */
    @Override
    public void onCreate() {
        if (mView != null) {
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

//    @Override
//    public void onItemClicked(Context context, String postId, String imgSrc) {
//        Intent intent = new Intent(context, NewsDetailActivity.class);
//        intent.putExtra(Constants.NEWS_POST_ID, postId);
//        intent.putExtra(Constants.NEWS_IMG_RES, imgSrc);
//        context.startActivity(intent);
//    }
}
