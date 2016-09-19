package com.demo.panguso.mvp_mode.interactor;

import com.demo.panguso.mvp_mode.response.RequestCallBack;

import rx.Subscription;

/**
 * Created by ${yangfang} on 2016/9/19.
 */
public interface NewsChannelInteractor<T> {
    Subscription loadNewsChannels(RequestCallBack<T> callBack);
}
