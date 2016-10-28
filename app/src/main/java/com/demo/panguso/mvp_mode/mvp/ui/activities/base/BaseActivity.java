package com.demo.panguso.mvp_mode.mvp.ui.activities.base;

import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.demo.panguso.mvp_mode.R;
import com.demo.panguso.mvp_mode.app.App;
import com.demo.panguso.mvp_mode.inject.component.ActivityComponent;
import com.demo.panguso.mvp_mode.inject.component.DaggerActivityComponent;
import com.demo.panguso.mvp_mode.inject.module.ActivityModule;
import com.demo.panguso.mvp_mode.mvp.presenter.base.BasePresenter;
import com.demo.panguso.mvp_mode.utils.SharedPreferencesUtil;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.squareup.leakcanary.RefWatcher;

import butterknife.ButterKnife;


/**
 * Created by ${yangfang} on 2016/9/7.
 */
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    public Toolbar toolbar;
    private Class mClass;
    public NavigationView mBaseNavView;
    protected boolean mIsHasNavigationView;
    private boolean isAddView;
    private View mNightView = null;
    private WindowManager mWindowManager = null;
    protected T mPresenter;

    protected ActivityComponent mActivityComponent;

    public ActivityComponent getmActivityComponent() {
        return mActivityComponent;
    }

    /**
     * 初始化布局
     */
    protected abstract void initViews();

    protected abstract int getLayoutId();

    protected abstract void initInjector();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setNightOrDayMode();
        mActivityComponent = DaggerActivityComponent.builder()
                .applicationComponent(((App) getApplication()).getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .build();
        int layoutId = getLayoutId();
        setContentView(layoutId);
        initInjector();
        ButterKnife.bind(this);
        initToolBar();
        initViews();
//        if(mIsHasNavigationView){
//            initDrawerLayout();
//
//        }
//        initNightModeSwitch();
        if (mPresenter != null) {
            mPresenter.onCreate();
        }
    }

    //TODO:适配4.4
    protected void setStatusBarTranslucent() {
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.colorPrimary);
        }
    }

    private void setNightOrDayMode() {
        if (SharedPreferencesUtil.getIsNightMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            initNightView();
            mNightView.setBackgroundResource(R.color.night_mask);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

    }

    //    private void initNightModeSwitch() {
//        if(this instanceof NewsActivity || this instanceof PhotoActivity){
//            SwitchCompat dayNightSwitch = mBaseNavView.getMenu().findItem(R.)
//        }
//    }
//
//    private void initDrawerLayout() {
//        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
//        //监听DrawerLayou的事件。
//        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.open_drawer,R.string.close_drawer);
//        mDrawerLayout.addDrawerListener(drawerToggle);
//        //将DrawerLayout和ActionBar相关联
//        drawerToggle.syncState();
//
//        //针对navigationView的选择事件的选取
//        if(navigationView != null){
//            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//                @Override
//                public boolean onNavigationItemSelected(MenuItem item) {
//                    switch (item.getItemId()){
//                        case R.id.nav_news:
//                            mClass = NewsActivity.class;
//                            break;
//                        case R.id.nav_photo:
//                            mClass = PhotoActivity.class;
//                            break;
//                        case R.id.nav_video:
//                            ToastUtil.showToast(BaseActivity.this,"hh",0);
//                            break;
//                        default:
//                            break;
//                    }
//                    mDrawerLayout.closeDrawer(GravityCompat.START);
//                    return false;
//                }
//
//            });
//        }
//        mDrawerLayout.addDrawerListener(new SimpleDrawerListener(){
//            @Override
//            public void onDrawerClosed(View drawerView) {
//                super.onDrawerClosed(drawerView);
//                if(mClass != null){
//                    Intent intent = new Intent(BaseActivity.this,mClass);
//                    startActivity(intent);
//                    overridePendingTransition(0,0);
//                    mClass = null;
//                }
//            }
//        });
//    }
//
    private void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("新闻");
        setSupportActionBar(toolbar);
    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main,menu);
//        return super.onCreateOptionsMenu(menu);
//    }


    private void initNightView() {
        if (isAddView) {
            return;
        }

        //  增加夜间模式蒙版
        WindowManager.LayoutParams nightParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE |
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSPARENT);


        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mNightView = new View(this);
        mWindowManager.addView(mNightView, nightParams);
        isAddView = true;
    }

    public void changeToNight() {
        getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        initNightView();
        mNightView.setBackgroundResource(R.color.night_mask);
    }

    public void changeToDay() {
        getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        mNightView.setBackgroundResource(R.color.transparent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    finishAfterTransition();
                } else {
                    finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestory();
        }
        RefWatcher refWatcher = App.getWatcher(this);
        refWatcher.watch(this);
        if (isAddView) {
            //移除夜间模式蒙版
            mWindowManager.removeViewImmediate(mNightView);
            mWindowManager = null;
            mNightView = null;
        }
    }
}
