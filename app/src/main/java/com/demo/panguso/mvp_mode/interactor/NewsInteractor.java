package com.demo.panguso.mvp_mode.interactor;

import com.demo.panguso.mvp_mode.mvp.bean.NewsSummary;
import com.demo.panguso.mvp_mode.response.RequestCallBack;

import java.util.List;

import rx.Subscription;

/**
 * Created by ${yangfang} on 2016/9/20.
 */
public interface NewsInteractor<T> {
    Subscription setListItem(RequestCallBack<List<NewsSummary>> listener, String type, String id, int startPage);
}
