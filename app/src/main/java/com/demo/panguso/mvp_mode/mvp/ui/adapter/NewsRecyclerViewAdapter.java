package com.demo.panguso.mvp_mode.mvp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.demo.panguso.mvp_mode.R;
import com.demo.panguso.mvp_mode.app.App;
import com.demo.panguso.mvp_mode.listener.OnItemClickListener;
import com.demo.panguso.mvp_mode.mvp.bean.NewsSummary;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ${yangfang} on 2016/9/9.
 */
public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.ViewHolder> {

    @Inject
    public NewsRecyclerViewAdapter() {

    }

    private List<NewsSummary> mNewsList = new ArrayList<>();
    private int mLastPosition = -1;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    //    public List<NewsSummary> getmNewsList(){
//        return mNewsList;
//    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        //这是给每一条新闻详情消息添加自定义的点击事件
        setItemOnclick(holder);
        return holder;
    }

    private void setItemOnclick(final ViewHolder holder) {
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(holder.itemView, holder.getLayoutPosition());
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        setItemValues(holder, position);
        setItemAppearAnimation(holder, position);
    }

    private void setItemAppearAnimation(ViewHolder holder, int position) {
        if (position > mLastPosition) {
            Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(),
                    R.anim.item_bottom);
//            AnimationSet animation = new AnimationSet(true);
//            TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0.5f,
//                    Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1.0f);
//            AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
//            animation.addAnimation(translateAnimation);
//            animation.addAnimation(alphaAnimation);
            holder.itemView.startAnimation(animation);
        }
    }

    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        if (holder.itemView.getAnimation() != null &&
                holder.itemView.getAnimation().hasStarted()) {
            holder.itemView.clearAnimation();
        }
    }

    private void setItemValues(ViewHolder holder, int position) {
        holder.mNewsSummaryTitleTv.setText(mNewsList.get(position).getTitle());
        holder.mNewsSummaryDigestTv.setText(mNewsList.get(position).getDigest());
        holder.mTextViewTime.setText(mNewsList.get(position).getPtime());
        Glide.with(App.getAppContext()).load(mNewsList.get(position).getImgsrc())
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .format(DecodeFormat.PREFER_ARGB_8888)
//                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_load_fail)
                .into(holder.mImageViewPhotoIv);
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    public void setItems(List<NewsSummary> items) {
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
