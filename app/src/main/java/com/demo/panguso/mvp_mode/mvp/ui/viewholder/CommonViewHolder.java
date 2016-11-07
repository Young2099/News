package com.demo.panguso.mvp_mode.mvp.ui.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.panguso.mvp_mode.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.demo.panguso.mvp_mode.mvp.ui.adapter.NewsRecyclerViewAdapter.TYPE_FOOTER;
import static com.demo.panguso.mvp_mode.mvp.ui.adapter.NewsRecyclerViewAdapter.TYPE_ITEM;
import static com.demo.panguso.mvp_mode.mvp.ui.adapter.NewsRecyclerViewAdapter.TYPE_PHOTO_ITEM;
import static com.demo.panguso.mvp_mode.mvp.ui.adapter.PhotoListAdapter.TYPE_PHOTO;

/**
 * Created by ${yangfang} on 2016/10/26.
 */

public class CommonViewHolder {

    public ItemViewHolder itemViewHolder;
    public FooterViewHolder footerViewHolder;
    public PhotoViewHolder photoViewHolder;
    public PhotoListViewHolder photoListViewHolder;

    public CommonViewHolder(View view, int i) {
        if (i == TYPE_ITEM) {
            this.itemViewHolder = new ItemViewHolder(view);
        } else if (i == TYPE_FOOTER) {
            this.footerViewHolder = new FooterViewHolder(view);
        } else if (i == TYPE_PHOTO_ITEM) {
            this.photoViewHolder = new PhotoViewHolder(view);
        } else if (i == TYPE_PHOTO) {
            photoListViewHolder = new PhotoListViewHolder(view);
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.news_summary_title_tv)
        public TextView mNewsSummaryTitleTv;
        @BindView(R.id.news_summary_photo_iv)
        public ImageView mImageViewPhotoIv;
        @BindView(R.id.news_summary_digest_tv)
        public TextView mNewsSummaryDigestTv;
        @BindView(R.id.news_summary_ptime_tv)
        public TextView mTextViewTime;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.news_summary_title_tv)
        public TextView mNewsSummaryTitleTv;
        @BindView(R.id.news_summary_ptime_tv)
        public TextView mNewsSummaryPtime;
        @BindView(R.id.news_summary_photo_iv_group)
        public LinearLayout mNewsSummaryPhotoIvGroup;
        @BindView(R.id.news_summary_photo_iv_left)
        public ImageView mNewsSummaryPhotoLeft;
        @BindView(R.id.news_summary_photo_iv_right)
        public ImageView mNewsSummaryPhotoRight;
        @BindView(R.id.news_summary_photo_iv_middle)
        public ImageView mNewsSummaryPhotoMiddle;


        public PhotoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class PhotoListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.photo_list_view)
        public ImageView mImageView;

        public PhotoListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
