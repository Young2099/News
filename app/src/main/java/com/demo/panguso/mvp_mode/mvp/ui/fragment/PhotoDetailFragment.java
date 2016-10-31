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

import butterknife.BindView;
import uk.co.senab.photoview.PhotoView;

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
    }

    private void initArgument() {
        if (getArguments() != null) {
            imgSrc = getArguments().getString(Constants.PHOTO_DETAIL_IMGSRC);
        }
    }
}
