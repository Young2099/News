package com.demo.panguso.mvp_mode.interactor;

import java.util.List;

/**
 * Created by ${yangfang} on 2016/9/9.
 */
public interface NewsInteractor {

    interface OnFinishedListener {
        void onFinished(List<String> items);
    }

    void loadNews(OnFinishedListener listener);
}
