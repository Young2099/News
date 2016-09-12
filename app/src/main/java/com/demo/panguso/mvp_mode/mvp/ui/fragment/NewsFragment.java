package com.demo.panguso.mvp_mode.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.demo.panguso.mvp_mode.R;
import com.demo.panguso.mvp_mode.component.DaggerNewsComponent;
import com.demo.panguso.mvp_mode.module.NewsModule;
import com.demo.panguso.mvp_mode.mvp.presenter.NewsPresenter;
import com.demo.panguso.mvp_mode.mvp.ui.adapter.NewsRecyclerViewAdapter;
import com.demo.panguso.mvp_mode.mvp.ui.fragment.base.BaseFragment;
import com.demo.panguso.mvp_mode.mvp.view.NewsView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by ${yangfang} on 2016/9/9.
 */
public class NewsFragment extends BaseFragment implements NewsView {

    RecyclerView mNewsRV;
    ProgressBar mProgressBar;

    @Inject
    NewsRecyclerViewAdapter mNewsRecyclerViewAdapter;
    @Inject
    NewsPresenter mNewsPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        mNewsRV = (RecyclerView) view.findViewById(R.id.news_rv);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        mNewsRV.setHasFixedSize(true);
        mNewsRV.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        DaggerNewsComponent.builder()
                .newsModule(new NewsModule(this))
                .build()
                .inject(this);
        mNewsPresenter.onCreateView();
        return view;
    }

    @Override
    public void setItems(List<String> items) {
        mNewsRecyclerViewAdapter.setItems(items);
        mNewsRV.setAdapter(mNewsRecyclerViewAdapter);
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showMsg(String message) {

    }

    @Override
    public void onDestoryView() {
        mNewsPresenter.onDestory();
    }
}
