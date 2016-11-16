package com.sky.pm.ui.adapter;

import android.view.View;

import com.sky.adapter.RecyclerAdapter;
import com.sky.adapter.RecyclerHolder;
import com.sky.pm.R;
import com.sky.pm.model.Latest;

/**
 * Created by 李彬 on 2016/11/13.
 */
public class Type05Adapter extends RecyclerAdapter<Latest,RecyclerHolder> {
    public Type05Adapter(int layoutId) {
        super(layoutId);
    }

    public Type05Adapter(int layoutId, int layoutFootViewId) {
        super(layoutId, layoutFootViewId);
    }

    @Override
    protected RecyclerHolder onCreateBodyHolder(View view) {
        return new RecyclerHolder(view);
    }

    @Override
    protected void onAchieveHolder(RecyclerHolder holder, int position) {
        holder.setText(R.id.tv_id,position+"");
        holder.setText(R.id.tv_name,datas.get(position).getStationName());
        holder.setText(R.id.tv_area,datas.get(position).getStationmn());


    }
}
