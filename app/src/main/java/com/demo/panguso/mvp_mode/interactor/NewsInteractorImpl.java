package com.demo.panguso.mvp_mode.interactor;

import android.util.Log;

import com.demo.panguso.mvp_mode.common.ApiConstants;
import com.demo.panguso.mvp_mode.common.HostType;
import com.demo.panguso.mvp_mode.mvp.bean.NewsSummary;
import com.demo.panguso.mvp_mode.net.RetrofitManager;
import com.demo.panguso.mvp_mode.response.RequestCallBack;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by ${yangfang} on 2016/9/9.
 */
public class NewsInteractorImpl implements NewsInteractor<List<NewsSummary>> {
//    private String type = ApiConstants.HEADLINE_TYPE;
//    private String id = ApiConstants.HEADLINE_ID;//新闻头条的id
//    private int startPage = 0;

    @Override
    public Subscription loadNews(final RequestCallBack<List<NewsSummary>> listener, String type, final String id, int startPage) {
        Log.e("NewsInteractorImpl", "6666666");
        return RetrofitManager.getInstance(HostType.NETEASE_NEWS_VIDEO).getNewsListObservable(type, id, 0)
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
                .map(new Func1<NewsSummary, NewsSummary>() {
                    @Override
                    public NewsSummary call(NewsSummary newsSummary) {
                        try {
                            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                                    .parse(newsSummary.getPtime());
                            String ptime = new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault()).format(date);
                            newsSummary.setPtime(ptime);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        return newsSummary;
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
                        Log.e("NewsInteractorImpl", "success");
                        listener.success(newsSummaries);
                    }
                });
    }

}
