package com.demo.panguso.mvp_mode.inject.module;

import android.app.Service;
import android.content.Context;

import com.demo.panguso.mvp_mode.inject.scope.ContextLife;
import com.demo.panguso.mvp_mode.inject.scope.PerService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ${yangfang} on 2016/9/26.
 */
@Module
public class ServiceModule {
    private Service mService;

    public ServiceModule(Service service) {
        mService = service;
    }

    @Provides
    @PerService
    @ContextLife("Service")
    public Context providerServiceContext() {
        return mService;
    }
}
