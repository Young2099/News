package com.demo.panguso.mvp_mode.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.panguso.mvp_mode.R;

/**
 * Created by ${yangfang} on 2016/9/8.
 */
public class ToastUtil {
    private static Toast toast;
    //显示Toast的消息标记
    private static final int SHOW_TOAST = 0;
    private static final long TOAST_SHORT_DELAY = 1000;

    private static Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    public static void make(Context context, CharSequence cs, int resId) {
        showToast(context, cs, resId);
    }

    public static void showToast(Context context, CharSequence cs, int resId) {
        destory();
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_toast, null);
        ProgressBar pro = (ProgressBar) view.findViewById(R.id.progressIconToast);
        pro.setVisibility(View.GONE);
        //设置toast文字
        TextView tv = (TextView) view.findViewById(R.id.tvTextToast);
        tv.setText(cs);
        toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        LinearLayout toastView = (LinearLayout) toast.getView();
        ImageView imageCodeProject = new ImageView(context);
        imageCodeProject.setImageResource(resId);
        toastView.addView(imageCodeProject, 0);
        toast.show();
    }

    private static void destory() {
        if (toast != null) {
            toast.cancel();
            mHandler.removeCallbacksAndMessages(null);
            toast = null;
        }
    }

}
