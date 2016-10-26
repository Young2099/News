package com.demo.panguso.mvp_mode.mvp.ui.activities;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.demo.panguso.mvp_mode.R;
import com.demo.panguso.mvp_mode.listener.ChannelItemMoveEvent;
import com.demo.panguso.mvp_mode.listener.OnItemClickListener;
import com.demo.panguso.mvp_mode.mvp.presenter.impl.NewsChannelPresenterImpl;
import com.demo.panguso.mvp_mode.mvp.ui.activities.base.BaseActivity;
import com.demo.panguso.mvp_mode.mvp.ui.adapter.NewsChannelAdapter;
import com.demo.panguso.mvp_mode.mvp.view.ItemDragHelperCallback;
import com.demo.panguso.mvp_mode.mvp.view.NewsChannelView;
import com.demo.panguso.mvp_mode.utils.RxBus;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import greendao.NewsChannelTable;
import rx.Subscription;
import rx.functions.Action1;

public class NewsChannelActivity extends BaseActivity implements NewsChannelView {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.news_channel_mine_rv)
    RecyclerView mNewsChannelMineRv;
    @BindView(R.id.news_channel_more_rv)
    RecyclerView mNewsChannelMoreRv;

    @Inject
    NewsChannelPresenterImpl mChannelPresenter;

    private NewsChannelAdapter mNewsChannelAdapterMine;
    private NewsChannelAdapter mNewsChannelAdapterMore;

    @Override
    protected void initViews() {
        mPresenter = mChannelPresenter;
        mPresenter.attachView(this);
        mToolbar.setTitle("频道管理");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    private Subscription mSubscription = RxBus.getInstance().toObservable(ChannelItemMoveEvent.class)
            .subscribe(new Action1<ChannelItemMoveEvent>() {
                @Override
                public void call(ChannelItemMoveEvent channelItemMoveEvent) {
                    int fromPosition = channelItemMoveEvent.getFromPosition();
                    int toPosition = channelItemMoveEvent.getToPosition();
                    mChannelPresenter.onItemSwap(fromPosition, toPosition);
                }
            });

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSubscription.unsubscribe();
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
        initRecyclerView(mNewsChannelMineRv, newsChannelsMine, true);
        initRecyclerView(mNewsChannelMoreRv, newsChannelsMore, false);

    }

    private void initRecyclerView(RecyclerView recyclerView, List<NewsChannelTable> newsChannels, boolean isChannelMine) {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4, LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        if (isChannelMine) {
            mNewsChannelAdapterMine = new NewsChannelAdapter(newsChannels);
            recyclerView.setAdapter(mNewsChannelAdapterMine);
            setChannelMineOnItemClick();
            initItemDragHelper();
        } else {
            mNewsChannelAdapterMore = new NewsChannelAdapter(newsChannels);
            recyclerView.setAdapter(mNewsChannelAdapterMore);
            setChannelMoreOrItemClick();
        }
    }

    /**
     * 点击更多频道添加到我的频道
     */
    private void setChannelMoreOrItemClick() {
        mNewsChannelAdapterMore.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int layoutPosition) {
                NewsChannelTable newsChannelTable = mNewsChannelAdapterMore.getData().get(layoutPosition);
                mNewsChannelAdapterMine.add(mNewsChannelAdapterMine.getItemCount(), newsChannelTable);
                mNewsChannelAdapterMore.delete(layoutPosition);
                mChannelPresenter.onItemAddOrRemove(newsChannelTable, false);
            }
        });
    }

    /**
     * 点击我的频道删除，添加到更多频道
     */
    private void setChannelMineOnItemClick() {
        mNewsChannelAdapterMine.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int layoutPosition) {
                NewsChannelTable newsChannel = mNewsChannelAdapterMine.getData().get(layoutPosition);
                boolean isNewsChannelFixed = newsChannel.getNewsChannelFixed();
                if (!isNewsChannelFixed) {
                    mNewsChannelAdapterMore.add(mNewsChannelAdapterMore.getItemCount(), newsChannel);
                    mNewsChannelAdapterMine.delete(layoutPosition);
                    mChannelPresenter.onItemAddOrRemove(newsChannel, true);
                }
            }
        });
    }

    /**
     * 针对我添加的频道做出的变化。长按排序功能
     */
    private void initItemDragHelper() {
        ItemDragHelperCallback itemDragHelperCallback = new ItemDragHelperCallback(mNewsChannelAdapterMine);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragHelperCallback);
        itemTouchHelper.attachToRecyclerView(mNewsChannelMineRv);
        mNewsChannelAdapterMine.setItemDragHelperCallback(itemDragHelperCallback);
    }
}
