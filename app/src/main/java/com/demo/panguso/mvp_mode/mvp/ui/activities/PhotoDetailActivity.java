package com.demo.panguso.mvp_mode.mvp.ui.activities;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bumptech.glide.Glide;
import com.demo.panguso.mvp_mode.R;
import com.demo.panguso.mvp_mode.common.Constants;
import com.demo.panguso.mvp_mode.mvp.ui.activities.base.BaseActivity;
import com.demo.panguso.mvp_mode.utils.MyUtils;

import butterknife.BindView;
import ooo.oxo.library.widget.PullBackLayout;
import uk.co.senab.photoview.PhotoView;


public class PhotoDetailActivity extends BaseActivity implements PullBackLayout.Callback {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.photo_iv)
    PhotoView mPhotoView;
    @BindView(R.id.pull_back_layout)
    PullBackLayout mPullBackLayout;

    private ColorDrawable mBackGround;

    @Override
    protected void initViews() {
        mToolbar.setTitle(R.string.photo);
        initImageView();
        initBackground();
    }

    private void initBackground() {
        mBackGround = new ColorDrawable(Color.BLACK);
        MyUtils.getRootView(this).setBackgroundDrawable(mBackGround);
    }

    /**
     * 获取图片，插入到控件中去
     */
    private void initImageView() {
        Glide.with(this)
                .load(getIntent().getStringExtra(Constants.PHOTO_DETAIL))
                .asBitmap()
                .placeholder(R.mipmap.ic_photo_empty)
                .error(R.mipmap.ic_load_fail)
                .into(mPhotoView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_photo_detail;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPullBackLayout.setCallback(this);
    }

    @Override
    public void onPullStart() {
        startAnimation(View.GONE, 0.9f, 0.5f);
    }

    private void startAnimation(final int endState, float startValue, float endValue) {
        ObjectAnimator animator =ObjectAnimator.ofFloat(mToolbar
        ,"alpha",startValue,endValue)
                .setDuration(200);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mToolbar.setAlpha(1.0f);
                mToolbar.setVisibility(endState);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.start();
    }

    @Override
    public void onPull(float progress) {
        progress = Math.min(1f, progress * 3f);
        mBackGround.setAlpha((int) (0xff/*255*/*(1f-progress)));
    }

    @Override
    public void onPullCancel() {
        startAnimation(View.VISIBLE,0.9f,0.5f);
    }

    @Override
    public void onPullComplete() {
        supportFinishAfterTransition();
    }
}
