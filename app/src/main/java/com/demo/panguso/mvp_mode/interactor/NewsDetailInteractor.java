package com.demo.panguso.mvp_mode.interactor;


import com.demo.panguso.mvp_mode.response.RequestCallBack;

import rx.Subscription;

/**
 * Created by ${yangfang} on 2016/9/20.
 */
public interface NewsDetailInteractor<T> {
    Subscription loadDetailNews(RequestCallBack<T> callBack, String id);
}
