package com.demo.panguso.mvp_mode.mvp.ui.adapter.base;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.demo.panguso.mvp_mode.R;
import com.demo.panguso.mvp_mode.listener.OnItemClickListener;

import java.util.List;


/**
 * Created by ${yangfang} on 2016/11/4.
 */

public class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected List<T> mList;
    public static final int TYPE_ITEM = 0;
    public static final int TYPE_FOOTER = 1;
    protected int mLastPosition;
    protected boolean mIsShowFooter;
    protected OnItemClickListener mOnItemClickListener;

    public BaseRecyclerViewAdapter(List<T> mList) {
        this.mList = mList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if (getItemViewType(position) == TYPE_FOOTER) {
            if (layoutParams != null) {
                if (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
                    StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
                    params.setFullSpan(true);//设置图片充满整个屏幕
                }
            }
        }
    }

    /**
     * 实例化其布局
     *
     * @param parent
     * @param layout
     * @return
     */
    protected View getView(ViewGroup parent, int layout) {
        return LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
    }

    @Override
    public int getItemCount() {
        if (mList == null) {
            return 0;
        }
        int itemSize = mList.size();
        return itemSize;
    }

    protected void setItemAppearAnimation(RecyclerView.ViewHolder holder, int position) {
        if (position > mLastPosition) {
            Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(),
                    R.anim.item_bottom);
            holder.itemView.startAnimation(animation);
            mLastPosition = position;
        }
    }

    protected boolean isFooterPosition(int position) {
        return (getItemCount() - 1) == position;
    }

    public void add(int position, T item) {
        mList.add(position, item);
        notifyItemInserted(position);
    }

    public void addMore(List<T> data) {
        int statrPosition = mList.size();
        mList.addAll(data);
        notifyItemRangeChanged(statrPosition, mList.size());
    }

    public void delete(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
    }

    public List<T> getList() {
        return mList;
    }

    public void setList(List<T> items) {
        mList = items;
    }

    /**
     * 显示Footer
     */
    public void showFooter() {
        mIsShowFooter = true;
        notifyItemInserted(getItemCount());
    }

    /**
     * 隐藏底部的footer
     */
    public void hideFooter() {
        mIsShowFooter = false;
        notifyItemRemoved(getItemCount());
    }


}
