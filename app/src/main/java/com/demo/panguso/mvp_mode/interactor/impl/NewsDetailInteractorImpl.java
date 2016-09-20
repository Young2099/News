package com.demo.panguso.mvp_mode.interactor.impl;

import com.demo.panguso.mvp_mode.interactor.NewsDetailInteractor;
import com.demo.panguso.mvp_mode.mvp.bean.NewsDetail;
import com.demo.panguso.mvp_mode.response.RequestCallBack;

import rx.Subscription;

/**
 * Created by ${yangfang} on 2016/9/20.
 */
public class NewsDetailInteractorImpl implements NewsDetailInteractor<NewsDetail> {


    @Override
    public Subscription loadDetailNews(RequestCallBack<NewsDetail> data, String id) {
        return null;
    }
}
