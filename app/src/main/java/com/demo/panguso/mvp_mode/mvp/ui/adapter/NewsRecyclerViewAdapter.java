package com.demo.panguso.mvp_mode.mvp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.demo.panguso.mvp_mode.R;
import com.demo.panguso.mvp_mode.app.App;
import com.demo.panguso.mvp_mode.mvp.bean.NewsSummary;
import com.demo.panguso.mvp_mode.mvp.ui.viewholder.CommonViewHolder;
import com.demo.panguso.mvp_mode.utils.DimenUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by ${yangfang} on 2016/9/9.
 */
public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @Inject
    public NewsRecyclerViewAdapter() {

    }

    public static final int TYPE_ITEM = 0;
    public static final int TYPE_FOOTER = 1;
    public static final int TYPE_PHOTO_ITEM = 2;
    private boolean mIsShowFooter;
    private List<NewsSummary> mNewsList = new ArrayList<>();
    private int mLastPosition = -1;
    private OnNewsListItemClickListener onItemClickListener;
    CommonViewHolder mCommonViewHolder;

    public interface OnNewsListItemClickListener {
        void onItemClick(View view, int position, boolean isPhoto);
    }

    public void setOnItemClickListener(OnNewsListItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public List<NewsSummary> getmNewsList() {
        return mNewsList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case TYPE_FOOTER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_footer, parent, false);
                mCommonViewHolder = new CommonViewHolder(view,TYPE_FOOTER);
                return mCommonViewHolder.footerViewHolder;
            case TYPE_ITEM:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
                mCommonViewHolder = new CommonViewHolder(view,TYPE_ITEM);
                setItemOnclick(mCommonViewHolder.itemViewHolder, false);
                return mCommonViewHolder.itemViewHolder;
            case TYPE_PHOTO_ITEM:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_photo, parent, false);
                mCommonViewHolder = new CommonViewHolder(view,TYPE_PHOTO_ITEM);
                setItemOnclick(mCommonViewHolder.photoViewHolder, true);
                return mCommonViewHolder.photoViewHolder;
            default:
                throw new RuntimeException("break prison");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        setValues(holder, position);
        //动画在加载新item的时候 动画显示
        setItemAppearAnimation(holder, position);
    }

    private void setItemOnclick(final RecyclerView.ViewHolder holder, final boolean isPhoto) {
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(holder.itemView, holder.getLayoutPosition(), isPhoto);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mIsShowFooter && (getItemCount() - 1) == position) {
            return TYPE_FOOTER;
        } else if (!TextUtils.isEmpty(mNewsList.get(position).getDigest())) {
            return TYPE_ITEM;
        } else {
            return TYPE_PHOTO_ITEM;
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

    private void setValues(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CommonViewHolder.ItemViewHolder) {
            setItemValues((CommonViewHolder.ItemViewHolder) holder, position);
        } else if (holder instanceof CommonViewHolder.PhotoViewHolder) {
            setPhotoItemValues((CommonViewHolder.PhotoViewHolder) holder, position);
        }
    }

    /**
     * 加载页面是图片的item
     * @param holder
     * @param position
     */
    private void setPhotoItemValues(CommonViewHolder.PhotoViewHolder holder, int position) {
        String title = mNewsList.get(position).getTitle();
        String pTime = mNewsList.get(position).getPtime();
        holder.mNewsSummaryTitleTv.setText(title);
        holder.mNewsSummaryPtime.setText(pTime);

        int mPhotoTreeHeight = (int) DimenUtil.dp2px(90);
        int mPhotoTwoHeight = (int) DimenUtil.dp2px(120);
        int mPhotoOneHeight = (int) DimenUtil.dp2px(150);

        String imgSrcLeft = null;
        String imgSrcMiddle = null;
        String imgSrcRight = null;

        ViewGroup.LayoutParams layoutParams = holder.mNewsSummaryPhotoIvGroup.getLayoutParams();
        NewsSummary newsSummary = mNewsList.get(position);
        if (newsSummary.getAds() != null) {
            List<NewsSummary.AdsBean> mAdsBeen = newsSummary.getAds();
            int size = mAdsBeen.size();
            if (size >= 3) {
                imgSrcLeft = mAdsBeen.get(0).getImgsrc();
                imgSrcMiddle = mAdsBeen.get(1).getImgsrc();
                imgSrcRight = mAdsBeen.get(2).getImgsrc();
                layoutParams.height = mPhotoTreeHeight;
                holder.mNewsSummaryTitleTv.setText(App.getAppContext()
                        .getString(R.string.photos,mAdsBeen.get(0).getTitle()));
            } else if (size >= 2) {
                imgSrcLeft = mAdsBeen.get(0).getImgsrc();
                imgSrcMiddle = mAdsBeen.get(1).getImgsrc();
                layoutParams.height = mPhotoTwoHeight;
            } else if (size >= 1) {
                imgSrcLeft = mAdsBeen.get(0).getImgsrc();
                layoutParams.height = mPhotoOneHeight;
            }
        } else if (newsSummary.getImgextra() != null) {
            int size = newsSummary.getImgextra().size();
            if (size >= 3) {
                imgSrcLeft = newsSummary.getImgextra().get(0).getImgsrc();
                imgSrcMiddle = newsSummary.getImgextra().get(1).getImgsrc();
                imgSrcRight = newsSummary.getImgextra().get(2).getImgsrc();
                layoutParams.height = mPhotoTreeHeight;

            } else if (size >= 2) {
                imgSrcLeft = newsSummary.getImgextra().get(0).getImgsrc();
                imgSrcMiddle = newsSummary.getImgextra().get(1).getImgsrc();
                layoutParams.height = mPhotoTwoHeight;
            } else if (size >= 1) {
                imgSrcLeft = newsSummary.getImgextra().get(0).getImgsrc();
                layoutParams.height = mPhotoOneHeight;
            }
        } else {
            imgSrcLeft = newsSummary.getImgsrc();
            layoutParams.height = mPhotoOneHeight;
        }
        setPhotoImageView(holder, imgSrcLeft, imgSrcMiddle, imgSrcRight);
        holder.mNewsSummaryPhotoIvGroup.setLayoutParams(layoutParams);
    }

    private void setPhotoImageView(CommonViewHolder.PhotoViewHolder holder, String imgSrcLeft, String imgSrcMiddle, String imgSrcRight) {
        if (imgSrcLeft != null) {
            showAndSetPhoto(holder.mNewsSummaryPhotoLeft, imgSrcLeft);
        } else {
            holder.mNewsSummaryPhotoLeft.setVisibility(View.GONE);
        }
        if (imgSrcMiddle != null) {
            showAndSetPhoto(holder.mNewsSummaryPhotoMiddle, imgSrcMiddle);
        } else {
            holder.mNewsSummaryPhotoRight.setVisibility(View.GONE);
        }
        if(imgSrcRight != null){
            showAndSetPhoto(holder.mNewsSummaryPhotoRight,imgSrcRight);
        }else {
            holder.mNewsSummaryPhotoRight.setVisibility(View.GONE);
        }
    }

    private void showAndSetPhoto(ImageView imageView, String imgSrc) {
        imageView.setVisibility(View.VISIBLE);
        Glide.with(App.getAppContext()).load(imgSrc)
                .asBitmap().format(DecodeFormat.PREFER_ARGB_8888)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.mipmap.ic_load_fail)
                .into(imageView);
    }

    private void setItemValues(CommonViewHolder.ItemViewHolder holder, int position) {
        String title = mNewsList.get(position).getLtitle();
        if (title == null) {
            title = mNewsList.get(position).getTitle();
        }
        String ptime = mNewsList.get(position).getPtime();
        String digest = mNewsList.get(position).getDigest();
        String imgSrc = mNewsList.get(position).getImgsrc();

        holder.mNewsSummaryTitleTv.setText(title);
        holder.mTextViewTime.setText(ptime);
        holder.mNewsSummaryDigestTv.setText(digest);
        Glide.with(App.getAppContext()).load(imgSrc)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .format(DecodeFormat.PREFER_ARGB_8888)
//                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_load_fail)
                .into(holder.mImageViewPhotoIv);
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
}
