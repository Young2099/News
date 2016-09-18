package com.demo.panguso.mvp_mode.mvp.presenter;

import com.demo.panguso.mvp_mode.mvp.presenter.Base.BasePresenter;

/**
 * Created by ${yangfang} on 2016/9/18.
 */
public interface NewsListPresenter extends BasePresenter {
    void onItemClicked(int position);
}
