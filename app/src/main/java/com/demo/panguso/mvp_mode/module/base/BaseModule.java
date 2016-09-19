package com.demo.panguso.mvp_mode.module.base;

import com.demo.panguso.mvp_mode.mvp.view.base.BaseView;

/**
 * Created by ${yangfang} on 2016/9/18.
 */
public class BaseModule<T extends BaseView> {
    protected T mView;
}
