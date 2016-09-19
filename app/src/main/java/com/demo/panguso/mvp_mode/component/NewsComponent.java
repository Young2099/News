package com.demo.panguso.mvp_mode.component;

import com.demo.panguso.mvp_mode.module.NewsModule;
import com.demo.panguso.mvp_mode.mvp.ui.fragment.NewsFragment;

import dagger.Component;

/**
 * Created by ${yangfang} on 2016/9/9.
 */
@Component(modules = {NewsModule.class})
public interface NewsComponent {
    void inject(NewsFragment newsActivity);
}
