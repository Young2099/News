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
import com.demo.panguso.mvp_mode.inject.component.DaggerNewsChannelComponent;
import com.demo.panguso.mvp_mode.inject.module.NewsChannelModule;
import com.demo.panguso.mvp_mode.mvp.presenter.NewsChannelPresenter;
import com.demo.panguso.mvp_mode.mvp.ui.activities.base.BaseActivity;
import com.demo.panguso.mvp_mode.mvp.ui.adapter.NewsFragmetPagerAdapter;
import com.demo.panguso.mvp_mode.mvp.ui.fragment.NewsListFragment;
import com.demo.panguso.mvp_mode.mvp.view.NewsChannelView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import greendao.NewsChannelTable;

public class NewsActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, NewsChannelView {

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
    NewsChannelPresenter mNewsChannelPresenter;
    private ArrayList<Fragment> mNewsFragmentList = new ArrayList<>();
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
        //适配
        setStatusBarTranslucent();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open_drawer, R.string.close_drawer);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        mNavView.setNavigationItemSelectedListener(this);
        DaggerNewsChannelComponent.builder().newsChannelModule(new NewsChannelModule(this))
                .build()
                .inject(this);
//        mNewsChannelPresenter.onCreate();
        mPresenter = mNewsChannelPresenter;
        mPresenter.onCreate();
    }

    @Override
    public void initViewPager(List<NewsChannelTable> list) {
        List<String> channelName = new ArrayList<>();
        if (list != null) {
            for (NewsChannelTable channelTable : list) {
                NewsListFragment newsFragment = createListFragment(channelTable);
                mNewsFragmentList.add(newsFragment);
                channelName.add(channelTable.getNewsChannelName());
            }
        }
        mTabs.setTabMode(TabLayout.MODE_FIXED);
        NewsFragmetPagerAdapter adapter = new NewsFragmetPagerAdapter(getSupportFragmentManager(), channelName, mNewsFragmentList);
        mViewPager.setAdapter(adapter);
        mTabs.setupWithViewPager(mViewPager);
    }

    private NewsListFragment createListFragment(NewsChannelTable channelTable) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.NEWS_ID, channelTable.getNewsChannelId());
        bundle.putString(Constants.NEWS_TYPE, channelTable.getNewsChannelType());
        bundle.putInt(Constants.CHANNEL_POSITION, channelTable.getNewsChannelIndex());
        Log.e("TAG", "{channelTable.getNewsChannelId()}" + channelTable.getNewsChannelIndex());
        Log.e("TAG", "{channelTable.getNewsChannelName()}" + channelTable.getNewsChannelName());
        Log.e("TAG", "{channelTable.getNewsChannelType()}" + channelTable.getNewsChannelType());
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
    public void showErrorMsg(String message) {

    }

    @SuppressWarnings("StatementWithEmptyBody")
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
        return super.onOptionsItemSelected(item);
    }


}
