package com.demo.panguso.mvp_mode.mvp.interactor.impl;

import com.demo.panguso.mvp_mode.common.ApiConstants;
import com.demo.panguso.mvp_mode.common.HostType;
import com.demo.panguso.mvp_mode.listener.RequestCallBack;
import com.demo.panguso.mvp_mode.mvp.bean.NewsSummary;
import com.demo.panguso.mvp_mode.mvp.interactor.NewsListInteractor;
import com.demo.panguso.mvp_mode.respository.network.RetrofitManager;
import com.demo.panguso.mvp_mode.utils.TransformUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by ${yangfang} on 2016/9/20.
 */
public class NewsListInteractorImpl implements NewsListInteractor<List<NewsSummary>> {
    @Inject
    public NewsListInteractorImpl(){

    }
    @Override
    public Subscription setListItem(final RequestCallBack<List<NewsSummary>> listener, String type, final String id, final int startPage) {
        return RetrofitManager.getInstance(HostType.NETEASE_NEWS_VIDEO).getNewsListObservable(type, id, startPage)
//                .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
                .flatMap(new Func1<Map<String, List<NewsSummary>>, Observable<NewsSummary>>() {
                    @Override
                    public Observable<NewsSummary> call(Map<String, List<NewsSummary>> stringListMap) {
                        //找到id为北京的信息
                        if (id.endsWith(ApiConstants.HOUSE_ID)) {
                            return Observable.from(stringListMap.get("北京"));
                        }
                        return Observable.from(stringListMap.get(id));
                    }
                })
                .map(new Func1<NewsSummary, NewsSummary>() {
                    //.map是将NewsSummery里面的Ptime取出来,进行排序
                    @Override
                    public NewsSummary call(NewsSummary newsSummary) {
                        try {
                            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                                    .parse(newsSummary.getPtime());
                            String ptime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(date);
                            newsSummary.setPtime(ptime);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        return newsSummary;
                    }
                })
                .distinct()
                //对新闻信息根据时间进行排序
                .toSortedList(new Func2<NewsSummary, NewsSummary, Integer>() {
                    @Override
                    public Integer call(NewsSummary newsSummary, NewsSummary newsSummary2) {
                        return newsSummary2.getPtime().compareTo(newsSummary.getPtime());
                    }
                })
                .compose(TransformUtils.<List<NewsSummary>>defaultSchedulers() )
                .subscribe(new Subscriber<List<NewsSummary>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError("请求失败");
                    }

                    @Override
                    public void onNext(List<NewsSummary> newsSummaries) {
                        listener.success(newsSummaries);
                    }
                });
    }
}
