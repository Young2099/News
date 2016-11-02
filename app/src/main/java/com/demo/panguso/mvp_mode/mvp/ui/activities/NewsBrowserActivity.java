package com.demo.panguso.mvp_mode.mvp.ui.activities;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.demo.panguso.mvp_mode.R;
import com.demo.panguso.mvp_mode.common.Constants;
import com.demo.panguso.mvp_mode.mvp.ui.activities.base.BaseActivity;

import butterknife.BindView;

public class NewsBrowserActivity extends BaseActivity {

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.web_view)
    WebView mWebView;
    @BindView(R.id.toolbar)
    Toolbar mToolBar;


    @Override
    protected void initViews() {
        mToolBar.setTitle(getIntent().getStringExtra(Constants.NEWS_TITLE));
        initWebView();
    }

    private void initWebView() {
        setWebViewSettings();
        setWebView();

    }

    private void setWebView() {
        mWebView.loadUrl(getIntent().getStringExtra(Constants.NEWS_LINK));
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url != null) {
                    view.loadUrl(url);
                }
                return true;
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {//访问服务器成功
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(newProgress);
                }
            }
        });
    }

    private void setWebViewSettings() {
        WebSettings webSettings = mWebView.getSettings();
        //打开页面，自适应屏幕
        webSettings.setUseWideViewPort(true);//将图片调整到合适webview的大小
        webSettings.setLoadWithOverviewMode(true);//缩放至屏幕的大小
        //页面支持缩放
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_browser;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.removeAllViews();
        mWebView.destroy();
    }
}
