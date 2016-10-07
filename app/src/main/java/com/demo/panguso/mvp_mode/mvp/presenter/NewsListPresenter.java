package com.demo.panguso.mvp_mode.mvp.presenter;

import com.demo.panguso.mvp_mode.mvp.presenter.base.BasePresenter;

/**
 * Created by ${yangfang} on 2016/9/8.
 */
public interface NewsListPresenter extends BasePresenter {
    void onItemClicked(String newsType, String newsId);

    void resfreshData();

    void loadMore();
}
