package com.sky.pm.ui.dialog;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sky.adapter.RecyclerAdapter;
import com.sky.adapter.RecyclerHolder;
import com.sky.pm.R;
import com.sky.pm.model.Level;

/**
 * @author sky QQ:1136096189
 * @Description: TODO
 * @date 16/1/11 下午1:14
 */
public class AreaPop extends BasePop<Level> {
    private RecyclerView recycle;

    private RecyclerAdapter<Level, RecyclerHolder> adapter;

    public AreaPop(View view) {
        super(view);
    }

    public AreaPop(View view, int width, int height) {
        super(view, width, height);
    }

    @Override
    protected void initView() {
        super.initView();
        recycle = (RecyclerView) view.findViewById(R.id.recycle);
        adapter = new RecyclerAdapter<Level, RecyclerHolder>(R.layout.pop_level) {
            @Override
            protected RecyclerHolder onCreateBodyHolder(View view) {
                return new RecyclerHolder(view);
            }

            @Override
            protected void onAchieveHolder(RecyclerHolder holder, int position) {
                holder.setText(R.id.tv, datas.get(position).getAQIName());
            }
        };
        recycle.setAdapter(adapter);
    }

    @Override
    protected void initEvent() {
        adapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                TextView tv= (TextView) view;
                if (itemClickListener != null)
                    itemClickListener.onItemClick(position);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    @Override
    protected void initDatas() {
        adapter.setDatas(popDatas);
    }
}
