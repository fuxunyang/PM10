package com.sky.pm.ui.adapter;

import android.view.View;
import android.widget.ImageView;

import com.sky.adapter.RecyclerAdapter;
import com.sky.adapter.RecyclerHolder;
import com.sky.pm.R;

/**
 * Created by 李彬 on 2016/11/12.
 */
public class MainAdapter extends RecyclerAdapter<Integer, RecyclerHolder> {
    public MainAdapter(int layoutId) {
        super(layoutId);
    }

    public MainAdapter(int layoutId, int layoutFootViewId) {
        super(layoutId, layoutFootViewId);
    }

    @Override
    protected RecyclerHolder onCreateBodyHolder(View view) {
        return new RecyclerHolder(view);
    }

    @Override
    protected void onAchieveHolder(RecyclerHolder holder, int position) {
        ImageView img = holder.getView(R.id.img_describe);
        img.setImageResource(datas.get(position));
    }
}
