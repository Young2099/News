package com.demo.panguso.mvp_mode.inject.component;

import com.demo.panguso.mvp_mode.inject.module.NewsListModule;
import com.demo.panguso.mvp_mode.mvp.ui.fragment.NewsListFragment;

import dagger.Component;

/**
 * Created by ${yangfang} on 2016/9/20.
 */

@Component(modules = {NewsListModule.class})
public interface NewsListComponent {
    void inject(NewsListFragment newsListFragment);
}
