package com.sky.pm.ui.adapter;

import android.view.View;

import com.sky.adapter.RecyclerAdapter;
import com.sky.adapter.RecyclerHolder;
import com.sky.pm.R;
import com.sky.pm.model.WeatherEntity;

/**
 * Created by 李彬 on 2016/11/15.
 */
public class WeatherAdapter extends RecyclerAdapter<WeatherEntity.HourlyForecastBean, RecyclerHolder> {
    public WeatherAdapter(int layoutId, int layoutFootViewId) {
        super(layoutId, layoutFootViewId);
    }

    public WeatherAdapter(int layoutId) {
        super(layoutId);
    }

    @Override
    protected RecyclerHolder onCreateBodyHolder(View view) {
        return new RecyclerHolder(view);
    }

    @Override
    protected void onAchieveHolder(RecyclerHolder holder, int position) {
        holder.setText(R.id.tv_date, cutTemp(datas.get(position).getDate()));
        holder.setText(R.id.tv_wind, "风力：" + datas.get(position).getWind().getSc());
        holder.setText(R.id.tv_temp, datas.get(position).getTmp()+"℃");

    }
    private String cutTemp(String date) {
        return date.substring(date.length()-5,date.length());
    }
}
