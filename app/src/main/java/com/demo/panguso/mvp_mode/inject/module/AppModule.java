package com.demo.panguso.mvp_mode.inject.module;

import android.app.Application;

import com.demo.panguso.mvp_mode.app.App;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ${yangfang} on 2016/9/23.
 * 将App类变为全局变量
 */
@Module
public class AppModule {
    private App mApp;

    public AppModule(App app) {
        mApp = app;
    }

    @Provides
    @Singleton
    public Application providerApplication() {
        return mApp;
    }


}
