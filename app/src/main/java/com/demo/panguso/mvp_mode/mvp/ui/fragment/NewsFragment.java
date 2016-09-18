package com.demo.panguso.mvp_mode.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.demo.panguso.mvp_mode.R;
import com.demo.panguso.mvp_mode.app.App;
import com.demo.panguso.mvp_mode.common.Constants;
import com.demo.panguso.mvp_mode.component.DaggerNewsListComponent;
import com.demo.panguso.mvp_mode.module.NewsListModule;
import com.demo.panguso.mvp_mode.mvp.bean.NewsSummary;
import com.demo.panguso.mvp_mode.mvp.presenter.NewsListPresenter;
import com.demo.panguso.mvp_mode.mvp.ui.adapter.NewsRecyclerViewAdapter;
import com.demo.panguso.mvp_mode.mvp.ui.fragment.base.BaseFragment;
import com.demo.panguso.mvp_mode.mvp.view.NewsListView;
import com.demo.panguso.mvp_mode.utils.NetUtil;
import com.demo.panguso.mvp_mode.utils.ToastUtil;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by ${yangfang} on 2016/9/9.
 */
public class NewsFragment extends BaseFragment implements NewsListView {

    RecyclerView mNewsRV;
    ProgressBar mProgressBar;
    private String mNewsId;
    private String mNewsType;
    private int mStartPage;
    @Inject
    NewsRecyclerViewAdapter mNewsRecyclerViewAdapter;
    @Inject
    NewsListPresenter mNewsListPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNewsId = getArguments().getString(Constants.NEWS_ID);
            Log.e("TAG","PPPPPP"+mNewsId);
            mNewsType = getArguments().getString(Constants.NEWS_TYPE);
            mStartPage = getArguments().getInt(Constants.CHANNEL_POSITION);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        mNewsRV = (RecyclerView) view.findViewById(R.id.news_rv);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        mNewsRV.setHasFixedSize(true);

        mNewsRV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        DaggerNewsListComponent.builder()
                .newsListModule(new NewsListModule(this, mNewsId, mNewsType))
                .build()
                .inject(this);
        mNewsListPresenter.onCreate();
        checkNetState();
        return view;
    }

    private void checkNetState() {
        if (!NetUtil.isNetworkAvailable(App.getAppContext())) {
            ToastUtil.showToast(getActivity(), "网络不好", 0);
        }
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
    public void showMessage(String message) {

    }

    @Override
    public void showErrorMsg(String message) {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onDestory() {
        mNewsListPresenter.onDestory();
    }

    @Override
    public void setItems(List<NewsSummary> items) {
        Log.e("tag","hhhhhhh"+items.get(1).getTitle());
        mNewsRecyclerViewAdapter.setItems(items);
        mNewsRV.setAdapter(mNewsRecyclerViewAdapter);
    }

}
