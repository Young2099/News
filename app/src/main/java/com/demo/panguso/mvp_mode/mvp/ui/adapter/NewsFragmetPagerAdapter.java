package com.demo.panguso.mvp_mode.mvp.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by ${yangfang} on 2016/9/9.
 */
public class NewsFragmetPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> list;
    private final String[] titles = {"要闻", "科技", "娱乐"};

    public NewsFragmetPagerAdapter(FragmentManager fm, List<Fragment> mNewsFragmentList) {
        super(fm);
        list = mNewsFragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
