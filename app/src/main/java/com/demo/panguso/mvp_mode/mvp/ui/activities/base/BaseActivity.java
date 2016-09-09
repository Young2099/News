package com.demo.panguso.mvp_mode.mvp.ui.activities.base;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


/**
 * Created by ${yangfang} on 2016/9/7.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private Toolbar toolbar;
    private Class mClass;
    public NavigationView mBaseNavView;
    protected boolean mIsHasNavigationView;
//
//    /**
//     * 初始化布局
//     */
//    protected abstract void initViews();
//
//    protected abstract int getLayoutId();
//
//    protected abstract void initInjector();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        int layoutId = getLayoutId();
//        setContentView(layoutId);
//        initInjector();
//        ButterKnife.bind(this);
//        initToolBar();
//        initViews();
//        if(mIsHasNavigationView){
//            initDrawerLayout();
//
//        }
//        initNightModeSwitch();
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
//    private void initToolBar() {
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main,menu);
//        return super.onCreateOptionsMenu(menu);
//    }
}
