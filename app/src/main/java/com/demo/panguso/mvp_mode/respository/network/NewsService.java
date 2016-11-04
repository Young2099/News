package com.demo.panguso.mvp_mode.respository.network;

import com.demo.panguso.mvp_mode.mvp.bean.GirData;
import com.demo.panguso.mvp_mode.mvp.bean.NewsDetail;
import com.demo.panguso.mvp_mode.mvp.bean.NewsSummary;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by ${yangfang} on 2016/9/13.
 */
public interface NewsService {

    @GET("nc/article/{type}/{id}/{startPage}-20.html")
    Observable<Map<String, List<NewsSummary>>> getNewsList(
            @Header("Cache-Control") String cacheControl,
            @Path("type") String type,
            @Path("id") String id,
            @Path("startPage") int startPage
    );

    @GET("nc/article/{postId}/full.html")
    Observable<Map<String, NewsDetail>> getNewsDetail(
            @Header("Cache-Control") String cacheControl,
            @Path("postId") String postId);

    @GET
    Observable<ResponseBody> getNewsBodyHtmlPhoto(
//            @Header("Cache-Control") String cacheControl,
            @Url String photoPath
    );//@url，它允许我们直接传入一个请求的URL,这样以来，我们可以将一个请求获得
    //url直接传入进来，baseUrl将被无视
    // baseUrl 需要符合标准，为空、""、或不合法将会报错

    @GET("data/福利/{size}/{page}")
    Observable<GirData> getPhotoList(
            @Path("size") int size,
            @Path("page") int page);
}
