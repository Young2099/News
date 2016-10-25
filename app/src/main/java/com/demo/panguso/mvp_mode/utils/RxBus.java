package com.demo.panguso.mvp_mode.utils;


import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by ${yangfang} on 2016/10/24.
 */

public class RxBus {
    private static volatile RxBus mRxBus;
    //主题
    private final Subject<Object,Object> mBus;

    //PublishSubject只会把在订阅发生的时间点之后来之原始Observable的数据发射给观察者
    public RxBus() {
        mBus = new SerializedSubject<>(PublishSubject.create());
    }

    //单列RxBus
    public static RxBus getInstance() {
        if (mRxBus == null) {
            synchronized (RxBus.class) {
                if (mRxBus == null) {
                    mRxBus = new RxBus();
                }
            }
        }
        return mRxBus;
    }

    //提供了一个新的事件
    public void post(Object  o){
        mBus.onNext(o);
    }
    // 根据传递的eventType类型返回特定类型的被观察者
    public <T>Observable<T> toObservable(Class<T> eventType){
        return mBus.ofType(eventType);
    }


}
