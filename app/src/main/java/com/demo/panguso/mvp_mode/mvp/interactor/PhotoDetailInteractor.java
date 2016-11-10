package com.demo.panguso.mvp_mode.mvp.interactor;

import com.demo.panguso.mvp_mode.listener.RequestCallBack;

import rx.Subscription;

/**
 * Created by ${yangfang} on 2016/11/10.
 */

public interface PhotoDetailInteractor<T> {
    Subscription saveImageAndGetImageUri(RequestCallBack<T> listener,String url);
}
