package com.demo.panguso.mvp_mode.mvp.view;

import com.demo.panguso.mvp_mode.common.LoadNewsType;
import com.demo.panguso.mvp_mode.mvp.bean.PhotoGirl;
import com.demo.panguso.mvp_mode.mvp.view.base.BaseView;

import java.util.List;

/**
 * Created by ${yangfang} on 2016/11/4.
 */

public interface PhotoView extends BaseView {
    void initPhotoList(List<PhotoGirl> photoGirls, @LoadNewsType.checker int loadType);
}
