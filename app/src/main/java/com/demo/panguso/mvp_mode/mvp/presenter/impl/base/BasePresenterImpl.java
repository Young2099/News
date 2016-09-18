package com.demo.panguso.mvp_mode.mvp.presenter.impl.base;

import com.demo.panguso.mvp_mode.mvp.presenter.Base.BasePresenter;
import com.demo.panguso.mvp_mode.mvp.view.base.BaseView;
import com.demo.panguso.mvp_mode.response.RequestCallBack;

import rx.Subscription;

/**
 * Created by ${yangfang} on 2016/9/18.
 */
public class BasePresenterImpl<T extends BaseView, E> implements BasePresenter, RequestCallBack<E> {
    protected T mView;
    protected Subscription mSubscription;

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestory() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
        mView = null;
    }

    @Override
    public void success(E data) {
        mView.hideProgress();
    }

    @Override
    public void onError(String errorMsg) {

    }

    @Override
    public void beforeRequest() {
        mView.showProgress();
    }
}
