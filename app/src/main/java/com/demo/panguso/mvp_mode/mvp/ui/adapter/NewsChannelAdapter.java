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
import com.demo.panguso.mvp_mode.mvp.view.ItemDragHelperCallback;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import greendao.NewsChannelTable;

/**
 * Created by ${yangfang} on 2016/10/8.
 */
public class NewsChannelAdapter extends RecyclerView.Adapter<NewsChannelAdapter.NewsChannelViewHolder> implements ItemDragHelperCallback.OnItemMoveListener {
    private List<NewsChannelTable> mNewsChannelTables;
    private static final int IS_CHANEL_FIXED = 0;
    private static final int IS_CHANEL_NO_FIXED = 1;
    private ItemDragHelperCallback mItemDragHelperCallback;

    private void setItemDragHelperCallback(ItemDragHelperCallback itemDragHelperCallback) {
        mItemDragHelperCallback = itemDragHelperCallback;
    }

    public NewsChannelAdapter(List<NewsChannelTable> mNewsChannelTables) {
        this.mNewsChannelTables = mNewsChannelTables;
    }

    @Override
    public NewsChannelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_channel, null);
        NewsChannelViewHolder newsChannelViewHolder = new NewsChannelViewHolder(view);
        handleLongPress(newsChannelViewHolder);
        return newsChannelViewHolder;
    }

    private void handleLongPress(final NewsChannelViewHolder newsChannelViewHolder) {
        if (mItemDragHelperCallback != null) {
            newsChannelViewHolder.itemView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    NewsChannelTable newsChannelTable = mNewsChannelTables.get(newsChannelViewHolder.getLayoutPosition());
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
    public void onBindViewHolder(NewsChannelAdapter.NewsChannelViewHolder holder, int position) {
        NewsChannelTable newsChannelTable = mNewsChannelTables.get(position);
        String newsChannelName = newsChannelTable.getNewsChannelName();
        holder.mNewsChannelTv.setText(newsChannelName);
        if (newsChannelTable.getNewsChannelIndex() == 0) {
            holder.mNewsChannelTv.setTextColor(ContextCompat.getColor(App.getAppContext(), R.color.alpha_20_black));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mNewsChannelTables.get(position).getNewsChannelFixed()) {
            return IS_CHANEL_FIXED;
        } else {
            return IS_CHANEL_NO_FIXED;
        }
    }

    @Override
    public int getItemCount() {
        return mNewsChannelTables.size();
    }

    @Override
    public boolean onItemMoved(int formPosition, int toPosition) {
        if (mNewsChannelTables.get(formPosition).getNewsChannelFixed() || mNewsChannelTables.get(toPosition).getNewsChannelFixed()) {
            return false;
        }
        Collections.swap(mNewsChannelTables, formPosition, toPosition);
        notifyItemMoved(formPosition, toPosition);
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
}
