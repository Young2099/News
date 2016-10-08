package com.demo.panguso.mvp_mode.mvp.ui.activities;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.demo.panguso.mvp_mode.R;
import com.demo.panguso.mvp_mode.mvp.presenter.impl.NewsChannelPresenterImpl;
import com.demo.panguso.mvp_mode.mvp.ui.activities.base.BaseActivity;
import com.demo.panguso.mvp_mode.mvp.ui.adapter.NewsChannelAdapter;
import com.demo.panguso.mvp_mode.mvp.view.ItemDragHelperCallback;
import com.demo.panguso.mvp_mode.mvp.view.NewsChannelView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import greendao.NewsChannelTable;

public class NewsChannelActivity extends BaseActivity implements NewsChannelView {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.news_channel_mine_rv)
    RecyclerView mNewsChannelMineRv;
    @BindView(R.id.news_channel_more_rv)
    RecyclerView mNewsChannelMoreRv;

    @Inject
    NewsChannelPresenterImpl mChannelPresenter;

    @Override
    protected void initViews() {
        mPresenter = mChannelPresenter;
        mPresenter.attachView(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_channel;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initSupportActionBar() {
        setSupportActionBar(mToolbar);
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

    /**
     * 拿到 我的频道管理和增加更多的频道管理
     *
     * @param newsChannelsMine
     * @param newsChannelsMore
     */
    @Override
    public void initRecyclerViews(List<NewsChannelTable> newsChannelsMine, List<NewsChannelTable> newsChannelsMore) {
        initRecyclerView(mNewsChannelMineRv, newsChannelsMine,true);
        initRecyclerView(mNewsChannelMoreRv, newsChannelsMore,false);

    }

    private void initRecyclerView(RecyclerView recyclerView, List<NewsChannelTable> newsChannelsMine, boolean isUseItemDragHelper) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,4, LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        NewsChannelAdapter newsChannelAdapter = new NewsChannelAdapter(newsChannelsMine);
        recyclerView.setAdapter(newsChannelAdapter);
        initItemDragHelper(isUseItemDragHelper,newsChannelAdapter);
    }

    private void initItemDragHelper(boolean isUseItemDragHelper, NewsChannelAdapter newsChannelAdapter) {
        if(isUseItemDragHelper){
            ItemDragHelperCallback itemDragHelperCallback = new ItemDragHelperCallback(newsChannelAdapter);
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragHelperCallback);
            itemTouchHelper.attachToRecyclerView(mNewsChannelMineRv);
        }
    }
}
