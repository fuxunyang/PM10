package com.sky.pm.ui.adapter;

import android.view.View;

import com.sky.adapter.RecyclerAdapter;
import com.sky.adapter.RecyclerHolder;
import com.sky.pm.R;
import com.sky.pm.model.Latest;

/**
 * Created by 李彬 on 2016/11/13.
 */
public class TypeRealAdapter extends RecyclerAdapter<Latest, RecyclerHolder> {
    public TypeRealAdapter(int layoutId) {
        super(layoutId);
    }

    @Override
    protected RecyclerHolder onCreateBodyHolder(View view) {
        return new RecyclerHolder(view);
    }

    @Override
    protected void onAchieveHolder(RecyclerHolder holder, int position) {
        holder.setText(R.id.tv_id, position + 1 + "");
        holder.setText(R.id.tv_name, datas.get(position).getStationName());
        holder.setText(R.id.tv_date, datas.get(position).getDataTime());
        holder.setText(R.id.tv_pm, datas.get(position).getDataValue());

    }
}
