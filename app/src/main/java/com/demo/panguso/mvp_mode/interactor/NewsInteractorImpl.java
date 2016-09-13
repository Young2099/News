package com.demo.panguso.mvp_mode.interactor;

import com.demo.panguso.mvp_mode.common.ApiConstants;
import com.demo.panguso.mvp_mode.common.HostType;
import com.demo.panguso.mvp_mode.mvp.bean.NewsSummary;
import com.demo.panguso.mvp_mode.net.RetrofitManager;
import com.demo.panguso.mvp_mode.response.RequestCallBack;

import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by ${yangfang} on 2016/9/9.
 */
public class NewsInteractorImpl implements NewsInteractor<List<NewsSummary>> {

    private String type = ApiConstants.HEADLINE_TYPE;
    private String id = ApiConstants.HEADLINE_ID;//新闻头条的id
    private int startPage = 0;

//    @Override
//    public void loadNews(final RequestCallBack<List<NewsSummary>> listener) {
//
//        RetrofitManager.getInstance(HostType.NETEASE_NEWS_VIDEO).getNewsListObservable(type, id, startPage)
//                .flatMap(new Func1<Map<String, List<NewsSummary>>, Observable<NewsSummary>>() {
//                    @Override
//                    public Observable<NewsSummary> call(Map<String, List<NewsSummary>> stringListMap) {
//                        if (id.endsWith(ApiConstants.HOUSE_ID)) {
//                            //房产实际上针对地区的它的id与返回的key不同
//                            return Observable.from(stringListMap.get("北京"));
//                        }
//                        return Observable.from(stringListMap.get(id));
//                    }
//                })
//
//                .toSortedList(new Func2<NewsSummary, NewsSummary, Integer>() {
//                    @Override
//                    public Integer call(NewsSummary newsSummary, NewsSummary newsSummary2) {
//                        return newsSummary2.getPtime().compareTo(newsSummary.getPtime());
//                    }
//                })
//                .subscribe(new Subscriber<List<NewsSummary>>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(List<NewsSummary> newsSummaries) {
//                        listener.onFinished(newsSummaries);
//                    }
//                });
//    }
//
//    }

    @Override
    public void loadNews(final RequestCallBack<List<NewsSummary>> listener) {
        RetrofitManager.getInstance(HostType.NETEASE_NEWS_VIDEO).getNewsListObservable(type, id, startPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .flatMap(new Func1<Map<String, List<NewsSummary>>, Observable<NewsSummary>>() {
                    @Override
                    public Observable<NewsSummary> call(Map<String, List<NewsSummary>> stringListMap) {
                        if (id.endsWith(ApiConstants.HOUSE_ID)) {
                            return Observable.from(stringListMap.get("北京"));
                        }
                        return Observable.from(stringListMap.get(id));
                    }
                })
                //根据时间排序
                .toSortedList(new Func2<NewsSummary, NewsSummary, Integer>() {
                    @Override
                    public Integer call(NewsSummary newsSummary, NewsSummary newsSummary2) {
                        return newsSummary.getPtime().compareTo(newsSummary2.getPtime());
                    }
                })
                .subscribe(new Subscriber<List<NewsSummary>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onError("加载失败");
                    }

                    @Override
                    public void onNext(List<NewsSummary> newsSummaries) {
                        listener.success(newsSummaries);
                    }
                });
    }
}
