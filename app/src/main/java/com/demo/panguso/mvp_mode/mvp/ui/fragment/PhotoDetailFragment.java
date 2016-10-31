package com.demo.panguso.mvp_mode.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.demo.panguso.mvp_mode.R;
import com.demo.panguso.mvp_mode.app.App;
import com.demo.panguso.mvp_mode.common.Constants;
import com.demo.panguso.mvp_mode.mvp.ui.fragment.base.BaseFragment;
import com.demo.panguso.mvp_mode.mvp.view.PhotoDetailOnClickEvent;
import com.demo.panguso.mvp_mode.utils.RxBus;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by ${yangfang} on 2016/10/28.
 */
public class PhotoDetailFragment extends BaseFragment {
    private String imgSrc;
    @BindView(R.id.photoview)
    PhotoView mPhotoView;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;


    @Override
    public void initInjector() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_photo_detail;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initArgument();
    }

    @Override
    public void initViews(View view) {
        mProgressBar.setVisibility(View.VISIBLE);
        initPhotoView();
        //点击图片新闻的事件
        setPhotoViewClickEvent();
    }

    private void initPhotoView() {
        mSubscription = Observable.timer(100, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        mProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mProgressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Glide.with(App.getAppContext()).load(imgSrc).asBitmap()
                                .format(DecodeFormat.PREFER_ARGB_8888)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .error(R.mipmap.ic_load_fail)
                                .into(mPhotoView);
                    }
                });
    }

    private void setPhotoViewClickEvent() {
        mPhotoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float v, float v1) {
                handleOnTabEvent();
            }

            @Override
            public void onOutsidePhotoTap() {
                handleOnTabEvent();
            }
        });
    }

    private void handleOnTabEvent() {
        RxBus.getInstance().post(new PhotoDetailOnClickEvent());
    }

    private void initArgument() {
        if (getArguments() != null) {
            imgSrc = getArguments().getString(Constants.PHOTO_DETAIL_IMGSRC);
        }
    }
}
