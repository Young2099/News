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
import com.demo.panguso.mvp_mode.common.Constants;
import com.demo.panguso.mvp_mode.component.DaggerNewsComponent;
import com.demo.panguso.mvp_mode.module.NewsModule;
import com.demo.panguso.mvp_mode.mvp.bean.NewsSummary;
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

    private String channelId;
    private String channelType;
    private int startPage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("NewsFragment",",,,,,");
        if(getArguments() != null){
            channelId = getArguments().getString(Constants.NEWS_ID);
            channelType = getArguments().getString(Constants.NEWS_TYPE);
            startPage = getArguments().getInt(Constants.CHANNEL_POSITION);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("NewsFragment","2222222");

        View view = inflater.inflate(R.layout.fragment_news, container, false);

        mNewsRV = (RecyclerView) view.findViewById(R.id.news_rv);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        mNewsRV.setHasFixedSize(true);
        mNewsRV.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        DaggerNewsComponent.builder()
                .newsModule(new NewsModule(this,channelId,channelType,startPage))
                .build()
                .inject(this);
        mNewsPresenter.onCreate();
        return view;
    }
//
//    @Override
//    public void showProgress() {
//        mProgressBar.setVisibility(View.VISIBLE);
//    }
//
//    @Override
//    public void hideProgress() {
//        mProgressBar.setVisibility(View.GONE);
//    }
//

    @Override
    public void setItems(List<NewsSummary> items) {
        Log.e("NewsFragment","33333");
        mNewsRecyclerViewAdapter.setItems(items);
        mNewsRV.setAdapter(mNewsRecyclerViewAdapter);
    }


}
