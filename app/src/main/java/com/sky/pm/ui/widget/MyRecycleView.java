package com.sky.pm.ui.widget;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * @author LiBin
 * @Description: 要点在于布局的设定，其次为分割线以及item的添加与删除待修改
 * @date 2015/9/29 16:43
 */
public class MyRecycleView extends RecyclerView {

    public MyRecycleView(Context context) {
        this(context, null);
    }

    public MyRecycleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyRecycleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //设置布局管理
        setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        //分割线效果
//        addItemDecoration(new DividerGridItemDecoration(context));
//        addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
        //addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));
        // 添加删除时的动画效果
        setItemAnimator(new DefaultItemAnimator());
        //setItemAnimator(new DefaultItemAnimator());
    }
}