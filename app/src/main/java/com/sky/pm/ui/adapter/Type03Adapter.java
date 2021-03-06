package com.sky.pm.ui.adapter;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sky.adapter.RecyclerAdapter;
import com.sky.adapter.RecyclerHolder;
import com.sky.pm.R;
import com.sky.pm.model.Latest;

/**
 * Created by 李彬 on 2016/11/16.
 */
public class Type03Adapter extends RecyclerAdapter<Latest, RecyclerHolder> {
    public Type03Adapter(int layoutId) {
        super(layoutId);
    }

    public Type03Adapter(int layoutId, int layoutFootViewId) {
        super(layoutId, layoutFootViewId);
    }

    @Override
    protected RecyclerHolder onCreateBodyHolder(View view) {
        return new RecyclerHolder(view);
    }

    @Override
    protected void onAchieveHolder(RecyclerHolder holder, int position) {
        holder.setText(R.id.tv_name, datas.get(position).getStationName());
        holder.setText(R.id.tv_pm10, datas.get(position).getDataValue());
        int level = datas.get(position).getAQILevel();
        int AQI = (int) datas.get(position).getAQI();
        TextView tvAQI1 = holder.getView(R.id.tv_aqi1);
        tvAQI1.setVisibility(View.GONE);
        RelativeLayout layout = holder.getView(R.id.layout);
        layout.setVisibility(View.VISIBLE);
        TextView tvAQI = holder.getView(R.id.tv_aqi);
        tvAQI.setText(AQI + "");
        if (level == 1) {
            holder.setText(R.id.tv_level, "优");
            tvAQI.setBackground(context.getResources().getDrawable(R.drawable.type01_bg));
//            DrawableCompat.setTint(tvAQI.getBackground(),context.getResources().getColor(R.color.pie1));
        } else if (level == 2) {
            holder.setText(R.id.tv_level, "良");
            tvAQI.setBackground(context.getResources().getDrawable(R.drawable.type02_bg));
        } else if (level == 3) {
            holder.setText(R.id.tv_level, "轻度污染");
            tvAQI.setBackground(context.getResources().getDrawable(R.drawable.type03_bg));
        } else if (level == 4) {
            holder.setText(R.id.tv_level, "中度污染");
            tvAQI.setBackground(context.getResources().getDrawable(R.drawable.type04_bg));
        } else if (level == 5) {
            holder.setText(R.id.tv_level, "重度污染");
            tvAQI.setBackground(context.getResources().getDrawable(R.drawable.type05_bg));
        } else if (level >= 6) {
            holder.setText(R.id.tv_level, "严重污染");
            tvAQI.setBackground(context.getResources().getDrawable(R.drawable.type06_bg));
        }

    }
}
