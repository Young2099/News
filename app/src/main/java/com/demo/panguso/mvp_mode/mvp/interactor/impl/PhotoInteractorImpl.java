package com.demo.panguso.mvp_mode.mvp.interactor.impl;

import com.demo.panguso.mvp_mode.listener.RequestCallBack;
import com.demo.panguso.mvp_mode.mvp.bean.PhotoGirl;
import com.demo.panguso.mvp_mode.mvp.interactor.PhotoInteractor;

import java.util.List;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by ${yangfang} on 2016/11/4.
 */

public class PhotoInteractorImpl implements PhotoInteractor<List<PhotoGirl>> {

    @Inject
    public PhotoInteractorImpl() {
    }

    @Override
    public Subscription loadPhotos(RequestCallBack<List<PhotoGirl>> callBack, int size, int page) {
        return null;
    }
}
