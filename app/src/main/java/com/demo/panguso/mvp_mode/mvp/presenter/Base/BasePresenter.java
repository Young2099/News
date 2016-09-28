package com.demo.panguso.mvp_mode.mvp.presenter.base;

import com.demo.panguso.mvp_mode.mvp.view.base.BaseView;

/**
 * Created by ${yangfang} on 2016/9/8.
 */
public interface BasePresenter {

    void onCreate();
    void attachView(BaseView view);

    void onDestory();
}
