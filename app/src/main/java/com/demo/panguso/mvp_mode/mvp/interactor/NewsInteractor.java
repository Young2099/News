package com.demo.panguso.mvp_mode.mvp.interactor;

import com.demo.panguso.mvp_mode.listener.RequestCallBack;

import rx.Subscription;

/**
 * Created by ${yangfang} on 2016/9/19.
 */
public interface NewsInteractor<T> {
    Subscription loadNewsChannels(RequestCallBack<T> callBack);
}
