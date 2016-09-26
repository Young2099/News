package com.demo.panguso.mvp_mode.inject.component;

import android.content.Context;

import com.demo.panguso.mvp_mode.inject.module.ServiceModule;
import com.demo.panguso.mvp_mode.inject.scope.ContextLife;
import com.demo.panguso.mvp_mode.inject.scope.PerService;

import dagger.Component;

/**
 * Created by ${yangfang} on 2016/9/26.
 */
@PerService
@Component(dependencies = ApplicationComponent.class,modules = {ServiceModule.class})
public interface ServiceComponent {
    @ContextLife("Service")
    Context getServiceContext();

    @ContextLife("Application")
    Context getApplicationContext();
}
