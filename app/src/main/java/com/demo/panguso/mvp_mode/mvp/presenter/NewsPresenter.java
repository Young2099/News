package com.demo.panguso.mvp_mode.mvp.presenter;

import android.content.Context;

import com.demo.panguso.mvp_mode.mvp.presenter.base.BasePresenter;

/**
 * Created by ${yangfang} on 2016/9/8.
 */
public interface NewsPresenter extends BasePresenter {
    void onItemClicked(Context context, String postId, String imgSrc);
}
