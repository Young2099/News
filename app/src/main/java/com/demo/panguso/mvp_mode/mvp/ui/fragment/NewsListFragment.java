package com.demo.panguso.mvp_mode.mvp.ui.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.demo.panguso.mvp_mode.R;
import com.demo.panguso.mvp_mode.common.Constants;
import com.demo.panguso.mvp_mode.common.LoadNewsType;
import com.demo.panguso.mvp_mode.mvp.bean.NewsPhotoDetail;
import com.demo.panguso.mvp_mode.mvp.bean.NewsSummary;
import com.demo.panguso.mvp_mode.mvp.event.ScrollToTopEvent;
import com.demo.panguso.mvp_mode.mvp.presenter.impl.NewsListPresenterImpl;
import com.demo.panguso.mvp_mode.mvp.ui.activities.NewsDetailActivity;
import com.demo.panguso.mvp_mode.mvp.ui.activities.NewsPhotoDetailActivity;
import com.demo.panguso.mvp_mode.mvp.ui.adapter.NewsRecyclerViewAdapter;
import com.demo.panguso.mvp_mode.mvp.ui.fragment.base.BaseFragment;
import com.demo.panguso.mvp_mode.mvp.view.NewsListView;
import com.demo.panguso.mvp_mode.utils.NetUtil;
import com.demo.panguso.mvp_mode.utils.RxBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.functions.Action1;

//import android.support.v4.content.ContextCompat;

/**
 * Created by ${yangfang} on 2016/9/9.
 * 新闻详情页面
 */
public class NewsListFragment extends BaseFragment implements NewsListView, NewsRecyclerViewAdapter.OnNewsListItemClickListener, SwipeRefreshLayout.OnRefreshListener {

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
    @BindView(R.id.empty_view)
    TextView mEmptyView;

    private String channelId;
    private String channelType;
    private boolean mIsAllLoaded;

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
        registerScrollToTopEvent();
    }

    private void registerScrollToTopEvent() {
        mSubscription = RxBus.getInstance().toObservable(ScrollToTopEvent.class)
                .subscribe(new Action1<ScrollToTopEvent>() {
                    @Override
                    public void call(ScrollToTopEvent scrollToTopEvent) {
                        mNewsRV.getLayoutManager().scrollToPosition(0);
                    }
                });

    }

    private void initRecycleView() {
        mNewsRV.setHasFixedSize(true);
        mNewsRV.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mNewsRV.setItemAnimator(new DefaultItemAnimator());
        mNewsRV.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });

        mNewsRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                int lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                int visibileItemCount = layoutManager.getChildCount();
                int totalItmeCount = layoutManager.getItemCount();
                if (!mIsAllLoaded && visibileItemCount > 0 && newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItemPosition >= totalItmeCount - 1) {
                    mNewsPresenter.loadMore();
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
        NetUtil.checkNetworkState();
    }

    private void initValues() {
        if (getArguments() != null) {
            channelId = getArguments().getString(Constants.NEWS_ID);
            channelType = getArguments().getString(Constants.NEWS_TYPE);
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
        if (NetUtil.isNetworkAvailable()) {
            Snackbar.make(mNewsRV, message, Snackbar.LENGTH_LONG).show();
        }
    }

    /**
     * 回调接口加载数据
     *
     * @param items
     * @param loadType
     */
    @Override
    public void setItems(List<NewsSummary> items, @LoadNewsType.checker int loadType) {
        switch (loadType) {
            case LoadNewsType.TYPE_REFRESH_SUCCESS:
                mSwipeRefreshLayout.setRefreshing(false);
                mNewsRecyclerViewAdapter.setList(items);
                mNewsRecyclerViewAdapter.notifyDataSetChanged();
                checkIsEmpty(items);
                break;
            case LoadNewsType.TYPE_REFRESH_ERROR:
                mSwipeRefreshLayout.setRefreshing(false);
                checkIsEmpty(items);
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

    private void checkIsEmpty(List<NewsSummary> newsSummaries) {
        if (newsSummaries == null && mNewsRecyclerViewAdapter.getList() == null) {
            mNewsRV.setVisibility(View.VISIBLE);
            mEmptyView.setVisibility(View.VISIBLE);
        } else {
            mNewsRV.setVisibility(View.VISIBLE);
            mEmptyView.setVisibility(View.GONE);
        }
    }

//    /**
//     * 针对每个Itme设置的点击事件
//     *
//     * @param itemView
//     * @param layoutPosition
//     */
//    @Override
//    public void onItemClick(View itemView, int layoutPosition) {
//        startNewDetailActivity(itemView, layoutPosition);
//    }

    private void startNewDetailActivity(View view, int position) {
        Intent intent = setIntent(position);
        startActivity(view, intent);
    }

    private void startActivity(View view, Intent intent) {
        ImageView newsSummaryPhoto = (ImageView) view.findViewById(R.id.news_summary_photo_iv);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions
                    .makeSceneTransitionAnimation(mActivity, newsSummaryPhoto, getResources().getString(R.string.transition_photos));
            startActivity(intent, options.toBundle());
        } else {
/*            ActivityOptionsCompat.makeCustomAnimation(this,
                    R.anim.slide_bottom_in, R.anim.slide_bottom_out);
            这个我感觉没什么用处，类似于
            overridePendingTransition(R.anim.slide_bottom_in, android.R.anim.fade_out);*/

/*            ActivityOptionsCompat.makeThumbnailScaleUpAnimation(source, thumbnail, startX, startY)
            这个方法可以用于4.x上，是将一个小块的Bitmpat进行拉伸的动画。*/

            //让新的Activity从一个小的范围扩大到全屏
            ActivityOptionsCompat options = ActivityOptionsCompat
                    .makeScaleUpAnimation(view, view.getWidth() / 2, view.getHeight() / 2, 0, 0);
            ActivityCompat.startActivity(mActivity, intent, options.toBundle());
        }
    }

    @NonNull
    private Intent setIntent(int position) {
        List<NewsSummary> newsSummaryList = mNewsRecyclerViewAdapter.getList();
        Intent intent = new Intent(mActivity, NewsDetailActivity.class);
        intent.putExtra(Constants.NEWS_POST_ID, newsSummaryList.get(position).getPostid());
        intent.putExtra(Constants.NEWS_IMG_RES, newsSummaryList.get(position).getImgsrc());
        return intent;
    }

    @Override
    public void onRefresh() {
        mNewsPresenter.resfreshData();
    }


    @OnClick(R.id.empty_view)
    public void onClick() {
        mSwipeRefreshLayout.setRefreshing(true);
        mNewsPresenter.resfreshData();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onItemClick(View view, int position, boolean isPhoto) {
        if (isPhoto) {
            NewsPhotoDetail photoDetail = getPhotoDetail(position);
            startNewsPhotoDetailActivity(photoDetail);
        } else {
            startNewDetailActivity(view, position);
        }
    }

    /**
     * 打开图片详情的页面
     *
     * @param photoDetail
     */
    private void startNewsPhotoDetailActivity(NewsPhotoDetail photoDetail) {
        Intent intent = new Intent(getActivity(), NewsPhotoDetailActivity.class);
        intent.putExtra(Constants.PHOTO_DETAIL, photoDetail);
        startActivity(intent);
    }

    /**
     * 获取图片新闻的数据
     *
     * @param position
     * @return
     */
    private NewsPhotoDetail getPhotoDetail(int position) {
        NewsSummary newsSummary = mNewsRecyclerViewAdapter.getList().get(position);
        NewsPhotoDetail photoDetail = new NewsPhotoDetail();
        photoDetail.setTitle(newsSummary.getTitle());
        setPictures(newsSummary, photoDetail);
        return photoDetail;
    }

    private void setPictures(NewsSummary newsSummary, NewsPhotoDetail photoDetail) {
        List<NewsPhotoDetail.Picture> picturesList = new ArrayList<>();
        //newSummary里面的图片地址和title
        if (newsSummary.getAds() != null) {
            for (NewsSummary.AdsBean entity : newsSummary.getAds()) {
                setValuesAndAddToList(picturesList, entity.getTitle(), entity.getImgsrc());
            }
        } else if (newsSummary.getImgextra() != null) {
            for (NewsSummary.ImgextraBean entity : newsSummary.getImgextra()) {
                setValuesAndAddToList(picturesList, null, entity.getImgsrc());
            }
        } else {
            setValuesAndAddToList(picturesList, null, newsSummary.getImgsrc());
        }
        photoDetail.setPictures(picturesList);
    }

    private void setValuesAndAddToList(List<NewsPhotoDetail.Picture> picturesList, String title, String imgsrc) {
        NewsPhotoDetail.Picture picture = new NewsPhotoDetail.Picture();
        if (title != null) {
            picture.setTitle(title);
        }
        picture.setImgSrc(imgsrc);
        picturesList.add(picture);

    }

    @Override
    public void onItemClick(View itemView, int layoutPosition) {

    }

}
