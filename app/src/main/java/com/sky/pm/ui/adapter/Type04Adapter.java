package com.sky.pm.ui.adapter;

import android.view.View;

import com.sky.adapter.RecyclerAdapter;
import com.sky.adapter.RecyclerHolder;
import com.sky.pm.model.Latest;

/**
 * Created by 李彬 on 2016/11/15.
 */
public class Type04Adapter extends RecyclerAdapter<Latest, RecyclerHolder> {
    public Type04Adapter(int layoutId) {
        super(layoutId);
    }

    public Type04Adapter(int layoutId, int layoutFootViewId) {
        super(layoutId, layoutFootViewId);
    }

    @Override
    protected RecyclerHolder onCreateBodyHolder(View view) {
        return new RecyclerHolder(view);
    }

    @Override
    protected void onAchieveHolder(RecyclerHolder holder, int position) {

    }
}
