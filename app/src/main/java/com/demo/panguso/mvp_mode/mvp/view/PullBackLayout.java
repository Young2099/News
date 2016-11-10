package com.demo.panguso.mvp_mode.mvp.view;


import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

/**
 * Created by ${yangfang} on 2016/11/9.
 */

public class PullBackLayout extends FrameLayout {

    private final ViewDragHelper dragHelper;
    private final int minimumFlingVelocity;
    @Nullable
    private Callback callback;

    public PullBackLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        dragHelper = ViewDragHelper.create(this, 1f / 8f, new ViewDragCallback());
        minimumFlingVelocity = ViewConfiguration.get(context).getScaledMinimumFlingVelocity();
    }

    public PullBackLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullBackLayout(Context context) {
        this(context, null);
    }

    public void setCallback(@Nullable Callback callback) {
        this.callback = callback;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return dragHelper.shouldInterceptTouchEvent(ev);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {

            dragHelper.processTouchEvent(event);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void computeScroll() {
        if (dragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private class ViewDragCallback extends ViewDragHelper.Callback {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return 0;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return Math.max(0, top);
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return getHeight();
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return 0;
        }

        @Override
        public void onViewCaptured(View capturedChild, int activePointerId) {
            if (callback != null) {
                callback.onPullStart();
            }
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            if (callback != null) {
                callback.onPull((float) top / (float) getHeight());
            }
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            int slop = yvel > minimumFlingVelocity ? getHeight() / 6 : getHeight() / 3;
            if (releasedChild.getTop() > slop) {
                if (callback != null) {
                    callback.onPullComplete();
                }
            } else {
                if (callback != null) {
                    callback.onPullCancle();
                }
                dragHelper.settleCapturedViewAt(0, 0);
                invalidate();
            }
        }
    }

    public interface Callback {
        void onPullStart();

        void onPull(float progress);

        void onPullCancle();

        void onPullComplete();
    }
}
