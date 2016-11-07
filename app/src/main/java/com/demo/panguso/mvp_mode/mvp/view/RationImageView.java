package com.demo.panguso.mvp_mode.mvp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by ${yangfang} on 2016/11/7.
 */

public class RationImageView extends ImageView {
    private int originalWidth = -1;
    private int originalHeight = -1;

    public RationImageView(Context context) {
        super(context);
    }

    public RationImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RationImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOriginalSize(int originalWidth, int originalHeight) {
        this.originalHeight = originalHeight;
        this.originalWidth = originalWidth;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (originalWidth > 0 && originalHeight > 0) {
            float ratio = originalWidth / originalHeight;
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = MeasureSpec.getSize(heightMeasureSpec);
            if (width > 0) {
                height = (int) (width / ratio);
            }
            setMeasuredDimension(width, height);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
