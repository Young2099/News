package com.demo.panguso.mvp_mode.mvp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @Inject
    public NewsRecyclerViewAdapter() {

    }

    public static final int TYPE_ITEM = 0;
    public static final int TYPE_FOOTER = 1;
    private boolean mIsShowFooter;
    private List<NewsSummary> mNewsList = new ArrayList<>();
    private int mLastPosition = -1;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public List<NewsSummary> getmNewsList() {
        return mNewsList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_footer, parent, false);
            return new FooterViewHolder(view);
        } else if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
            ItemViewHolder itemViewHolder = new ItemViewHolder(view);
            setItemOnclick(itemViewHolder);
            return itemViewHolder;
        }
        throw new RuntimeException("virteu");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        setItemValues(holder, position);
        setItemAppearAnimation(holder, position);
    }

    private void setItemOnclick(final RecyclerView.ViewHolder holder) {
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
    public int getItemViewType(int position) {
        if (mIsShowFooter && (getItemCount() - 1) == position) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    private void setItemAppearAnimation(RecyclerView.ViewHolder holder, int position) {
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
            mLastPosition = position;
        }
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        if (holder.itemView.getAnimation() != null &&
                holder.itemView.getAnimation().hasStarted()) {
            holder.itemView.clearAnimation();
        }
    }

    private void setItemValues(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            String title = mNewsList.get(position).getLtitle();
            if (title == null) {
                title = mNewsList.get(position).getTitle();
            }
            String ptime = mNewsList.get(position).getPtime();
            String digest = mNewsList.get(position).getDigest();
            String imgSrc = mNewsList.get(position).getImgsrc();

            ((ItemViewHolder) holder).mNewsSummaryTitleTv.setText(title);
            ((ItemViewHolder) holder).mTextViewTime.setText(ptime);
            ((ItemViewHolder) holder).mNewsSummaryDigestTv.setText(digest);
            Glide.with(App.getAppContext()).load(imgSrc)
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .format(DecodeFormat.PREFER_ARGB_8888)
//                .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_load_fail)
                    .into(((ItemViewHolder) holder).mImageViewPhotoIv);
        }
    }

    @Override
    public int getItemCount() {
        int itemSize = mNewsList.size();
        if (mIsShowFooter) {
            itemSize += 1;
        }
        return itemSize;
    }

    public void showFooter() {
        mIsShowFooter = true;
        notifyItemInserted(getItemCount());
    }

    public void hideFooter() {
        mIsShowFooter = false;
        notifyItemRemoved(getItemCount());
    }

    public void setItems(List<NewsSummary> items) {
        this.mNewsList = items;
    }

    public void addMore(List<NewsSummary> newsSummaries) {
        int startPosition = newsSummaries.size();
        mNewsList.addAll(newsSummaries);
        notifyItemRangeInserted(startPosition, mNewsList.size());
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.news_summary_title_tv)
        TextView mNewsSummaryTitleTv;
        @BindView(R.id.news_summary_photo_iv)
        ImageView mImageViewPhotoIv;
        @BindView(R.id.news_summary_digest_tv)
        TextView mNewsSummaryDigestTv;
        @BindView(R.id.news_summary_ptime_tv)
        TextView mTextViewTime;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View view) {
            super(view);
        }
    }
}
