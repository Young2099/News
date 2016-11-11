package com.demo.panguso.mvp_mode.mvp.presenter;

import com.demo.panguso.mvp_mode.utils.PhotoRequestType;

/**
 * Created by ${yangfang} on 2016/11/10.
 * 接口作为分享的控制器
 */
public interface PhotoDetailPresenter {
    void handlePicture(String imageUrl, @PhotoRequestType.PhotoRequestTypeChecker int type);
}
