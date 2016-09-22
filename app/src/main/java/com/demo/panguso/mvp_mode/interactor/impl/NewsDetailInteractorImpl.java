package com.demo.panguso.mvp_mode.interactor.impl;

import com.demo.panguso.mvp_mode.R;
import com.demo.panguso.mvp_mode.app.App;
import com.demo.panguso.mvp_mode.common.HostType;
import com.demo.panguso.mvp_mode.interactor.NewsDetailInteractor;
import com.demo.panguso.mvp_mode.mvp.bean.NewsDetail;
import com.demo.panguso.mvp_mode.net.RetrofitManager;
import com.demo.panguso.mvp_mode.response.RequestCallBack;

import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by ${yangfang} on 2016/9/20.
 */
public class NewsDetailInteractorImpl implements NewsDetailInteractor<NewsDetail> {


    @Override
    public Subscription loadDetailNews(final RequestCallBack<NewsDetail> listener, final String id) {
        return RetrofitManager.getInstance(HostType.NETEASE_NEWS_VIDEO).getNewsDetailObservable(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .map(new Func1<Map<String, NewsDetail>, NewsDetail>() {
                    @Override
                    public NewsDetail call(Map<String, NewsDetail> stringNewsDetailMap) {
                        NewsDetail newsDetail = stringNewsDetailMap.get(id);
                        List<NewsDetail.ImgBean> imgSrcs = newsDetail.getImg();
                        if(imgSrcs != null && imgSrcs.size()>2 && App.isHavePhoto()){
                            String newsBody = newsDetail.getBody();
                            for(int i =1;i<imgSrcs.size();i++){
                                String oldChars = "<!--IMG#" + i + "-->";
                                String newChars = "<img src=\"" + imgSrcs.get(i).getSrc() + "\" />";
                                newsBody = newsBody.replace(oldChars, newChars);
                            }
                            newsDetail.setBody(newsBody);
                        }
                        return newsDetail;
                    }
                }).subscribe(new Subscriber<NewsDetail>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError(App.getAppContext().getString(R.string.load_error));
                    }

                    @Override
                    public void onNext(NewsDetail detail) {
                        listener.success(detail);
                    }
                });
    }
}
