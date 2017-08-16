package com.demo.panguso.mvp_mode.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;

import com.demo.panguso.mvp_mode.BuildConfig;
import com.demo.panguso.mvp_mode.common.Constants;
import com.demo.panguso.mvp_mode.inject.component.ApplicationComponent;
import com.demo.panguso.mvp_mode.inject.component.DaggerApplicationComponent;
import com.demo.panguso.mvp_mode.inject.module.ApplicationModule;
import com.demo.panguso.mvp_mode.utils.DebugUtil;
import com.demo.panguso.mvp_mode.utils.SharedPreferencesUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import de.greenrobot.dao.query.QueryBuilder;
import greendao.DaoMaster;
import greendao.DaoSession;
import greendao.NewsChannelTableDao;

/**
 * Created by ${yangfang} on 2016/9/12.
 */
public class App extends Application {
    private RefWatcher refWatcher;
    private static DaoSession mDaoSession;
    private static ApplicationComponent mAppComponent;

    //内存泄露检测
    public static RefWatcher getWatcher(Context context) {
        App application = (App) context.getApplicationContext();
        return application.refWatcher;
    }

    private static Context appContext;

    public static Context getAppContext() {
        return appContext;
    }

    public static ApplicationComponent getApplicationComponent() {
        return mAppComponent;
    }
//
//    /**
//     * release版本用此方法
//     */
//    protected RefWatcher installLeakeCanary() {
//        return RefWatcher.DISABLED;
////        KLog.init(BuildConfig.LOG_DEBUG);
//    }

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        initLeakCanary();
        initActivityLifecycleLogs();
        Fresco.initialize(this);
//        installLeakeCanary();
        //官方推荐将获取DaoMaster对象的方法放到Application层，这样将避免多次创建生成session对象
        initStricMode();
        setUpDataBase();
        initAppComponent();
    }

    private void initAppComponent() {
        mAppComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }


    private void initActivityLifecycleLogs() {
        this.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {
                DebugUtil.e("=========", activity + "  onActivityCreated");
            }

            @Override
            public void onActivityStarted(Activity activity) {
                DebugUtil.e("=========", activity + "  onActivityStarted");
            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                DebugUtil.e("=========", activity + "  onActivityDestroyed");
            }
        });
    }

    private void initLeakCanary() {
        if (BuildConfig.DEBUG) {
            refWatcher = LeakCanary.install(this);
        } else {
            refWatcher = installLeakCanary();
        }
    }

    /**
     * release版本用此方法
     *
     * @return
     */
    private RefWatcher installLeakCanary() {
        return RefWatcher.DISABLED;
    }

    private void initStricMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                    new StrictMode.ThreadPolicy.Builder()
                            .detectAll()
                            .penaltyLog()//在logcat中打印违规异常信息
                            .build()
            );
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog().build());
        }
    }

    public static NewsChannelTableDao getNewsChannelTableDao() {
        return mDaoSession.getNewsChannelTableDao();
    }

    private void setUpDataBase() {
        //通过DaoMaster的内部类DevOpenHelper，你可以得到一个便利的SQLiteOpenHelper 对象
        //这里不需要通过去编写CREATE TABLE这样的SQL语句，是因为greeDao已经帮你做了
        //注意：默认的DaoMaster.DevOpenHelper会在数据库升级时,删除所有的表，意味着这将导致数据的丢失
        //所以在正式的项目里面。做一层封装，来实数据库的安全升级。
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, Constants.DB_NAME, null);
        SQLiteDatabase db = helper.getWritableDatabase();
        //注意这里的数据库数据DaoMaster，所以多个Session指的是相同的数据库连接。
        DaoMaster daoMaster = new DaoMaster(db);
        mDaoSession = daoMaster.newSession();
        //在QueryBuilder类中内置两个Flag用于方便输出执行的SQL语句与传递参数的值;
        QueryBuilder.LOG_SQL = BuildConfig.DEBUG;
        QueryBuilder.LOG_VALUES = BuildConfig.DEBUG;
    }

    public static boolean isHavePhoto() {
        return SharedPreferencesUtil.getIsHavePhoto();
    }

}
