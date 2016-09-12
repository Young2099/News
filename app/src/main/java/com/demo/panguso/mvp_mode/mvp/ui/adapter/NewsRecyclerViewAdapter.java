package com.demo.panguso.mvp_mode.mvp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo.panguso.mvp_mode.R;

import java.util.List;

/**
 * Created by ${yangfang} on 2016/9/9.
 */
public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.ViewHolder>{

    private List<String> mNewsList;

    public NewsRecyclerViewAdapter() {

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_news,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String msg = mNewsList.get(position);
        holder.mText.setText(msg);
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    public void setItems(List<String> items) {
        this.mNewsList = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private  TextView mText;
        public ViewHolder(View itemView) {
            super(itemView);
            mText= (TextView) itemView.findViewById(R.id.msg);
        }
    }

}
