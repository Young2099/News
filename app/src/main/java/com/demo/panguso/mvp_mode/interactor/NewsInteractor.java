package com.demo.panguso.mvp_mode.interactor;

/**
 * Created by ${yangfang} on 2016/9/9.
 */
public interface NewsInteractor<T> {

    /**
     * 改为泛型的模式，包含任意的类型
     * @param <T>
     */

    interface OnFinishedListener<T> {
        void onFinished(T items);
    }

    void loadNews(OnFinishedListener<T> listener);
}
