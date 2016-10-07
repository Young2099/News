package com.demo.panguso.mvp_mode.mvp.ui.fragment;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.demo.panguso.mvp_mode.R;
import com.demo.panguso.mvp_mode.app.App;
import com.demo.panguso.mvp_mode.common.Constants;
import com.demo.panguso.mvp_mode.common.LoadNewsType;
import com.demo.panguso.mvp_mode.listener.OnItemClickListener;
import com.demo.panguso.mvp_mode.mvp.bean.NewsSummary;
import com.demo.panguso.mvp_mode.mvp.presenter.impl.NewsListPresenterImpl;
import com.demo.panguso.mvp_mode.mvp.ui.activities.NewsDetailActivity;
import com.demo.panguso.mvp_mode.mvp.ui.adapter.NewsRecyclerViewAdapter;
import com.demo.panguso.mvp_mode.mvp.ui.fragment.base.BaseFragment;
import com.demo.panguso.mvp_mode.mvp.view.NewsListView;
import com.demo.panguso.mvp_mode.utils.NetUtil;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import static android.support.v7.widget.RecyclerView.LayoutManager;
import static android.support.v7.widget.RecyclerView.OnScrollListener;
/**
 * Created by ${yangfang} on 2016/9/9.
 */
public class NewsListFragment extends BaseFragment implements NewsListView, OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    NewsRecyclerViewAdapter mNewsRecyclerViewAdapter;
    @Inject
    NewsListPresenterImpl mNewsPresenter;

    @Inject
    Activity mActivity;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.news_rv)
    RecyclerView mNewsRV;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    private boolean mIsRefreshing = false;
    private String channelId;
    private String channelType;
    private boolean mIsAllLoaded;
    private int startPage;
    private boolean isRefreshing;

    @Override
    public void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_news;
    }

    @Override
    public void initViews(View view) {
        initSwifreshLayout();
        initRecycleView();
        initPresenter();

    }

    private void initRecycleView() {
        mNewsRV.setHasFixedSize(true);
        mNewsRV.setLayoutManager(new WrapperLinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mNewsRV.setItemAnimator(new DefaultItemAnimator());

        mNewsRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LayoutManager layoutManager = recyclerView.getLayoutManager();
                int lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                int visibileItemCount = layoutManager.getChildCount();
                int totalItmeCount = layoutManager.getItemCount();
                if (!mIsAllLoaded && visibileItemCount > 0 && newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItemPosition >= totalItmeCount - 1) {
                    mNewsPresenter.loadMore();
                    mSwipeRefreshLayout.setRefreshing(false);
                    mNewsRecyclerViewAdapter.showFooter();
                    mNewsRV.scrollToPosition(mNewsRecyclerViewAdapter.getItemCount() - 1);
                }
            }
        });
        mNewsRecyclerViewAdapter.setOnItemClickListener(this);
        mNewsRV.setAdapter(mNewsRecyclerViewAdapter);
    }

    private void initSwifreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(mActivity, R.color.colorPrimary));
    }

    private void initPresenter() {
        mNewsPresenter.onItemClicked(channelType, channelId);
        mPresenter = mNewsPresenter;
        mPresenter.attachView(this);
        mPresenter.onCreate();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initValues();
        NetUtil.checkNetworkState(mActivity.getString(R.string.internet_error));
    }

    private void initValues() {
        if (getArguments() != null) {
            channelId = getArguments().getString(Constants.NEWS_ID);
            channelType = getArguments().getString(Constants.NEWS_TYPE);
            startPage = getArguments().getInt(Constants.CHANNEL_POSITION);
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
    public void setItems(List<NewsSummary> items, @LoadNewsType.checker int loadType) {
        switch (loadType) {
            case LoadNewsType.TYPE_REFRESH_SUCCESS:
                mSwipeRefreshLayout.setRefreshing(false);
                mNewsRecyclerViewAdapter.setItems(items);
                mNewsRecyclerViewAdapter.notifyDataSetChanged();
                break;
            case LoadNewsType.TYPE_REFRESH_ERROR:
                mSwipeRefreshLayout.setRefreshing(false);
                break;
            case LoadNewsType.TYPE_LOAD_MORE_SUCCESS:
                mNewsRecyclerViewAdapter.hideFooter();
                if (items == null || items.size() == 0) {
                    mIsAllLoaded = true;
                    Snackbar.make(mNewsRV, getString(R.string.no_more), Snackbar.LENGTH_LONG).show();
                } else {
                    mNewsRecyclerViewAdapter.addMore(items);
                }
                break;
            case LoadNewsType.TYPE_LOAD_MORE_ERROR:
                mNewsRecyclerViewAdapter.hideFooter();
                break;
        }
    }

    /**
     * 针对每个Itme设置的点击事件
     *
     * @param itemView
     * @param layoutPosition
     */
    @Override
    public void onItemClick(View itemView, int layoutPosition) {
        startNewDetailActivity(itemView, layoutPosition);
    }

    private void startNewDetailActivity(View view, int position) {
        List<NewsSummary> newsSummaryList = mNewsRecyclerViewAdapter.getmNewsList();
        Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
        intent.putExtra(Constants.NEWS_POST_ID, newsSummaryList.get(position).getPostid());
        intent.putExtra(Constants.NEWS_IMG_RES, newsSummaryList.get(position).getImgsrc());
        ImageView newsSummaryPhoto = (ImageView) view.findViewById(R.id.news_summary_photo_iv);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(mActivity, newsSummaryPhoto, Constants.TRANSITION_ANIMATION_NEWS_PHOTOS);
            startActivity(intent, options.toBundle());
        } else {
            ActivityOptionsCompat options = ActivityOptionsCompat.makeScaleUpAnimation(view, view.getWidth() / 2, view.getHeight() / 2, 0, 0);
            ActivityCompat.startActivity(mActivity, intent, options.toBundle());
        }
    }

    @Override
    public void onRefresh() {
        mNewsPresenter.resfreshData();
    }

    private class WrapperLinearLayoutManager extends LinearLayoutManager {

        public WrapperLinearLayoutManager(Context context) {
            super(context);
        }

        public WrapperLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        public WrapperLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }
        @Override
        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
            try {
                super.onLayoutChildren(recycler, state);
            } catch (IndexOutOfBoundsException e) {
                Log.e("probelem", "meet a IOOBE in RecyclerView");
            }
        }
    }

}
