package com.demo.panguso.mvp_mode.inject.component;

import android.app.Activity;
import android.content.Context;

import com.demo.panguso.mvp_mode.inject.module.FragmentModule;
import com.demo.panguso.mvp_mode.inject.scope.ContextLife;
import com.demo.panguso.mvp_mode.inject.scope.PerFragment;
import com.demo.panguso.mvp_mode.mvp.ui.fragment.NewsListFragment;

import dagger.Component;

/**
 * Created by ${yangfang} on 2016/9/26.
 */

@PerFragment
@Component(dependencies = ApplicationComponent.class,modules = {FragmentModule.class})
public interface FragmentComponent {
    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();

    Activity getActivity();

    void inject(NewsListFragment newsListFragment);
}
