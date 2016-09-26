package com.demo.panguso.mvp_mode.inject.component;

import android.content.Context;

import com.demo.panguso.mvp_mode.inject.module.ApplicationModule;
import com.demo.panguso.mvp_mode.inject.scope.ContextLife;
import com.demo.panguso.mvp_mode.inject.scope.PerApp;

import dagger.Component;

/**
 * Created by ${yangfang} on 2016/9/26.
 * 使用Dagger为了获得App的全局变量
 */

@PerApp
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
//    void inject(App app);
    @ContextLife("Application")
    Context getApplication();
}
