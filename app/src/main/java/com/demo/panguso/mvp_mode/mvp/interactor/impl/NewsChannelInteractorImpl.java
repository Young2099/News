package com.demo.panguso.mvp_mode.mvp.interactor.impl;

import android.util.Log;

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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import greendao.NewsChannelTable;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by ${yangfang} on 2016/10/8.
 */

public class NewsChannelInteractorImpl implements NewsChannelInteractor<Map<Integer, List<NewsChannelTable>>> {

    private Map<Integer, List<NewsChannelTable>> newsChannelData;
    private ExecutorService mSingleThreadPool;

    @Inject
    public NewsChannelInteractorImpl() {

    }

    private Map<Integer, List<NewsChannelTable>> getNewsChannelData() {
        Map<Integer, List<NewsChannelTable>> newsChannelListMap = new HashMap<>();
        List<NewsChannelTable> channelTableListMine = NewsChannelTableManager.loadNewsChannelsMine();
        List<NewsChannelTable> channelTableListMore = NewsChannelTableManager.loadNewsChannelsMore();
        newsChannelListMap.put(Constants.NEWS_CHANNEL_MINE, channelTableListMine);
        newsChannelListMap.put(Constants.NEWS_CHANNEL_MORE, channelTableListMore);
        return newsChannelListMap;
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

    private void createThreadPool() {
        if(mSingleThreadPool == null){
            mSingleThreadPool = Executors.newSingleThreadExecutor();
        }

    }

    @Override
    public void swapDb(final int fromPositon, final int toPosition) {
        createThreadPool();
        mSingleThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                Log.e("TAG", "fromPosition:" + fromPositon + ":" + "," + toPosition + "");
                NewsChannelTable formNewsChannel = NewsChannelTableManager.loadNewsChannels(fromPositon);
                NewsChannelTable toNewsChannel = NewsChannelTableManager.loadNewsChannels(toPosition);
                if (isAdjacent(fromPositon, toPosition)) {
                    swapAdjacentIndexAndUpdate(formNewsChannel, toNewsChannel);
                } else if (fromPositon - toPosition > 0) {
                    Log.e("TAG","fromposition:"+fromPositon+toPosition);
                    List<NewsChannelTable> newsChannels = NewsChannelTableManager
                            .loadNewsChannelsWithin(toPosition,fromPositon-1);
                    increaseOrReduceIndexAndUpdate(newsChannels, true);
                    changeFromChannelIndexAndUpdate(formNewsChannel,toPosition);
                } else if (fromPositon - toPosition < 0) {
                    List<NewsChannelTable> newsChannelTables = NewsChannelTableManager
                            .loadNewsChannelsWithin(fromPositon+1,toPosition);
                    increaseOrReduceIndexAndUpdate(newsChannelTables,false);
                    changeFromChannelIndexAndUpdate(formNewsChannel,toPosition);
                    
                }
            }

            private boolean isAdjacent(int fromPositon, int toPosition) {
                return Math.abs(fromPositon - toPosition) == 1;
            }

            private void swapAdjacentIndexAndUpdate(NewsChannelTable formNewsChannel, NewsChannelTable toNewsChannel) {
                formNewsChannel.setNewsChannelIndex(toPosition);
                toNewsChannel.setNewsChannelIndex(fromPositon);
                NewsChannelTableManager.update(formNewsChannel);
                NewsChannelTableManager.update(toNewsChannel);

            }
        });


    }

    private void changeFromChannelIndexAndUpdate(NewsChannelTable formNewsChannel, int toPosition) {
        formNewsChannel.setNewsChannelIndex(toPosition);
        NewsChannelTableManager.update(formNewsChannel);
    }

    private void increaseOrReduceIndexAndUpdate(List<NewsChannelTable> newsChannels, boolean b) {
        for(NewsChannelTable newsChannel:newsChannels){
            increaseOrReduceIndex(b,newsChannel);
            NewsChannelTableManager.update(newsChannel);
        }
    }

    private void increaseOrReduceIndex(boolean b, NewsChannelTable newsChannel) {
        int targetIndex;
        if(b){
            targetIndex = newsChannel.getNewsChannelIndex()+1;
        }else {
            targetIndex = newsChannel.getNewsChannelIndex()-1;
        }
        newsChannel.setNewsChannelIndex(targetIndex);
    }

    @Override
    public void updateDb(final NewsChannelTable newsChannelTable, final boolean isChanelMine) {
        createThreadPool();
        mSingleThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                int channelIndex = newsChannelTable.getNewsChannelIndex();
                if(isChanelMine){
                    List<NewsChannelTable> newsChannelTables =NewsChannelTableManager.loadNewsChannelsIndxeGt(channelIndex);
                            increaseOrReduceIndexAndUpdate(newsChannelTables,false);
                    int targetIndex = NewsChannelTableManager.getAllSize();
                    changeIsSelectAndIndex(targetIndex,false);
                }else{
                    List<NewsChannelTable> newsChannels = NewsChannelTableManager.loadNewsChannelsIndexLtAndIsUnselect(channelIndex);
                 increaseOrReduceIndexAndUpdate(newsChannels, true);
                     int targetIndex = NewsChannelTableManager.getNewsChannelSelectSize();
                    changeIsSelectAndIndex(targetIndex,true);
                }
            }
            private void changeIsSelectAndIndex(int targetIndex, boolean b) {
                newsChannelTable.setNewsChannelSelect(b);
                changeFromChannelIndexAndUpdate(newsChannelTable,targetIndex);
            }

        });
    }


}
