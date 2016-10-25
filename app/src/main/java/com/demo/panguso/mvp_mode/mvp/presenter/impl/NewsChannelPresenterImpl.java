package com.demo.panguso.mvp_mode.mvp.presenter.impl;

import com.demo.panguso.mvp_mode.common.Constants;
import com.demo.panguso.mvp_mode.listener.ChannelItemMoveEvent;
import com.demo.panguso.mvp_mode.mvp.interactor.impl.NewsChannelInteractorImpl;
import com.demo.panguso.mvp_mode.mvp.presenter.NewsChannelPresenter;
import com.demo.panguso.mvp_mode.mvp.presenter.base.BasePresenterImpl;
import com.demo.panguso.mvp_mode.mvp.view.NewsChannelView;
import com.demo.panguso.mvp_mode.utils.RxBus;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import greendao.NewsChannelTable;

/**
 * Created by ${yangfang} on 2016/10/8.
 */

public class NewsChannelPresenterImpl extends BasePresenterImpl<NewsChannelView,
        Map<Integer, List<NewsChannelTable>>> implements NewsChannelPresenter {
    private NewsChannelInteractorImpl mNewsChannelInteractor;
    private boolean mIsChannelChanged;

    @Inject
    public NewsChannelPresenterImpl(NewsChannelInteractorImpl newsChannelInteractor) {
        mNewsChannelInteractor = newsChannelInteractor;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mNewsChannelInteractor.loadNewsChannels(this);
    }

    @Override
    public void success(Map<Integer, List<NewsChannelTable>> data) {
        super.success(data);
        mView.initRecyclerViews(data.get(Constants.NEWS_CHANNEL_MINE), data.get(Constants.NEWS_CHANNEL_MORE));
    }

    /**
     * 交换移动变化我的频道的位置
     *
     * @param fromPosition
     * @param toPosition
     */
    @Override
    public void onItemSwap(int fromPosition, int toPosition) {
        mNewsChannelInteractor.swapDb(fromPosition, toPosition);
        mIsChannelChanged = true;
    }

    /**
     * 增加、删减我的频道
     *
     * @param newsChannelTable
     * @param isChannelMine
     */
    @Override
    public void onItemAddOrRemove(NewsChannelTable newsChannelTable, boolean isChannelMine) {
        mNewsChannelInteractor.updateDb(newsChannelTable, isChannelMine);
        mIsChannelChanged = true;
    }

    @Override
    public void onDestory() {
        super.onDestory();
        if (mIsChannelChanged) {
            RxBus.getInstance().post(new ChannelItemMoveEvent());
        }
    }
}
