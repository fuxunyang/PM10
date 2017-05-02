package com.sky.pm.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sky.pm.R;
import com.sky.pm.api.IDataResultImpl;
import com.sky.pm.model.NewsModel;
import com.sky.pm.ui.BaseFragment;
import com.sky.pm.ui.adapter.NewsAdapter;
import com.sky.pm.utils.HttpDataUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by 李彬 on 2016/11/9.
 */
@ContentView(R.layout.fragment_three)
public class BusinessFragment extends BaseFragment {
    @ViewInject(R.id.recycle)
    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private List<NewsModel> list;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    adapter.setDatas(list);
                    break;
            }
        }
    };

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        getData();
    }

    private void getData() {
        HttpDataUtils.NewsGetAllListByJson(new IDataResultImpl<List<NewsModel>>() {
            @Override
            public void onSuccessData(List<NewsModel> data) {
                list = data;
                handler.sendEmptyMessage(1);
            }
        });
    }

    //

    private void initView() {
        recyclerView.setHasFixedSize(true);
        //瀑布流布局
        adapter = new NewsAdapter(R.layout.adapter_text);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }
        });

    }
}
