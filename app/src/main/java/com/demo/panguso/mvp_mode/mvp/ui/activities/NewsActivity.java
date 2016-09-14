package com.demo.panguso.mvp_mode.mvp.ui.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.demo.panguso.mvp_mode.R;
import com.demo.panguso.mvp_mode.mvp.ui.activities.base.BaseActivity;
import com.demo.panguso.mvp_mode.mvp.ui.adapter.NewsFragmetPagerAdapter;
import com.demo.panguso.mvp_mode.mvp.ui.fragment.NewsFragment;
import com.demo.panguso.mvp_mode.utils.SharedPreferencesUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tabs)
    TabLayout mTabs;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.navigation_view)
    NavigationView mNavView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    FloatingActionButton mFab;
    private ArrayList<Fragment> mNewsFragmentList ;
//    protected void initViews() {
//        mBaseNavView = mNavView;
//
//    }
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.activity_news;
//    }
//
//    @Override
//    protected void initInjector() {
//
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open_drawer, R.string.close_drawer);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        mNavView.setNavigationItemSelectedListener(this);
        initFragment();
        initViewPager();
    }

    private void initViewPager() {
        initFragment();
        //设置tabLayout的模式
        mTabs.setTabMode(TabLayout.MODE_FIXED);
        //为TabLayout添加tba的名称
        mTabs.addTab(mTabs.newTab().setText("要闻"));
        mTabs.addTab(mTabs.newTab().setText("科技"));
        mTabs.addTab(mTabs.newTab().setText("娱乐"));
        NewsFragmetPagerAdapter adapter = new NewsFragmetPagerAdapter(getSupportFragmentManager(), mNewsFragmentList);
        mViewPager.setAdapter(adapter);
        //TabLayour加载viewpager
        mTabs.setupWithViewPager(mViewPager);
    }

    private void initFragment() {
        NewsFragment newsFragment1 = new NewsFragment();
        NewsFragment newsFragment2 = new NewsFragment();
        NewsFragment newsFragment3 = new NewsFragment();

        mNewsFragmentList = new ArrayList<>();
        mNewsFragmentList.add(newsFragment1);
        mNewsFragmentList.add(newsFragment2);
        mNewsFragmentList.add(newsFragment3);

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_news) {

        } else if (id == R.id.nav_photo) {

        } else if (id == R.id.nav_video) {

        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_about) {
            return true;
        }
        if(id == R.id.nav_night_mode){
            if(SharedPreferencesUtil.getIsNightMode()){
                changeToDay();
                SharedPreferencesUtil.setIsNightMode(false);
            }else {
                changeToNight();
                SharedPreferencesUtil.setIsNightMode(true);
            }
        }
        return super.onOptionsItemSelected(item);
    }

}
