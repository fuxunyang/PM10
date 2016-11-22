package com.sky.pm.ui.adapter;

import android.view.View;

import com.sky.adapter.RecyclerAdapter;
import com.sky.adapter.RecyclerHolder;
import com.sky.pm.R;
import com.sky.pm.model.NewsModel;

/**
 * Created by 李彬 on 2016/11/12.
 */
public class NewsAdapter extends RecyclerAdapter<NewsModel, RecyclerHolder> {
    public NewsAdapter(int layoutId) {
        super(layoutId);
    }

    public NewsAdapter(int layoutId, int layoutFootViewId) {
        super(layoutId, layoutFootViewId);
    }

    @Override
    protected RecyclerHolder onCreateBodyHolder(View view) {
        return new RecyclerHolder(view);
    }

    @Override
    protected void onAchieveHolder(RecyclerHolder holder, int position) {
        holder.setText(R.id.tv_text,datas.get(position).getTitle());

    }
}
