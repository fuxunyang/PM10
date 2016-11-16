package com.sky.pm.ui.adapter;

import android.view.View;

import com.sky.adapter.RecyclerAdapter;
import com.sky.adapter.RecyclerHolder;
import com.sky.pm.R;
import com.sky.pm.model.WeatherEntity;

/**
 * Created by 李彬 on 2016/11/15.
 */
public class Weather01Adapter extends RecyclerAdapter<WeatherEntity.DailyForecastBean, RecyclerHolder> {
    public Weather01Adapter(int layoutId) {
        super(layoutId);
    }

    public Weather01Adapter(int layoutId, int layoutFootViewId) {
        super(layoutId, layoutFootViewId);
    }

    @Override
    protected RecyclerHolder onCreateBodyHolder(View view) {
        return new RecyclerHolder(view);
    }

    @Override
    protected void onAchieveHolder(RecyclerHolder holder, int position) {
        holder.setText(R.id.tv_date,datas.get(position).getDate());
        holder.setText(R.id.tv_weather,datas.get(position).getCond().getTxt_d()+"转"+datas.get(position).getCond().getTxt_n());
        holder.setText(R.id.tv_wind,datas.get(position).getWind().getDir()+datas.get(position).getWind().getSpd()+"级");
        holder.setText(R.id.tv_temp,datas.get(position).getTmp().getMin()+"℃"
                +"~"+datas.get(position).getTmp().getMax()+"℃");

    }
}
