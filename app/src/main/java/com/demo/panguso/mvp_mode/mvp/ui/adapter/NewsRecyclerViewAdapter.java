package com.demo.panguso.mvp_mode.mvp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.demo.panguso.mvp_mode.R;
import com.demo.panguso.mvp_mode.app.App;
import com.demo.panguso.mvp_mode.mvp.bean.NewsSummary;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ${yangfang} on 2016/9/9.
 */
public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.ViewHolder> {

    private List<NewsSummary> mNewsList = new ArrayList<>();

    public NewsRecyclerViewAdapter() {

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mNewsSummaryTitleTv.setText(mNewsList.get(position).getTitle());
        holder.mNewsSummaryDigestTv.setText(mNewsList.get(position).getDigest());
        holder.mTextViewTime.setText(mNewsList.get(position).getPtime());
        Glide.with(App.getAppContext()).load(mNewsList.get(position).getImgsrc())
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_add)
                .into(holder.mImageViewPhotoIv);
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    public void setItems(List<NewsSummary> items) {
        Log.e("TAG", "++++>>>>>" + items.get(2).getTitle());
        this.mNewsList = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.news_summary_title_tv)
        TextView mNewsSummaryTitleTv;
        @BindView(R.id.news_summary_photo_iv)
        ImageView mImageViewPhotoIv;
        @BindView(R.id.news_summary_digest_tv)
        TextView mNewsSummaryDigestTv;
        @BindView(R.id.news_summary_ptime_tv)
        TextView mTextViewTime;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
