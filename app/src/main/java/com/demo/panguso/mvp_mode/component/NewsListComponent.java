package com.demo.panguso.mvp_mode.component;

import com.demo.panguso.mvp_mode.module.NewsListModule;
import com.demo.panguso.mvp_mode.mvp.ui.fragment.NewsFragment;

import dagger.Component;

/**
 * Created by ${yangfang} on 2016/9/14.
 */
@Component(modules = {NewsListModule.class})
public interface NewsListComponent {
    void inject(NewsFragment newsFragment);
}
