package com.demo.panguso.mvp_mode.mvp.interactor;

import com.demo.panguso.mvp_mode.listener.RequestCallBack;

import rx.Subscription;


/**
 * Created by ${yangfang} on 2016/11/4.
 */

public interface PhotoInteractor<T> {
    Subscription loadPhotosList(RequestCallBack<T> callBack, int size, int page);
}
