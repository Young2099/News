package com.demo.panguso.mvp_mode.inject.component;

import com.demo.panguso.mvp_mode.inject.module.NewsModule;
import com.demo.panguso.mvp_mode.mvp.ui.activities.NewsActivity;

import dagger.Component;

/**
 * Created by ${yangfang} on 2016/9/19.
 */
@Component(modules = {NewsModule.class})
public interface NewsComponent {
    void inject(NewsActivity newsActivity);
}
