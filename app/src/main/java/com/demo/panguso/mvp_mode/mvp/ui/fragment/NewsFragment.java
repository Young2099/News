package com.demo.panguso.mvp_mode.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.demo.panguso.mvp_mode.R;
import com.demo.panguso.mvp_mode.mvp.presenter.NewsPresenter;
import com.demo.panguso.mvp_mode.mvp.presenter.impl.NewsPresenterImpl;
import com.demo.panguso.mvp_mode.mvp.ui.adapter.NewsRecyclerViewAdapter;
import com.demo.panguso.mvp_mode.mvp.ui.fragment.base.BaseFragment;
import com.demo.panguso.mvp_mode.mvp.view.NewsView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${yangfang} on 2016/9/9.
 */
public class NewsFragment extends BaseFragment implements NewsView {

    RecyclerView mNewsRV;
    ProgressBar mProgressBar;

    private List<String> mNewsList;
    private NewsRecyclerViewAdapter mNewsRecyclerViewAdapter;
    private NewsPresenter mNewsPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        mNewsRV = (RecyclerView) view.findViewById(R.id.news_rv);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        mNewsRV.setHasFixedSize(true);
        mNewsList = new ArrayList<>();
        mNewsRecyclerViewAdapter = new NewsRecyclerViewAdapter(mNewsList);
        mNewsPresenter = new NewsPresenterImpl(this);
        mNewsPresenter.onCreateView();
        return view;
    }

    @Override
    public void setItems(List<String> items) {
        mNewsList.clear();
        mNewsList.addAll(items);
        mNewsRecyclerViewAdapter.notifyDataSetChanged();
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
}
