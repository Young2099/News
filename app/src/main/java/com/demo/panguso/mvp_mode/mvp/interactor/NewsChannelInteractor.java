package com.demo.panguso.mvp_mode.mvp.interactor;

import com.demo.panguso.mvp_mode.listener.RequestCallBack;

import greendao.NewsChannelTable;
import rx.Subscription;

/**
 * Created by ${yangfang} on 2016/10/8.
 */

public interface NewsChannelInteractor<T> {
    //加载更多新闻频道
    Subscription loadNewsChannels(RequestCallBack<T> callBack);

    //定义方法来滑动频道管理的图标
    void swapDb(int fromPositon, int toPosition);

    //更新频道
    void updateDb(NewsChannelTable newsChannelTable,boolean isChanelMine);
}
