package com.demo.panguso.mvp_mode.mvp.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${yangfang} on 2016/9/9.
 */
public class NewsFragmetPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> list = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    public NewsFragmetPagerAdapter(FragmentManager fm, List<String> channelName, List<Fragment> mNewsFragmentList) {
        super(fm);
        list = mNewsFragmentList;
        titles = channelName;
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
        return titles.get(position);
    }
}
