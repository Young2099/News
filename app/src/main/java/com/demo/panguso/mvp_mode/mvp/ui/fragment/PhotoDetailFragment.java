package com.demo.panguso.mvp_mode.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.demo.panguso.mvp_mode.R;
import com.demo.panguso.mvp_mode.app.App;
import com.demo.panguso.mvp_mode.common.Constants;
import com.demo.panguso.mvp_mode.mvp.ui.fragment.base.BaseFragment;
import com.demo.panguso.mvp_mode.mvp.view.PhotoDetailOnClickEvent;
import com.demo.panguso.mvp_mode.utils.RxBus;

import butterknife.BindView;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by ${yangfang} on 2016/10/28.
 */
public class PhotoDetailFragment extends BaseFragment {
    private String imgSrc;
    @BindView(R.id.photoview)
    PhotoView mPhotoView;

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
        Glide.with(App.getAppContext()).load(imgSrc).asBitmap()
                .format(DecodeFormat.PREFER_ARGB_8888)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.mipmap.ic_load_fail)
                .into(mPhotoView);
        //点击图片新闻的事件
        setPhotoClickEvent();
    }

    private void setPhotoClickEvent() {
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
