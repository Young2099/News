package com.demo.panguso.mvp_mode.response;

/**
 * Created by ${yangfang} on 2016/9/13.
 */
public interface RequestCallBack<T> {
    void success(T data);

    void onError(String errorMsg);

    void beforeRequest();
}
