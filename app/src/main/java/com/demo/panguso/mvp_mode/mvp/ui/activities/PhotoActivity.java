package com.demo.panguso.mvp_mode.mvp.ui.activities;

import com.demo.panguso.mvp_mode.common.LoadNewsType;
import com.demo.panguso.mvp_mode.mvp.bean.PhotoGirl;
import com.demo.panguso.mvp_mode.mvp.presenter.impl.PhotoPresenterImpl;
import com.demo.panguso.mvp_mode.mvp.ui.activities.base.BaseActivity;
import com.demo.panguso.mvp_mode.mvp.view.PhotoView;

import java.util.List;

import javax.inject.Inject;

public class PhotoActivity extends BaseActivity implements PhotoView {
    @Inject
    PhotoPresenterImpl mPhotoPresenter;


    @Override
    protected void initViews() {
        mPhotoPresenter.attachView(this);
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initInjector() {

    }

    /**
     * 得到图片数据的方法
     *  @param photoGirls
     * @param loadType
     */
    @Override
    public void initPhotoList(List<PhotoGirl> photoGirls, @LoadNewsType.checker int loadType) {

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
}
