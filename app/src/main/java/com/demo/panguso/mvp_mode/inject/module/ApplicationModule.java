package com.demo.panguso.mvp_mode.inject.module;

import android.content.Context;

import com.demo.panguso.mvp_mode.app.App;
import com.demo.panguso.mvp_mode.inject.scope.ContextLife;
import com.demo.panguso.mvp_mode.inject.scope.PerApp;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ${yangfang} on 2016/9/23.
 * 将App类变为全局变量
 */
@Module
public class ApplicationModule {
    private App mApplication;

    public ApplicationModule(App application) {
        mApplication = application;
    }

    @Provides
    @PerApp
    @ContextLife("Application")
    public Context providerApplication() {
        return mApplication.getApplicationContext();
    }


}
