package com.demo.panguso.mvp_mode.inject.module;

import android.app.Application;
import android.content.Context;

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
    private Application mApplication;

    public ApplicationModule(Application context) {
        mApplication = context;
    }

    @Provides
    @PerApp
    @ContextLife("Application")
    public Context providerApplication() {
        return mApplication.getApplicationContext();
    }


}
