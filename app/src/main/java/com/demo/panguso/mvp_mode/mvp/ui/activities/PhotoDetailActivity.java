package com.demo.panguso.mvp_mode.mvp.ui.activities;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.demo.panguso.mvp_mode.R;
import com.demo.panguso.mvp_mode.common.Constants;
import com.demo.panguso.mvp_mode.mvp.bean.PhotoDetail;
import com.demo.panguso.mvp_mode.mvp.ui.activities.base.BaseActivity;
import com.demo.panguso.mvp_mode.mvp.ui.adapter.NewsFragmetPagerAdapter;
import com.demo.panguso.mvp_mode.mvp.ui.fragment.PhotoDetailFragment;
import com.demo.panguso.mvp_mode.mvp.view.PhotoDetailOnClickEvent;
import com.demo.panguso.mvp_mode.mvp.view.PhotoViewPager;
import com.demo.panguso.mvp_mode.utils.RxBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.functions.Action1;

public class PhotoDetailActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.viewpager)
    PhotoViewPager mViewPager;
    @BindView(R.id.photo_detail_tv)
    TextView mPhotoTextViewTitle;

    private List<Fragment> mPhotoDetailFragments = new ArrayList<>();
    private PhotoDetail mPhotoDetail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * 点击图片显示title是一个异步的过程
         */
        mSubscription = RxBus.getInstance().toObservable(
                PhotoDetailOnClickEvent.class)
                .subscribe(new Action1<PhotoDetailOnClickEvent>() {
                    @Override
                    public void call(PhotoDetailOnClickEvent photoDetailOnClickEvent) {
                        if (mPhotoTextViewTitle.getVisibility() == View.VISIBLE) {
                            startAnimation(View.GONE, 0.9f, 0.5f);
                        } else {
                            mPhotoTextViewTitle.setVisibility(View.VISIBLE);
                            startAnimation(View.VISIBLE, 0.5f, 0.9f);
                        }
                    }
                });
    }

    private void startAnimation(final int endState, float startValue, float endValue) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(
                mPhotoTextViewTitle, "alpha", startValue, endValue)
                .setDuration(200);

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mPhotoTextViewTitle.setVisibility(endState);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.start();

    }


    @Override
    protected void initViews() {
        mToolbar.setTitle("图片新闻");
        mPhotoDetail = getIntent().getParcelableExtra(Constants.PHOTO_DETAIL);
        createFragment(mPhotoDetail);
        initViewPager();
        //显示初始化的位置的新闻title
        setPhotoDetailTitle(0);
    }

    private void initViewPager() {
        NewsFragmetPagerAdapter fragmetPagerAdapter = new NewsFragmetPagerAdapter(getSupportFragmentManager(), null, mPhotoDetailFragments);
        mViewPager.setAdapter(fragmetPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setPhotoDetailTitle(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 创建多少张图片的fragment
     *
     * @param mPhotoDetail
     */
    private void createFragment(PhotoDetail mPhotoDetail) {
        mPhotoDetailFragments.clear();
        for (PhotoDetail.Picture photoDetail : mPhotoDetail.getPictures()) {
            PhotoDetailFragment fragment = new PhotoDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putString(Constants.PHOTO_DETAIL_IMGSRC, photoDetail.getImgSrc());
            fragment.setArguments(bundle);
            mPhotoDetailFragments.add(fragment);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_photo;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    public void setPhotoDetailTitle(int photoPosition) {
        String title = getDetailTitle(photoPosition);
        mPhotoTextViewTitle.setText(getString(R.string.photos_detail_title,
                photoPosition + 1, mPhotoDetailFragments.size(), title));
    }

    private String getDetailTitle(int photoDetailTitle) {
        String title = mPhotoDetail.getPictures().get(photoDetailTitle).getTitle();
        if (title == null) {
            title = mPhotoDetail.getTitle();
        }
        return title;
    }

}
