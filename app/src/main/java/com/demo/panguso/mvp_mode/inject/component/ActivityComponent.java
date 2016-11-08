package com.demo.panguso.mvp_mode.inject.component;

import android.app.Activity;
import android.content.Context;

import com.demo.panguso.mvp_mode.inject.module.ActivityModule;
import com.demo.panguso.mvp_mode.inject.scope.ContextLife;
import com.demo.panguso.mvp_mode.inject.scope.PerActivity;
import com.demo.panguso.mvp_mode.mvp.ui.activities.NewsActivity;
import com.demo.panguso.mvp_mode.mvp.ui.activities.NewsChannelActivity;
import com.demo.panguso.mvp_mode.mvp.ui.activities.NewsDetailActivity;
import com.demo.panguso.mvp_mode.mvp.ui.activities.PhotoActivity;
import com.demo.panguso.mvp_mode.mvp.ui.activities.NewsPhotoDetailActivity;

import dagger.Component;

/**
 * Created by ${yangfang} on 2016/9/26.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class})
public interface ActivityComponent {
    Activity activity();

    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();

    void inject(NewsActivity newsActivity);

    void inject(NewsDetailActivity newsDetailActivity);

    void inject(NewsChannelActivity newsChannelActivity);

    void inject(NewsPhotoDetailActivity photoDetailActivity);

    void inject(PhotoActivity photoDetailActivity);
}
