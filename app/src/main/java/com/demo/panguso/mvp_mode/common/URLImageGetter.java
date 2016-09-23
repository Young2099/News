package com.demo.panguso.mvp_mode.common;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.Html;
import android.widget.TextView;

import com.demo.panguso.mvp_mode.R;
import com.demo.panguso.mvp_mode.app.App;
import com.demo.panguso.mvp_mode.net.RetrofitManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
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
    private static final String mFilePath = App.getAppContext().getCacheDir().getAbsolutePath();

    public URLImageGetter(TextView textView, String newsBody, int total) {
        mTextView = textView;
        mPicWidth = mTextView.getWidth();
        mNewsBody = newsBody;
        mPicTotal = total;

    }

    @Override
    public Drawable getDrawable(String s) {
//        Drawable drawable = null;
//        File file = new File(App.getAppContext().getCacheDir(), s.hashCode() + "");
//        if (s.startsWith("http")) {
//            if (file.exists()) {
//                drawable = getDrawableFromDisk(file);
//            } else {
//                drawable = getDrawableFromNet(s);
//            }
//        }
        Drawable drawable;
        File file = new File(mFilePath, s.hashCode() + "");
        if (file.exists()) {
            mPicCount++;
            drawable = getDrawableFromDisk(file);
        } else {
            drawable = getDrawableFromNet(s);
        }
        return drawable;
    }

    private Drawable getDrawableFromNet(final String s) {
        RetrofitManager.getInstance(HostType.NEWS_DETAIL_HTML_PHOTO).getNewsBodyHtmlPhoto(s)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<ResponseBody, Boolean>() {
                    @Override
                    public Boolean call(ResponseBody responseBody) {
                        return loadNetPicture(responseBody, s);
                    }
                }).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                mPicCount++;
                if (/*isLoadSuccess &&*/ (mPicCount == mPicTotal - 1)) {
                    mTextView.setText(Html.fromHtml(mNewsBody, URLImageGetter.this, null));
                }
            }
        });
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

    private Boolean loadNetPicture(ResponseBody response, String filePath) {
        File file = new File(App.getAppContext().getCacheDir(), filePath.hashCode() + "");
        InputStream in = null;
        FileOutputStream out = null;
        try {
            in = response.byteStream();
            out = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
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
