package com.demo.panguso.mvp_mode.mvp.presenter;

import com.demo.panguso.mvp_mode.mvp.presenter.Base.BasePresenter;

/**
 * Created by ${yangfang} on 2016/9/8.
 */
public interface NewsPresenter extends BasePresenter {
    void onFabClicked();

    void onItemClicked(int position);
}
