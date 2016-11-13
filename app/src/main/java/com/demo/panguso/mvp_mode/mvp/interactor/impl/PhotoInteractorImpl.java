package com.demo.panguso.mvp_mode.mvp.interactor.impl;

import android.util.Log;

import com.demo.panguso.mvp_mode.R;
import com.demo.panguso.mvp_mode.app.App;
import com.demo.panguso.mvp_mode.common.HostType;
import com.demo.panguso.mvp_mode.listener.RequestCallBack;
import com.demo.panguso.mvp_mode.mvp.bean.GirlData;
import com.demo.panguso.mvp_mode.mvp.bean.PhotoGirl;
import com.demo.panguso.mvp_mode.mvp.interactor.PhotoInteractor;
import com.demo.panguso.mvp_mode.respository.network.RetrofitManager;
import com.demo.panguso.mvp_mode.utils.TransformUtils;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
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
    public Subscription loadPhotosList(final RequestCallBack<List<PhotoGirl>> callBack, int size, int page) {
        return RetrofitManager.getInstance(HostType.GANK_GIRL_PHOTO)
                .getPhotoList(size, page)
                //用map说明返回的数据是数组类型的girdata和一个错误的信息message
                .map(new Func1<GirlData, List<PhotoGirl>>() {
                    @Override
                    public List<PhotoGirl> call(GirlData girData) {
                        return girData.getResults();
                    }
                })
                .compose(TransformUtils.<List<PhotoGirl>>defaultSchedulers())
                .subscribe(new Subscriber<List<PhotoGirl>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG", "PPPPPPPP");
                        callBack.onError(App.getAppContext().getString(R.string.load_error));
                    }

                    @Override
                    public void onNext(List<PhotoGirl> photoGirls) {
                        callBack.success(photoGirls);
                    }
                })
                ;
    }
}
