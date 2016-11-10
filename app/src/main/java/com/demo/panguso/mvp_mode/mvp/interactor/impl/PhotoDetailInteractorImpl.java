package com.demo.panguso.mvp_mode.mvp.interactor.impl;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.demo.panguso.mvp_mode.R;
import com.demo.panguso.mvp_mode.app.App;
import com.demo.panguso.mvp_mode.listener.RequestCallBack;
import com.demo.panguso.mvp_mode.mvp.interactor.PhotoDetailInteractor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by ${yangfang} on 2016/11/10.
 */

public class PhotoDetailInteractorImpl implements PhotoDetailInteractor {

    @Inject
    public PhotoDetailInteractorImpl() {
    }

    @Override
    public Subscription saveImageAndGetImageUri(final RequestCallBack listener, final String url) {
        return Observable.create(new Observable.OnSubscribe<Bitmap>() {
            @Override
            public void call(final Subscriber<? super Bitmap> subscriber) {
                //将Url转成Bitmap
                Glide.with(App.getAppContext())
                        .load(url)
                        .asBitmap()
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                                subscriber.onNext(bitmap);
                                subscriber.onCompleted();
                            }
                        });
            }
        })
                .subscribeOn(AndroidSchedulers.mainThread())//gilde在主线程运行
                .unsubscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(new Func1<Bitmap, Observable<Uri>>() {
                    @Override
                    public Observable<Uri> call(Bitmap bitmap) {
                        return getUriObservable(bitmap, url);//将bitmap转成uri
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Uri>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(App.getAppContext().getString(R.string.share_error));
                    }

                    @Override
                    public void onNext(Uri uri) {
                        listener.success(uri);
                    }
                })
                ;
    }

    private Observable<Uri> getUriObservable(Bitmap bitmap, String url) {
        File file = getImageFile(bitmap, url);
        Uri uri = Uri.fromFile(file);
        return Observable.just(uri);//将转成的uri多个just的意思
    }

    private File getImageFile(Bitmap bitmap, String url) {
        String fileName = "/photo/" + url.hashCode() + ".jpg";//设置存储的路径，url的hascode的值来作为不同的存储路径
        File file = new File(Environment.getExternalStorageDirectory(), fileName);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdir();
        }
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            //bitmap压缩
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }
}
