package com.demo.panguso.mvp_mode.mvp.interactor;

import com.demo.panguso.mvp_mode.listener.RequestCallBack;

import rx.Subscription;

/**
 * Created by ${yangfang} on 2016/10/8.
 */

public interface NewsChannelInteractor<T> {
    Subscription loadNewsChannels(RequestCallBack<T> callBack);
}
