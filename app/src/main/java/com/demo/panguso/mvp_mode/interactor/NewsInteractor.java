package com.demo.panguso.mvp_mode.interactor;

import com.demo.panguso.mvp_mode.response.RequestCallBack;

import rx.Subscription;

/**
 * Created by ${yangfang} on 2016/9/9.
 */
public interface NewsInteractor<T> {

    Subscription loadChannel(RequestCallBack<T> listener);
}
