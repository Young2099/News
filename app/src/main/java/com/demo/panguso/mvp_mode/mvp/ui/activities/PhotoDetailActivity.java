package com.demo.panguso.mvp_mode.mvp.ui.activities;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.demo.panguso.mvp_mode.R;
import com.demo.panguso.mvp_mode.common.Constants;
import com.demo.panguso.mvp_mode.inject.scope.ContextLife;
import com.demo.panguso.mvp_mode.mvp.bean.PhotoDetaiView;
import com.demo.panguso.mvp_mode.mvp.presenter.impl.PhotoDetailPresenterImpl;
import com.demo.panguso.mvp_mode.mvp.ui.activities.base.BaseActivity;
import com.demo.panguso.mvp_mode.mvp.view.PullBackLayout;
import com.demo.panguso.mvp_mode.utils.MyUtils;
import com.demo.panguso.mvp_mode.utils.PhotoRequestType;
import com.demo.panguso.mvp_mode.utils.SystemUnivisibilityUtil;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import dagger.Lazy;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;


public class PhotoDetailActivity extends BaseActivity implements PullBackLayout.Callback, PhotoDetaiView {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.photo_iv)
    ImageView mPhotoView;
    @BindView(R.id.pull_back_layout)
    PullBackLayout mPullBackLayout;
    @BindView(R.id.photo_touch_iv)
    PhotoView mPhotoTouchView;
    private boolean mIsToolBarHidden;
    private boolean mIsStatusBarHidden;
    private ColorDrawable mBackGround;

    /**
     * 使用dagger2延迟加载
     */
    @Inject
    Lazy<PhotoDetailPresenterImpl> mPhotoDetailPresenter;

    @Inject
    @ContextLife("Activity")
    Context mContext;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initViews() {
        mToolbar.setTitle(R.string.photo);
        initImageView();
        initBackground();
        setPhotoViewClickEvent();
//        initPresenter();
    }

    private void initPresenter() {
//        在这时才创建mPhotoDetailPresenter,以后每次调用get会得到同一个mPhotoDetailPresenter对象
        mPresenter = mPhotoDetailPresenter.get();
        mPresenter.attachView(this);
    }

    @Override
    public void supportFinishAfterTransition() {
        super.supportFinishAfterTransition();
    }

    private void initBackground() {
        mBackGround = new ColorDrawable(Color.BLACK);
        MyUtils.getRootView(this).setBackgroundDrawable(mBackGround);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_photo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                handlePicture(PhotoRequestType.TYPE_SHARE);
                return true;
            case R.id.action_save:
                handlePicture(PhotoRequestType.TYPE_SAVE);
                return true;
            case R.id.action_set_wallpaper:
                handlePicture(PhotoRequestType.TYPE_SET_WALLPAPER);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void handlePicture(int type) {
        initPresenter();
        mPhotoDetailPresenter.get().handlePicture(getIntent().getStringExtra(Constants.PHOTO_DETAIL), type);
    }

    /**
     * 获取图片，插入到控件中去
     */
    private void initImageView() {
//        Glide.with(this)
//                .load(getIntent().getStringExtra(Constants.PHOTO_DETAIL))
//                .asBitmap()
//                .placeholder(R.mipmap.ic_photo_empty)
//                .error(R.mipmap.ic_load_fail)
//                .into(mPhotoView);
//        loadPhotoTouchIv();
        loadPhotoIv();
    }

    private void loadPhotoIv() {
//        Glide.with(this)
//                .load(getIntent().getStringExtra(Constants.PHOTO_DETAIL))
//                .asBitmap()
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(mPhotoView);
        Picasso.with(this)
                .load(getIntent().getStringExtra(Constants.PHOTO_DETAIL))
                .error(R.mipmap.ic_load_fail)
                .into(mPhotoView);
    }

    private void setPhotoViewClickEvent() {
        mPhotoTouchView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float v, float v1) {
                hideOrShowToolbar();
                hideOrShowStatusBar();
            }

            @Override
            public void onOutsidePhotoTap() {
                hideOrShowToolbar();
                hideOrShowStatusBar();
            }
        });
    }

    private void hideOrShowStatusBar() {
        if (mIsStatusBarHidden) {
            SystemUnivisibilityUtil.enter(PhotoDetailActivity.this);
        } else {
            SystemUnivisibilityUtil.exit(PhotoDetailActivity.this);
        }
        mIsStatusBarHidden = !mIsStatusBarHidden;
    }

    private void hideOrShowToolbar() {
        mToolbar.animate()
                .alpha(mIsToolBarHidden ? 1.0f : 0.0f)
                .setInterpolator(new DecelerateInterpolator(2))
                .start();
        mIsToolBarHidden = !mIsToolBarHidden;
    }

    private void loadPhotoTouchIv() {
        Picasso.with(this)
                .load(getIntent().getStringExtra(Constants.PHOTO_DETAIL))
//                .placeholder(R.mipmap.ic_photo_empty)
                .error(R.mipmap.ic_load_fail)
                .into(mPhotoTouchView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_photo_detail;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPullBackLayout.setCallback(this);
        initLazyLoadView();
    }

    private void initLazyLoadView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getEnterTransition().addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionStart(Transition transition) {

                }

                @Override
                public void onTransitionEnd(Transition transition) {
                    showToolBarAndPhotoTouchView();
                }

                @Override
                public void onTransitionCancel(Transition transition) {

                }

                @Override
                public void onTransitionPause(Transition transition) {

                }

                @Override
                public void onTransitionResume(Transition transition) {

                }
            });
        } else {
            showToolBarAndPhotoTouchView();
        }
    }

    private void showToolBarAndPhotoTouchView() {
        mIsToolBarHidden = true;
        hideOrShowToolbar();
        loadPhotoTouchIv();
    }


    @Override
    public void onPullStart() {
        mIsToolBarHidden = false;
        hideOrShowToolbar();
        mIsStatusBarHidden = true;
        hideOrShowStatusBar();
    }

    @Override
    public void onPull(float progress) {
        progress = Math.min(1f, progress * 3f);
        Log.e("TAG", ":::" + (int) (0xff/*255*/ * (1f - progress)));
        mBackGround.setAlpha((int) (0xff/*255*/ * (1f - progress)));
    }

    @Override
    public void onPullCancle() {
        mIsToolBarHidden = true;
        hideOrShowToolbar();
    }

    @Override
    public void onPullComplete() {
        supportFinishAfterTransition();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showErrorMsg(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAfterTransition();
    }
}
