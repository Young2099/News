package com.demo.panguso.mvp_mode.mvp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.demo.panguso.mvp_mode.R;
import com.demo.panguso.mvp_mode.app.App;
import com.demo.panguso.mvp_mode.mvp.bean.PhotoGirl;
import com.demo.panguso.mvp_mode.mvp.ui.adapter.base.BaseRecyclerViewAdapter;
import com.demo.panguso.mvp_mode.mvp.ui.viewholder.CommonViewHolder;
import com.demo.panguso.mvp_mode.mvp.view.RationImageView;
import com.demo.panguso.mvp_mode.utils.DimenUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.inject.Inject;

/**
 * Created by Administrator on 2016/11/6.
 */

public class PhotoListAdapter extends BaseRecyclerViewAdapter<PhotoGirl> {

    private int width = DimenUtil.getScreenSize() / 2;

    private Map<Integer, Integer> mHeights = new HashMap<>();

    public static final int TYPE_PHOTO = 5;

    @Inject
    public PhotoListAdapter() {
        super(null);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case TYPE_FOOTER:
                view = getView(parent, R.layout.item_news_footer);
                CommonViewHolder commonHolder = new CommonViewHolder(view, TYPE_FOOTER);
                return commonHolder.footerViewHolder;
            case TYPE_ITEM:
                view = getView(parent, R.layout.item_photo);
                CommonViewHolder holder = new CommonViewHolder(view, TYPE_PHOTO);
                setItemOnclickEvent(holder.photoListViewHolder);
                return holder.photoListViewHolder;
            default:
                throw new RuntimeException("there is no type");
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (mIsShowFooter && isFooterPosition(position)) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    /**
     * item的点击监听事件
     *
     * @param holder
     */
    public void setItemOnclickEvent(final CommonViewHolder.PhotoListViewHolder holder) {
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(view, holder.getLayoutPosition());
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (holder instanceof CommonViewHolder.PhotoListViewHolder) {
            RationImageView imageView = ((CommonViewHolder.PhotoListViewHolder) holder).mImageView;
            imageView.setOriginalSize(width,getHeight(position));
            Glide.with(App.getAppContext())
                    .load(mList.get(position).getUrl())
//                    .asBitmap().format(DecodeFormat.PREFER_ARGB_8888)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .placeholder(R.mipmap.ic_photo_empty)
                    .error(R.mipmap.ic_load_fail)
                    .into(imageView);
        }
        setItemAppearAnimation(holder,position);
    }

    private int getHeight(int position) {
        int height;
        if(position >= mHeights.size()){
            height = (int) (width*(new Random().nextFloat() /2 +1));
            mHeights.put(position,height);
        }else {
            height = mHeights.get(position);
        }
        return height;
    }
}
