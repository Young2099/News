package com.demo.panguso.mvp_mode.mvp.interactor.impl;

import android.util.Log;

import com.demo.panguso.mvp_mode.R;
import com.demo.panguso.mvp_mode.app.App;
import com.demo.panguso.mvp_mode.common.HostType;
import com.demo.panguso.mvp_mode.listener.RequestCallBack;
import com.demo.panguso.mvp_mode.mvp.bean.GirData;
import com.demo.panguso.mvp_mode.mvp.bean.PhotoGirl;
import com.demo.panguso.mvp_mode.mvp.interactor.PhotoInteractor;
import com.demo.panguso.mvp_mode.respository.network.RetrofitManager;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by ${yangfang} on 2016/11/4.
 */

public class PhotoInteractorImpl implements PhotoInteractor<List<PhotoGirl>> {

    @Inject
    public PhotoInteractorImpl() {
    }

    @Override
    public Subscription loadPhotos(final RequestCallBack<List<PhotoGirl>> callBack, int size, final int page) {
        return RetrofitManager.getInstance(HostType.GANK_GIRL_PHOTO)
                .getPhotoList(size, page)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                //用map说明返回的数据是数组类型的girdata和一个错误的信息message
                .map(new Func1<GirData, List<PhotoGirl>>() {
                    @Override
                    public List<PhotoGirl> call(GirData girData) {
                        return girData.getResult();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<PhotoGirl>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onError(App.getAppContext().getString(R.string.load_error));
                    }

                    @Override
                    public void onNext(List<PhotoGirl> photoGirls) {
                        callBack.success(photoGirls);
                        Log.e("PhotoInteractorImpl","//////"+photoGirls.size());
                    }
                })
                ;
    }
}
