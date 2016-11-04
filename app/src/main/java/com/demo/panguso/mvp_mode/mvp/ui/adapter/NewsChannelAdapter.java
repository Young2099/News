package com.demo.panguso.mvp_mode.mvp.ui.adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo.panguso.mvp_mode.R;
import com.demo.panguso.mvp_mode.app.App;
import com.demo.panguso.mvp_mode.listener.ChannelItemMoveEvent;
import com.demo.panguso.mvp_mode.listener.OnItemClickListener;
import com.demo.panguso.mvp_mode.mvp.ui.adapter.base.BaseRecyclerViewAdapter;
import com.demo.panguso.mvp_mode.mvp.view.ItemDragHelperCallback;
import com.demo.panguso.mvp_mode.utils.RxBus;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import greendao.NewsChannelTable;

/**
 * Created by ${yangfang} on 2016/10/8.
 */
public class NewsChannelAdapter extends BaseRecyclerViewAdapter<NewsChannelTable> implements ItemDragHelperCallback.OnItemMoveListener {
    private static final int IS_CHANEL_FIXED = 0;
    private static final int IS_CHANEL_NO_FIXED = 1;
    private ItemDragHelperCallback mItemDragHelperCallback;
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setItemDragHelperCallback(ItemDragHelperCallback itemDragHelperCallback) {
        mItemDragHelperCallback = itemDragHelperCallback;
    }

    public NewsChannelAdapter(List<NewsChannelTable> mNewsChannelTables) {
        super(mNewsChannelTables);
    }

    @Override
    public NewsChannelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_channel, parent, false);
        NewsChannelViewHolder newsChannelViewHolder = new NewsChannelViewHolder(view);
        handleLongPress(newsChannelViewHolder);
        handleOnClick(newsChannelViewHolder);
        return newsChannelViewHolder;
    }

    private void handleOnClick(final NewsChannelViewHolder newsChannelViewHolder) {
        if (mOnItemClickListener != null) {
            newsChannelViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(view, newsChannelViewHolder.getLayoutPosition());
                }
            });
        }
    }

    private void handleLongPress(final NewsChannelViewHolder newsChannelViewHolder) {
        if (mItemDragHelperCallback != null) {
            newsChannelViewHolder.itemView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    NewsChannelTable newsChannelTable = mList.get(newsChannelViewHolder.getLayoutPosition());
                    boolean isChannelFixed = newsChannelTable.getNewsChannelFixed();
                    if (isChannelFixed) {
                        mItemDragHelperCallback.setLongPressEnabled(false);
                    } else {
                        mItemDragHelperCallback.setLongPressEnabled(true);
                    }
                    return false;
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NewsChannelTable newsChannelTable = mList.get(position);
        String newsChannelName = newsChannelTable.getNewsChannelName();
        NewsChannelViewHolder viewHolder = (NewsChannelViewHolder) holder;
        viewHolder.mNewsChannelTv.setText(newsChannelName);
        if (newsChannelTable.getNewsChannelIndex() == 0) {
            viewHolder.mNewsChannelTv.setTextColor(ContextCompat.getColor(App.getAppContext(), R.color.alpha_20_black));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mList.get(position).getNewsChannelFixed()) {
            return IS_CHANEL_FIXED;
        } else {
            return IS_CHANEL_NO_FIXED;
        }
    }

    /**
     * 回调接口,这是我的频道的长按调换顺序的动作
     *
     * @param formPosition
     * @param toPosition
     * @return
     */
    @Override
    public boolean onItemMoved(int formPosition, int toPosition) {
        if (mList.get(formPosition).getNewsChannelFixed() || mList.get(toPosition).getNewsChannelFixed()) {
            return false;
        }
        Collections.swap(mList, formPosition, toPosition);
        notifyItemMoved(formPosition, toPosition);
        RxBus.getInstance().post(new ChannelItemMoveEvent(formPosition, toPosition));
        return true;
    }

    class NewsChannelViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.news_channel_tv)
        TextView mNewsChannelTv;

        public NewsChannelViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public List<NewsChannelTable> getData() {
        return mList;
    }
}
