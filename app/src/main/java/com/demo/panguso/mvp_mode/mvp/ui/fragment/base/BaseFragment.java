package com.demo.panguso.mvp_mode.mvp.ui.fragment.base;


import android.support.v4.app.Fragment;

import com.demo.panguso.mvp_mode.mvp.bean.NewsSummary;
import com.demo.panguso.mvp_mode.mvp.view.NewsView;

import java.util.List;

/**
 * Created by ${yangfang} on 2016/9/9.
 */
public class BaseFragment extends Fragment implements NewsView{


    @Override
    public void setItems(List<NewsSummary> items) {

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

}
