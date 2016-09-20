package com.demo.panguso.mvp_mode.mvp.ui.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import com.demo.panguso.mvp_mode.R;
import com.demo.panguso.mvp_mode.component.DaggerNewsDetailComponent;
import com.demo.panguso.mvp_mode.module.NewsDetailModule;
import com.demo.panguso.mvp_mode.mvp.bean.NewsDetail;
import com.demo.panguso.mvp_mode.mvp.presenter.NewsDetailPresenter;
import com.demo.panguso.mvp_mode.mvp.view.NewsDetailView;

import javax.inject.Inject;

/**
 * Created by ${yangfang} on 2016/9/20.
 */
public class NewsDetailActivity extends AppCompatActivity implements NewsDetailView {

    @Inject
    NewsDetailPresenter mNewsDetailPresenter;
    private String postId;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_newsdetail);

        DaggerNewsDetailComponent.builder()
                .newsDetailModule(new NewsDetailModule(this, postId))
                .build()
                .inject(this);
        mNewsDetailPresenter.onCreate();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showErrorMsg(String message) {

    }

    @Override
    public void initDetailNews(NewsDetail newsDetail) {

    }
}
