package com.demo.panguso.mvp_mode.component;

import com.demo.panguso.mvp_mode.module.NewsChannelModule;
import com.demo.panguso.mvp_mode.mvp.ui.activities.NewsActivity;

import dagger.Component;

/**
 * Created by ${yangfang} on 2016/9/19.
 */
@Component(modules = {NewsChannelModule.class})
public interface NewsChannelComponent {
    void inject(NewsActivity newsActivity);
}
