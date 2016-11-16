package com.sky.pm.ui.adapter;

import android.view.View;
import android.widget.ImageView;

import com.sky.adapter.RecyclerAdapter;
import com.sky.adapter.RecyclerHolder;
import com.sky.pm.R;
import com.sky.pm.model.PieModel;

/**
 * Created by 李彬 on 2016/11/13.
 */
public class Type01Adapter extends RecyclerAdapter<PieModel, RecyclerHolder> {
    public Type01Adapter(int layoutId) {
        super(layoutId);
    }

    public Type01Adapter(int layoutId, int layoutFootViewId) {
        super(layoutId, layoutFootViewId);
    }

    @Override
    protected RecyclerHolder onCreateBodyHolder(View view) {
        return new RecyclerHolder(view);
    }

    @Override
    protected void onAchieveHolder(RecyclerHolder holder, int position) {
        ImageView img = holder.getView(R.id.img_describe);
        img.setImageResource(datas.get(position).getCorlor());
        holder.setText(R.id.tv_text, datas.get(position).getText());
    }
}
