package com.demo.panguso.mvp_mode.mvp.ui.fragment.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo.panguso.mvp_mode.app.App;
import com.demo.panguso.mvp_mode.inject.component.DaggerFragmentComponent;
import com.demo.panguso.mvp_mode.inject.component.FragmentComponent;
import com.demo.panguso.mvp_mode.inject.module.FragmentModule;
import com.demo.panguso.mvp_mode.mvp.presenter.base.BasePresenter;
import com.demo.panguso.mvp_mode.utils.MyUtils;
import com.squareup.leakcanary.RefWatcher;

import butterknife.ButterKnife;
import rx.Subscription;

/**
 * Created by ${yangfang} on 2016/9/9.
 */
public abstract class BaseFragment<T extends BasePresenter> extends Fragment {

    protected T mPresenter;
    protected FragmentComponent mFragmentComponent;
    private View mFragmentView;

    public FragmentComponent getmFragmentComponent() {
        return mFragmentComponent;
    }

    public abstract void initInjector();

    public abstract int getLayoutId();

    public abstract void initViews(View view);
    public Subscription mSubscription;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentComponent = DaggerFragmentComponent.builder()
                .applicationComponent(((App) getActivity().getApplication()).getApplicationComponent())
                .fragmentModule(new FragmentModule(this))
                .build();
        initInjector();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mFragmentView == null) {
            mFragmentView = inflater.inflate(getLayoutId(), container, false);
            ButterKnife.bind(this, mFragmentView);
            initViews(mFragmentView);
        }
        return mFragmentView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = App.getWatcher(getActivity());
        refWatcher.watch(this);
        if (mPresenter != null) {
            mPresenter.onDestory();
        }
        MyUtils.cancleSubscription(mSubscription);
    }
}
