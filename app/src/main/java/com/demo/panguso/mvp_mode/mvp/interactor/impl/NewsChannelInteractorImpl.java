package com.demo.panguso.mvp_mode.mvp.interactor.impl;

import com.demo.panguso.mvp_mode.R;
import com.demo.panguso.mvp_mode.app.App;
import com.demo.panguso.mvp_mode.common.Constants;
import com.demo.panguso.mvp_mode.listener.RequestCallBack;
import com.demo.panguso.mvp_mode.mvp.interactor.NewsChannelInteractor;
import com.demo.panguso.mvp_mode.respository.db.NewsChannelTableManager;
import com.demo.panguso.mvp_mode.utils.TransformUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import greendao.NewsChannelTable;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by ${yangfang} on 2016/10/8.
 */

public class NewsChannelInteractorImpl implements NewsChannelInteractor<Map<Integer, List<NewsChannelTable>>> {

    private Map<Integer, List<NewsChannelTable>> newsChannelData;

    @Inject
    public NewsChannelInteractorImpl() {

    }

    @Override
    public Subscription loadNewsChannels(final RequestCallBack<Map<Integer, List<NewsChannelTable>>> callBack) {
        return rx.Observable.create(new rx.Observable.OnSubscribe<Map<Integer, List<NewsChannelTable>>>() {

            @Override
            public void call(Subscriber<? super Map<Integer, List<NewsChannelTable>>> subscriber) {
                Map<Integer, List<NewsChannelTable>> newIntegerListMap = getNewsChannelData();
                subscriber.onNext(newIntegerListMap);
                subscriber.onCompleted();
            }
        }).compose(TransformUtils.<Map<Integer, List<NewsChannelTable>>>defaultSchedulers())
                .subscribe(new Subscriber<Map<Integer, List<NewsChannelTable>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onError(App.getAppContext().getString(R.string.internet_error));
                    }

                    @Override
                    public void onNext(Map<Integer, List<NewsChannelTable>> integerListMap) {
                        callBack.success(integerListMap);
                    }
                });
    }

    public Map<Integer, List<NewsChannelTable>> getNewsChannelData() {
        Map<Integer, List<NewsChannelTable>> newsChanIntegerListMap = new HashMap<>();
        List<NewsChannelTable> channelTablesMine = NewsChannelTableManager.loadNewsChannelsMine();
        List<NewsChannelTable> channelTablesMore = NewsChannelTableManager.loadNewsChannelsMore();
        newsChanIntegerListMap.put(Constants.NEWS_CHANNEL_MINE, channelTablesMine);
        newsChanIntegerListMap.put(Constants.NEWS_CHANNEL_MORE, channelTablesMore);
        return newsChanIntegerListMap;
    }
}
