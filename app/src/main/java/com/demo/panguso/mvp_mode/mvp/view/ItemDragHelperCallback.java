package com.demo.panguso.mvp_mode.mvp.view;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.demo.panguso.mvp_mode.mvp.ui.adapter.NewsChannelAdapter;

/**
 * Created by ${yangfang} on 2016/10/8.
 */
public class ItemDragHelperCallback extends ItemTouchHelper.Callback {
    private OnItemMoveListener mOnItemMoveListener;
    private boolean mIsLongPressEnabled;
    private RecyclerView dragFlags;

    public void setLongPressEnabled(boolean LongPressEnabled) {
        this.mIsLongPressEnabled = LongPressEnabled;
    }


    public interface OnItemMoveListener {
        boolean onItemMoved(int formPosition, int toPosition);
    }

    public ItemDragHelperCallback(NewsChannelAdapter newsChannelAdapter) {
        mOnItemMoveListener = newsChannelAdapter;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return mIsLongPressEnabled;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = setDragFlags(recyclerView);
        int swipeFlags = 0;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    public int setDragFlags(RecyclerView recyclerView) {
        int dragFlags;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager || layoutManager instanceof StaggeredGridLayoutManager) {
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        } else {
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        }
        return dragFlags;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        if (isDifferentItemViewType(viewHolder, target)) {
            return false;
        }
        int fromPosition = viewHolder.getAdapterPosition();
        int toPosition = target.getAdapterPosition();
        return mOnItemMoveListener.onItemMoved(fromPosition, toPosition);

    }

    private boolean isDifferentItemViewType(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return viewHolder.getItemViewType() != target.getItemViewType();
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }
}
