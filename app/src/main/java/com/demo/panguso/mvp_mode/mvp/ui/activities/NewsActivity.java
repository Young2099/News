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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.demo.panguso.mvp_mode.R;
import com.demo.panguso.mvp_mode.common.Constants;
import com.demo.panguso.mvp_mode.component.DaggerNewsComponent;
import com.demo.panguso.mvp_mode.module.NewsModule;
import com.demo.panguso.mvp_mode.mvp.presenter.NewsPresenter;
import com.demo.panguso.mvp_mode.mvp.ui.activities.base.BaseActivity;
import com.demo.panguso.mvp_mode.mvp.ui.adapter.NewsFragmetPagerAdapter;
import com.demo.panguso.mvp_mode.mvp.ui.fragment.NewsFragment;
import com.demo.panguso.mvp_mode.mvp.view.NewsView;
import com.demo.panguso.mvp_mode.utils.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import greendao.NewsChannelTable;

public class NewsActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, NewsView {

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

    @Inject
    NewsPresenter mNewsPresenter;

    private List<Fragment> mNewsFragmentList = new ArrayList<>();
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
//        initFragment();
        DaggerNewsComponent.builder()
                .newsModule(new NewsModule(this))
                .build().inject(this);
        mNewsPresenter.onCreate();

    }

//
//    private void initFragment() {
//        NewsFragment newsFragment1 = new NewsFragment();
//        NewsFragment newsFragment2 = new NewsFragment();
//        NewsFragment newsFragment3 = new NewsFragment();
//
//        mNewsFragmentList = new ArrayList<>();
//        mNewsFragmentList.add(newsFragment1);
//        mNewsFragmentList.add(newsFragment2);
//        mNewsFragmentList.add(newsFragment3);
//
//    }

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
        if (id == R.id.nav_night_mode) {
            if (SharedPreferencesUtil.getIsNightMode()) {
                changeToDay();
                SharedPreferencesUtil.setIsNightMode(false);
            } else {
                changeToNight();
                SharedPreferencesUtil.setIsNightMode(true);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initViewPager(List<NewsChannelTable> data) {
        List<String> channelNames = new ArrayList<>();
        if (data != null) {
            for (NewsChannelTable newsChannel : data) {
                NewsFragment newsFragment = createListFragment(newsChannel.getNewsChannelId(), newsChannel.getNewsChannelType()
                        , newsChannel.getNewsChannelIndex());
                mNewsFragmentList.add(newsFragment);
                channelNames.add(newsChannel.getNewsChannelName());
                Log.e("TAG","/////)))))"+newsChannel.getNewsChannelName());
                Log.e("TAG","/////)))))"+newsChannel.getNewsChannelType());
            }
            //设置TabLayout的模式
            mTabs.setTabMode(TabLayout.MODE_FIXED);
            NewsFragmetPagerAdapter adapter = new NewsFragmetPagerAdapter(getSupportFragmentManager(), channelNames,mNewsFragmentList);
            mViewPager.setAdapter(adapter);
            mTabs.setupWithViewPager(mViewPager);
        }
    }

    private NewsFragment createListFragment(String channelId, String newsType, int newsChannelIndex) {
        NewsFragment fragment = new NewsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.NEWS_ID, channelId);
        bundle.putString( Constants.NEWS_TYPE, newsType);
        bundle.putInt(Constants.CHANNEL_POSITION, newsChannelIndex);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showErrorMsg(String message) {

    }

    @Override
    public void onDestory() {

    }
}
