package com.demo.panguso.mvp_mode.utils;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.demo.panguso.mvp_mode.R;
import com.demo.panguso.mvp_mode.app.App;
import com.demo.panguso.mvp_mode.mvp.presenter.base.BasePresenter;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscription;

/**
 * Created by ${yangfang} on 2016/9/14.
 */
public class MyUtils {

    private static int screenWidth;

    public static String formatDate(String before) {

        String after;
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                    .parse(before);
            after = new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault()).format(date);
        } catch (ParseException e) {
            Log.e("MyUtils", "日期转换格式异常");
            return before;
        }
        return after;
    }

    public static int getStatusBarHeight(Activity activity) {
        int height = 0;
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            height = activity.getResources().getDimensionPixelSize(resourceId);
        }
        return height;
    }


    public static void dynamicSetTabLayoutMode(TabLayout tabLayout) {
        int tabWith = caculateTabWith(tabLayout);
        int screenWidth = getScreenWidth();
        if (tabWith <= screenWidth) {
            //小于就不滑动
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
        } else {
            //大于就滑动，tabLayout的数量
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        }
    }

    private static int caculateTabWith(TabLayout mTabs) {
        int tabWidth = 0;
        for (int i = 0; i < mTabs.getChildCount(); i++) {
            View view = mTabs.getChildAt(i);
            view.measure(0, 0);//这里是通知view测量，以便于能够保证获取到宽高
            tabWidth += view.getMeasuredWidth();
        }
        return tabWidth;
    }

    public static int getScreenWidth() {
        //屏幕的宽度获取
        return App.getAppContext().getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 取消注册的被观察者
     *
     * @param mSubscription
     */
    public static void cancleSubscription(Subscription mSubscription) {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    public static String analyzeNetworkError(Throwable e) {
        String errMsg = App.getAppContext().getString(R.string.load_error);
        if (e instanceof HttpException) {
            int state = ((HttpException) e).code();
            if (state == 403) {
                errMsg = App.getAppContext().getString(R.string.retry_after);
            }
        }
        return errMsg;
    }

    /**
     * 解决InputManager内存泄露现象
     *
     * @param context
     * @param <T>
     */
    public static <T extends BasePresenter> void fixInputMethodManagerLeak(Context context) {
        if (context == null) {
            return;
        }
        InputMethodManager im = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im == null) {
            return;
        }
        String[] arr = new String[]{"mCurRootView", "mServedView", "mNextServedView"};

        Field f;
        Object obj_get;
        for (String param : arr) {
            try {
                f = im.getClass().getDeclaredField(param);
                if (!f.isAccessible()) {
                    f.setAccessible(true);
                }
                obj_get = f.get(im);
                if (obj_get != null && obj_get instanceof View) {
                    View v_get = (View) obj_get;
                    if (v_get.getContext() == context) { // 被InputMethodManager持有引用的context是想要目标销毁的
                        f.set(im, null);
                    } else {
                        // 不是想要目标销毁的，即为又进了另一层界面了，不要处理，避免影响原逻辑,也就不用继续for循环了
                                               /*if (QLog.isColorLevel()) {
                           QLog.d(ReflecterHelper.class.getSimpleName(), QLog.CLR, "fixInputMethodManagerLeak break, context is not suitable, get_context=" + v_get.getContext()+" dest_context=" + destContext);
                        }*/
                    }
                } else {
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }
}
