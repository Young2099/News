package com.demo.panguso.mvp_mode.app;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.RefWatcher;
/**
 * Created by ${yangfang} on 2016/9/12.
 */
public class App extends Application {
    private RefWatcher refWatcher;
    //内存泄露检测
    public static RefWatcher getWatcher(Context context){
        App application = (App) context.getApplicationContext();
        return application.refWatcher;
    }

    private static Context appContext;

    public static Context getAppContext(){
        return appContext;
    }

    /**
     * release版本用此方法
     */
    protected RefWatcher installLeakeCanary(){
        return RefWatcher.DISABLED;
//        KLog.init(BuildConfig.LOG_DEBUG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
    }
}
