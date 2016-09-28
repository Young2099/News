package com.demo.panguso.mvp_mode.mvp.ui.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.demo.panguso.mvp_mode.R;
import com.demo.panguso.mvp_mode.app.App;
import com.demo.panguso.mvp_mode.common.Constants;
import com.demo.panguso.mvp_mode.inject.component.DaggerNewsListComponent;
import com.demo.panguso.mvp_mode.inject.module.NewsListModule;
import com.demo.panguso.mvp_mode.listener.OnItemClickListener;
import com.demo.panguso.mvp_mode.mvp.bean.NewsSummary;
import com.demo.panguso.mvp_mode.mvp.presenter.NewsListPresenter;
import com.demo.panguso.mvp_mode.mvp.ui.activities.NewsDetailActivity;
import com.demo.panguso.mvp_mode.mvp.ui.adapter.NewsRecyclerViewAdapter;
import com.demo.panguso.mvp_mode.mvp.ui.fragment.base.BaseFragment;
import com.demo.panguso.mvp_mode.mvp.view.NewsListView;
import com.demo.panguso.mvp_mode.utils.NetUtil;
import com.demo.panguso.mvp_mode.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by ${yangfang} on 2016/9/9.
 */
public class NewsListFragment extends BaseFragment implements NewsListView, OnItemClickListener {

    RecyclerView mNewsRV;
    ProgressBar mProgressBar;

    @Inject
    NewsRecyclerViewAdapter mNewsRecyclerViewAdapter;
    @Inject
    NewsListPresenter mNewsPresenter;

    private String channelId;
    private String channelType;
    private int startPage;
    List<NewsSummary> newsSummaries = new ArrayList<>();

    @Override
    public void initInjector() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            channelId = getArguments().getString(Constants.NEWS_ID);
            channelType = getArguments().getString(Constants.NEWS_TYPE);
            startPage = getArguments().getInt(Constants.CHANNEL_POSITION);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        init(view);
        checkNetState();
        return view;
    }

    private void init(View view) {
        mNewsRV = (RecyclerView) view.findViewById(R.id.news_rv);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        mNewsRV.setHasFixedSize(true);
        mNewsRV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        DaggerNewsListComponent.builder()
                .newsListModule(new NewsListModule(this, channelId, channelType, startPage))
                .build()
                .inject(this);
        mPresenter = mNewsPresenter;
        mPresenter.onCreate();
        mNewsRecyclerViewAdapter.setOnItemClickListener(this);
    }

    private void checkNetState() {
        if (!NetUtil.isNetworkAvailable(App.getAppContext())) {
            ToastUtil.showToast(getActivity(), getString(R.string.internet_error), 0);
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
    public void showErrorMsg(String message) {
        mProgressBar.setVisibility(View.GONE);
        if (NetUtil.isNetworkAvailable(App.getAppContext())) {
            Snackbar.make(mNewsRV, message, Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDestroy() {
        mNewsPresenter.onDestory();
        super.onDestroy();
    }

    @Override
    public void setItems(List<NewsSummary> items) {
        this.newsSummaries = items;
        mNewsRecyclerViewAdapter.setItems(items);
        mNewsRV.setAdapter(mNewsRecyclerViewAdapter);
    }

    /**
     * 针对每个Itme设置的点击事件
     *
     * @param itemView
     * @param layoutPosition
     */
    @Override
    public void onItemClick(View itemView, int layoutPosition) {
        startNewDetailActivity(itemView, newsSummaries, layoutPosition);
    }

    private void startNewDetailActivity(View view, List<NewsSummary> newsSummaries, int position) {
        Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
        intent.putExtra(Constants.NEWS_POST_ID, newsSummaries.get(position).getPostid());
        intent.putExtra(Constants.NEWS_IMG_RES, newsSummaries.get(position).getImgsrc());
        ImageView newsSummaryPhoto = (ImageView) view.findViewById(R.id.news_summary_photo_iv);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), newsSummaryPhoto, Constants.TRANSITION_ANIMATION_NEWS_PHOTOS);
            startActivity(intent, options.toBundle());
        } else {
            ActivityOptionsCompat options = ActivityOptionsCompat.makeScaleUpAnimation(view, view.getWidth() / 2, view.getHeight() / 2, 0, 0);
            ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
        }
    }
}
