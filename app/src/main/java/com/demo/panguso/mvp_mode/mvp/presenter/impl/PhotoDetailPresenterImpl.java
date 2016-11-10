package com.demo.panguso.mvp_mode.mvp.presenter.impl;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.demo.panguso.mvp_mode.listener.RequestCallBack;
import com.demo.panguso.mvp_mode.mvp.bean.PhotoDetaiView;
import com.demo.panguso.mvp_mode.mvp.interactor.impl.PhotoDetailInteractorImpl;
import com.demo.panguso.mvp_mode.mvp.presenter.PhotoDetailPresenter;
import com.demo.panguso.mvp_mode.mvp.presenter.base.BasePresenterImpl;

import javax.inject.Inject;

/**
 * Created by ${yangfang} on 2016/11/10.
 */

public class PhotoDetailPresenterImpl extends BasePresenterImpl<PhotoDetaiView, Uri> implements PhotoDetailPresenter, RequestCallBack<Uri> {
    private PhotoDetailInteractorImpl mPhotoDetailInteractor;
    private Activity mActivity;

    @Inject
    public PhotoDetailPresenterImpl(PhotoDetailInteractorImpl photoDetailPresenter, Activity activity) {
        mPhotoDetailInteractor = photoDetailPresenter;
        mActivity = activity;
    }

    @Override
    public void shareUri(String imageUrl) {
       mSubscription =  mPhotoDetailInteractor.saveImageAndGetImageUri(this, imageUrl);
    }

    @Override
    public void success(Uri data) {
        super.success(data);
        share(data);
    }

    private void share(Uri uri) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setType("image/jpeg");
        mActivity.startActivity(intent);
    }
}
