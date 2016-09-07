package com.demo.panguso.mvp_mode.mvp.ui.activities.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.demo.panguso.mvp_mode.R;

import butterknife.ButterKnife;


/**
 * Created by ${yangfang} on 2016/9/7.
 */
public abstract class BaseActivity extends AppCompatActivity {

    /**
     * 初始化布局
     */
    protected abstract void initViews();

    protected abstract int getLayoutId();

    protected abstract void initInjector();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int layoutId = getLayoutId();
        setContentView(layoutId);
        initInjector();
        ButterKnife.bind(this);
        initToolBar();
        initViews();
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}
