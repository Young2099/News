package com.demo.panguso.mvp_mode.mvp.ui.activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.demo.panguso.mvp_mode.R;
import com.demo.panguso.mvp_mode.app.Application;
import com.demo.panguso.mvp_mode.common.Constants;
import com.demo.panguso.mvp_mode.common.URLImageGetter;
import com.demo.panguso.mvp_mode.inject.component.DaggerNewsDetailComponent;
import com.demo.panguso.mvp_mode.inject.module.NewsDetailModule;
import com.demo.panguso.mvp_mode.mvp.bean.NewsDetail;
import com.demo.panguso.mvp_mode.mvp.presenter.NewsDetailPresenter;
import com.demo.panguso.mvp_mode.mvp.ui.activities.base.BaseActivity;
import com.demo.panguso.mvp_mode.mvp.view.NewsDetailView;
import com.demo.panguso.mvp_mode.utils.DebugUtil;
import com.demo.panguso.mvp_mode.utils.MyUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ${yangfang} on 2016/9/20.
 */
public class NewsDetailActivity extends BaseActivity implements NewsDetailView {

    private static final String TAG = "NewsDetailActivity";
    @Inject
    NewsDetailPresenter mNewsDetailPresenter;
    private String postId;

    @BindView(R.id.news_detail_photo_iv)
    ImageView mNewsDetailPhotoIv;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.news_detail_body_tv)
    TextView mNewsDetailBodyTv;
    @BindView(R.id.news_detail_from_tv)
    TextView mNewsDetailFromTv;
    @BindView(R.id.news_detail_title_tv)
    TextView mNewsDetailTitleTv;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.mask_view)
    View mMaskView;

    URLImageGetter mUrlImageGetter;

    @Override
    protected void initViews() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_detail;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_news_detail);
        init();
    }

    private void init() {
        ButterKnife.bind(this);
        if (getIntent() != null) {
            postId = getIntent().getStringExtra(Constants.NEWS_POST_ID);
        }
        setSupportActionBar(mToolbar);
        DaggerNewsDetailComponent.builder()
                .newsDetailModule(new NewsDetailModule(this, postId))
                .build()
                .inject(this);
//        mNewsDetailPresenter.onCreate();
        mPresenter = mNewsDetailPresenter;
        mPresenter.onCreate();
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showErrorMsg(String message) {
        mProgressBar.setVisibility(View.GONE);
        Snackbar.make(mAppBarLayout, message, Snackbar.LENGTH_LONG);
    }

    @Override
    public void initDetailNews(NewsDetail newsDetail) {
        String newsTitle = newsDetail.getTitle();
        String newsSource = newsDetail.getSource();
        String newsTime = MyUtils.formatDate(newsDetail.getPtime());
        String newsBody = newsDetail.getBody();
        String imgsrc = getPhotoSrc(newsDetail);
        mNewsDetailTitleTv.setText(newsTitle);
        mNewsDetailFromTv.setText(getString(R.string.news_from, newsSource, newsTime));
        setNewsDetailPhoto(imgsrc);
        setNewsBody(newsDetail, newsBody);
        setToolBarLayout(newsTitle);
    }

    private void setNewsBody(NewsDetail newsDetail, String newsBody) {
        if (mNewsDetailBodyTv != null) {
            if (Application.isHavePhoto() && newsDetail.getImg().size() >= 2) {
                int total = newsDetail.getImg().size();
                mUrlImageGetter = new URLImageGetter(mNewsDetailBodyTv, newsBody, total);
                mNewsDetailBodyTv.setText(Html.fromHtml(newsBody, mUrlImageGetter, null));

            } else {
                mNewsDetailBodyTv.setText(Html.fromHtml(newsBody));
            }
        }
    }

    private void setToolBarLayout(String newsTitle) {
        mCollapsingToolbarLayout.setTitle(newsTitle);
        mCollapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, R.color.primary_text_white));
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.primary_text_white));
    }

    private void setNewsDetailPhoto(String imgsrc) {
        Glide.with(Application.getAppContext()).load(imgsrc).asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .format(DecodeFormat.PREFER_ARGB_8888)
                .error(R.mipmap.ic_launcher)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        mNewsDetailPhotoIv.setImageBitmap(resource);
                        mMaskView.setVisibility(View.VISIBLE);
                    }
                });
    }

    private String getPhotoSrc(NewsDetail newsDetail) {
        List<NewsDetail.ImgBean> imgSrc = newsDetail.getImg();
        String imgsrc;
        if (imgSrc != null && imgSrc.size() > 0) {
            imgsrc = imgSrc.get(0).getSrc();
        } else {
            imgsrc = getIntent().getStringExtra(Constants.NEWS_IMG_RES);
        }
        return imgsrc;
    }

    @Override
    protected void onDestroy() {
        cancleUrlImageGetterSubscrition();
        super.onDestroy();
    }

    private void cancleUrlImageGetterSubscrition() {
        if (mUrlImageGetter != null && mUrlImageGetter.mSubscription != null
                && !mUrlImageGetter.mSubscription.isUnsubscribed()) {
            {
                mUrlImageGetter.mSubscription.unsubscribe();
                DebugUtil.e(TAG, "UrlImageGetter unsubscribe");
            }
        }
    }
}
