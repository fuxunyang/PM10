package com.sky.pm.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sky.pm.R;
import com.sky.pm.api.IDataResultImpl;
import com.sky.pm.model.Latest;
import com.sky.pm.ui.BaseFragment;
import com.sky.pm.ui.adapter.NewsAdapter;
import com.sky.pm.utils.HttpDataUtils;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 李彬 on 2016/11/9.
 */
@ContentView(R.layout.fragment_three)
public class BusinessFragment extends BaseFragment {
    @ViewInject(R.id.recycle)
    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private List<Latest> list;
    //瀑布流布局
//    private StaggeredGridLayoutManager layoutManager;
    private List<String> texts;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    initView();
                    break;
            }
        }
    };

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getData();
    }

    private void getData() {
        HttpDataUtils.NewsGetAllListByJson(new IDataResultImpl<List<Latest>>() {
            @Override
            public void onSuccessData(List<Latest> data) {
                list = data;
                LogUtil.d(data.size() + "");
                handler.sendEmptyMessage(1);
            }
        });
    }

    //

    private void initView() {
        texts = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            texts.add("道可道，非常道；名可名，非常名。" +
                    "    无名，天地之始；有名，万物之母。" +
                    "    故常无，欲以观其妙；常有，欲以观其徼。" +
                    "    此两者同出而异名。同谓之玄，玄之又玄，众妙之门。");

        }
        recyclerView.setHasFixedSize(true);
        //瀑布流布局
        adapter = new NewsAdapter(R.layout.adapter_text);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        adapter.setDatas(texts);

    }
}
