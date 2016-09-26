package com.demo.panguso.mvp_mode.inject.component;

import android.app.Activity;
import android.content.Context;

import com.demo.panguso.mvp_mode.inject.module.ActivityModule;
import com.demo.panguso.mvp_mode.inject.scope.ContextLife;
import com.demo.panguso.mvp_mode.inject.scope.PerActivity;

import dagger.Component;

/**
 * Created by ${yangfang} on 2016/9/26.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class,modules = {ActivityModule.class})
public interface ActivityComponent {
    Activity activity();
    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();
}
