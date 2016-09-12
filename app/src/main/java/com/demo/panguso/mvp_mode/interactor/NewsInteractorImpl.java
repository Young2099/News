package com.demo.panguso.mvp_mode.interactor;

import android.os.Handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ${yangfang} on 2016/9/9.
 */
public class NewsInteractorImpl implements NewsInteractor {

    List<String> list = new ArrayList<>();

    @Override
    public void loadNews(final OnFinishedListener listener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                listener.onFinished(createArrayList());
            }
        }, 2000);

    }

    private List<String> createArrayList() {
        return Arrays.asList("Item 1",
                "Item 2",
                "Item 3",
                "Item 4", "Item 5",
                "Item 6",
                "Item 7",
                "Item 8",
                "Item 9");
    }

}
