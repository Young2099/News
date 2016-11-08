package com.demo.panguso.mvp_mode.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.demo.panguso.mvp_mode.R;
import com.demo.panguso.mvp_mode.app.App;

/**
 * Created by ${yangfang} on 2016/9/13.
 */
public class NetUtil {

    /**
     * 检测当前网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) App.getAppContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void checkNetworkState() {
        if (!NetUtil.isNetworkAvailable()) {
            ToastUtil.showToast(App.getAppContext(), App.getAppContext().getString(R.string.internet_error), 0);
        }
    }
}
