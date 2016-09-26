package com.demo.panguso.mvp_mode.respository.network;

import android.util.Log;
import android.util.SparseArray;

import com.demo.panguso.mvp_mode.app.Application;
import com.demo.panguso.mvp_mode.common.ApiConstants;
import com.demo.panguso.mvp_mode.common.HostType;
import com.demo.panguso.mvp_mode.mvp.bean.NewsDetail;
import com.demo.panguso.mvp_mode.mvp.bean.NewsSummary;
import com.demo.panguso.mvp_mode.utils.NetUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by ${yangfang} on 2016/9/13.
 */
public class RetrofitManager {
    private NewsService mNewsService;

    /**
     * 设置缓存有效期为2天
     */
    private static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;

    /**
     * +     * 查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
     * +     * max-stale 指示客户机可以接收超出超时期间的响应消息。如果指定max-stale消息的值，那么客户机可接收超出超时期指定值之内的响应消息。
     * +
     */
    private static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;

    /**
     * +     * 查询网络的Cache-Control设置，头部Cache-Control设为max-age=0
     * +     * (假如请求了服务器并在a时刻返回响应结果，则在max-age规定的秒数内，浏览器将不会发送对应的请求到服务器，数据由缓存直接返回)时则不会使用缓存而请求服务器
     * +
     */
    private static final String CACHE_CONTROL_AGE = "max-age=0";

    private static volatile OkHttpClient mOkHttpClient;

    private static SparseArray<RetrofitManager> retrofitManagerSparseArray = new SparseArray<>(HostType.TYPE_COUNT);

    private RetrofitManager(@HostType.HostTypeChecker int hostType) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiConstants.getHost(hostType))
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        Log.e("RetrofitManager", "///////++++++");
        mNewsService = retrofit.create(NewsService.class);
    }


    private OkHttpClient getOkHttpClient() {
        //双重锁德单例模式
        if (mOkHttpClient == null) {
            synchronized (RetrofitManager.class) {
                //设置缓存路径
                Cache cache = new Cache(new File(Application.getAppContext().getCacheDir(), "okhttp_cache"),
                        1024 * 1024 * 100);
                if (mOkHttpClient == null) {
                    mOkHttpClient = new OkHttpClient.Builder().cache(cache)
                            .connectTimeout(6, TimeUnit.SECONDS)
                            .readTimeout(6, TimeUnit.SECONDS)
                            .writeTimeout(6, TimeUnit.SECONDS)
                            .addInterceptor(mRewriteCacheControlInterceptor)
                            .addNetworkInterceptor(mRewriteCacheControlInterceptor)
                            .addInterceptor(mLoggingInterceptor)
                            .build();
                }
            }
        }
        return mOkHttpClient;
    }

    /**
     * 云端响应头拦截器，用来配置缓存策略
     */
    private final Interceptor mRewriteCacheControlInterceptor = new Interceptor() {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetUtil.isNetworkAvailable(Application.getAppContext())) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();

            }
            Response response = chain.proceed(request);
            if (NetUtil.isNetworkAvailable(Application.getAppContext())) {
                //有网读取接口的@Headers里的配置，进行统一设置
                String cacheControl = request.cacheControl().toString();
                return response.newBuilder().header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return response.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_SEC)
                        .removeHeader("Pragma")
                        .build();
            }
        }
    };
    private final Interceptor mLoggingInterceptor = new Interceptor() {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            long t1 = System.nanoTime();
//            KLog.i(String.format("Sending request %s on %s%n%s", request.url(), chain.connection(), request.headers()));
            Response response = chain.proceed(request);
            long t2 = System.nanoTime();
//           KLog.i(String.format(Locale.getDefault(), "Received response for %s in %.1fms%n%s",
//                    response.request().url(), (t2 - t1) / 1e6d, response.headers();
//            final Request request = chain.request();
//            final Response response = chain.proceed(request);
//
//            final ResponseBody responseBody = response.body();
//            final long contentLength = responseBody.contentLength();
//
//            BufferedSource source = responseBody.source();
//            source.request(Long.MAX_VALUE); // Buffer the entire body.
//            Buffer buffer = source.buffer();
//            Charset charset = Charset.forName("UTF-8");
//            MediaType contentType = responseBody.contentType();
//            if (contentType != null) {
//                try {
//                    charset = contentType.charset(charset);
//                } catch (UnsupportedCharsetException e) {
//                    Log.e("TAG", "Couldn't decode the response body; charset is likely malformed.");
//                    return response;
//                }
//            }
////            if (contentLength != 0) {
////                KLog.v("--------------------------------------------开始打印返回数据----------------------------------------------------");
////                KLog.json(buffer.clone().readString(charset));
////                KLog.v("--------------------------------------------结束打印返回数据----------------------------------------------------");
////            }

            return response;
        }
    };

    /**
     * -     * @param hostType NETEASE_NEWS_VIDEO：1 （新闻，视频），SINA_NEWS_PHOTO：2（图片新闻）
     *
     * @param hostType NETEASE_NEWS_VIDEO：1 （新闻，视频），SINA_NEWS_PHOTO：2（图片新闻）;
     *                 EWS_DETAIL_HTML_PHOTO:3新闻详情html图片)
     */

    public static RetrofitManager getInstance(int hostType) {
        RetrofitManager retrofitManager = retrofitManagerSparseArray.get(hostType);
        if (retrofitManager == null) {
            retrofitManager = new RetrofitManager(hostType);
            retrofitManagerSparseArray.put(hostType, retrofitManager);
            return retrofitManager;
        }
        return retrofitManager;
    }

    /**
     * 根据网络状况获取缓存的策略
     *
     * @return
     */
    private String getCacheControl() {
        return NetUtil.isNetworkAvailable(Application.getAppContext()) ? CACHE_CONTROL_AGE : CACHE_CONTROL_CACHE;
    }

    /**
     * * 网易新闻列表 例子：http://c.m.163.com/nc/article/headline/T1348647909107/0-20.html
     * * <p>
     * * 对API调用了observeOn(MainThread)之后，线程会跑在主线程上，包括onComplete也是，
     * * unsubscribe也在主线程，然后如果这时候调用call.cancel会导致NetworkOnMainThreadException
     * * 加一句unsubscribeOn(io)
     * *
     * * @param type      新闻类别：headline为头条,list为其他
     * * @param id        新闻类别id
     * * @param startPage 开始的页码
     * * @return 被观察对象
     */
    public Observable<Map<String, List<NewsSummary>>> getNewsListObservable(String type, String id, int startPage) {
//        return mNewsService.getNewsList(getCacheControl(), type, id, startPage).subscribeOn(Schedulers.io())
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .unsubscribeOn(Schedulers.io());
        return mNewsService.getNewsList(getCacheControl(), type, id, startPage);
    }

    public Observable<Map<String, NewsDetail>> getNewsDetailObservable(String postId) {
        return mNewsService.getNewsDetail(getCacheControl(), postId);
    }

    public Observable<ResponseBody> getNewsBodyHtmlPhoto(String photoPath) {
        return mNewsService.getNewsBodyHtmlPhoto(photoPath);
    }

}
