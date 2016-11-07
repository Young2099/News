package com.demo.panguso.mvp_mode.mvp.presenter.impl;

import com.demo.panguso.mvp_mode.common.LoadNewsType;
import com.demo.panguso.mvp_mode.listener.RequestCallBack;
import com.demo.panguso.mvp_mode.mvp.bean.PhotoGirl;
import com.demo.panguso.mvp_mode.mvp.interactor.impl.PhotoInteractorImpl;
import com.demo.panguso.mvp_mode.mvp.presenter.PhotoPresenter;
import com.demo.panguso.mvp_mode.mvp.presenter.base.BasePresenterImpl;
import com.demo.panguso.mvp_mode.mvp.view.PhotoView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by ${yangfang} on 2016/11/4.
 */

public class PhotoPresenterImpl extends BasePresenterImpl<PhotoView, List<PhotoGirl>> implements PhotoPresenter, RequestCallBack<List<PhotoGirl>> {

    private PhotoInteractorImpl mPhotoInteractor;
    private int SIZE = 20;
    private int mStartPage=1;
    private boolean misFirstLoad;
    private boolean mIsRefresh = true;

    @Inject
    public PhotoPresenterImpl(PhotoInteractorImpl photoInteractorImpl) {
        mPhotoInteractor = photoInteractorImpl;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        loadPhotoData();
    }

    @Override
    public void beforeRequest() {
        super.beforeRequest();
        if (!misFirstLoad) {
            mView.showProgress();
        }
    }

    private void loadPhotoData() {
       mSubscription = mPhotoInteractor.loadPhotosList(this,SIZE,mStartPage);
    }

    @Override
    public void success(List<PhotoGirl> data) {
        super.success(data);
        misFirstLoad = true;
        if (data != null) {
            mStartPage += 1;
        }
        int loadType = mIsRefresh ? LoadNewsType.TYPE_REFRESH_SUCCESS : LoadNewsType.TYPE_LOAD_MORE_SUCCESS;
        if (mView != null) {
            mView.initPhotoList(data, loadType);
            mView.hideProgress();
        }
    }

    @Override
    public void onError(String errorMsg) {
        super.onError(errorMsg);
        if (mView != null) {
            int loadType = mIsRefresh ? LoadNewsType.TYPE_REFRESH_ERROR : LoadNewsType.TYPE_LOAD_MORE_ERROR;
            mView.initPhotoList(null, loadType);
        }
    }

    @Override
    public void loadMore() {
        mIsRefresh = false;
        loadPhotoData();
    }

    @Override
    public void refreshData() {
        mStartPage = 1;
        mIsRefresh = true;
        loadPhotoData();
    }

    @Override
    public void onDestory() {
        super.onDestory();
        mView = null;
    }
}
