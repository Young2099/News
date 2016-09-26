package com.demo.panguso.mvp_mode.inject.module;

import android.app.Activity;
import android.content.Context;

import com.demo.panguso.mvp_mode.inject.scope.ContextLife;
import com.demo.panguso.mvp_mode.inject.scope.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ${yangfang} on 2016/9/26.
 */
@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    @PerActivity
    @ContextLife("Activity")
    public Context ProviderActivityContect(){
        return mActivity;
    }

    @Provides
    @PerActivity
    public Activity providerActivity() {
        return mActivity;
    }
}
