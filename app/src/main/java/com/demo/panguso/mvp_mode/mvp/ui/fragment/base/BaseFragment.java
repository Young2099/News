package com.demo.panguso.mvp_mode.mvp.ui.fragment.base;


import android.support.v4.app.Fragment;

import com.demo.panguso.mvp_mode.mvp.view.NewsView;

import java.util.List;

import greendao.NewsChannelTable;

/**
 * Created by ${yangfang} on 2016/9/9.
 */
public class BaseFragment extends Fragment implements NewsView{


    @Override
    public void initViewPager(List<NewsChannelTable> data) {

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
