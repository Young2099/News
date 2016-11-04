package com.demo.panguso.mvp_mode.mvp.presenter;

import com.demo.panguso.mvp_mode.mvp.presenter.base.BasePresenter;

/**
 * Created by ${yangfang} on 2016/11/4.
 */

public interface PhotoPresenter extends BasePresenter {
    void loadMore();
    void refreshData();
}
