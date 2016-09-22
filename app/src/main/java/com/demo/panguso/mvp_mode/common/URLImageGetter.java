package com.demo.panguso.mvp_mode.common;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.Html;
import android.widget.TextView;

import com.demo.panguso.mvp_mode.R;
import com.demo.panguso.mvp_mode.app.App;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${yangfang} on 2016/9/22.
 */
public class URLImageGetter implements Html.ImageGetter {
    private TextView mTextView;
    private int mPicWidth;
    private String mNewsBody;
    private int mPicCount;
    private int mPicTotal;

    public URLImageGetter(TextView textView, String newsBody, int total) {
        mTextView = textView;
        mPicWidth = mTextView.getWidth();
        mNewsBody = newsBody;
        mPicTotal = total;

    }

    @Override
    public Drawable getDrawable(String s) {
        Drawable drawable = null;
        File file = new File(App.getAppContext().getCacheDir(), s.hashCode() + "");
        if (s.startsWith("http")) {
            if (file.exists()) {
                drawable = getDrawableFromDisk(file);
            } else {
                drawable = getDrawableFromNet(s);
            }
        }
        return drawable;
    }

    private Drawable getDrawableFromNet(final String s) {
        Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                subscriber.onNext(loadNetPicture(s));
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        mPicCount++;
                        if (/*isLoadSuccess &&*/ (mPicCount == mPicTotal - 1)) {
                            mTextView.setText(Html.fromHtml(mNewsBody, URLImageGetter.this, null));
                        }

                    }
                })
        ;
        return createPicPlaceholder();
    }

    @SuppressWarnings("deprecation")
    @NonNull
    private Drawable createPicPlaceholder() {
        Drawable drawable;
        drawable = new ColorDrawable(App.getAppContext().getResources().getColor(R.color.background_color));
        drawable.setBounds(0, 0, mPicWidth, mPicWidth / 3);
        return drawable;
    }

    private Boolean loadNetPicture(String filePath) {
        File file = new File(App.getAppContext().getCacheDir(), filePath.hashCode() + "");
        InputStream in = null;
        FileOutputStream out = null;
        URL url;
        try {
            url = new URL(filePath);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("GET");
            if (connection.getResponseCode() == 200) {
                in = connection.getInputStream();
                out = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = in.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
            }else{

            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Drawable getDrawableFromDisk(File file) {
        Drawable drawable;
        drawable = Drawable.createFromPath(file.getAbsolutePath());
        if (drawable != null) {
            float imgWidth = drawable.getIntrinsicWidth();
            float imgHeight = drawable.getIntrinsicHeight();
            float rate = imgHeight / imgWidth;
            int picHeight = (int) (mPicWidth * rate);
            drawable.setBounds(0, 0, mPicWidth, picHeight);
        }
        return drawable;
    }
}
