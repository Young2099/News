package com.demo.panguso.mvp_mode.interactor;

import com.demo.panguso.mvp_mode.response.RequestCallBack;

/**
 * Created by ${yangfang} on 2016/9/14.
 */
public interface NewsListInteractor<T> {
    void loadNews(RequestCallBack<T> callBack, String type, String id, int startPage);
}
