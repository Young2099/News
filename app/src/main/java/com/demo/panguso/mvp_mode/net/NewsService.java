package com.demo.panguso.mvp_mode.net;

import com.demo.panguso.mvp_mode.mvp.bean.NewsDetail;
import com.demo.panguso.mvp_mode.mvp.bean.NewsSummary;

import java.util.List;
import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by ${yangfang} on 2016/9/13.
 *
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
}
