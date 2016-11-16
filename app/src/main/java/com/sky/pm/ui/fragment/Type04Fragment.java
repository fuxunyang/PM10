package com.sky.pm.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.sky.pm.R;
import com.sky.pm.model.Latest;
import com.sky.pm.ui.BaseFragment;
import com.sky.pm.ui.adapter.Type04Adapter;
import com.sky.pm.utils.itemdecoration.DividerGridItemDecoration;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by 李彬 on 2016/11/12.
 */
@ContentView(R.layout.fragment_type04)
public class Type04Fragment extends BaseFragment {
    @ViewInject(R.id.recycle)
    private RecyclerView recyclerView;
    private Type04Adapter adapter;
    private List<Latest> list;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    setRealView();
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
//        HttpDataUtils.DMS_T_DATA_SOURCEGetIListByJson(new IDataResultImpl<List<Latest>>() {
//            @Override
//            public void onSuccessData(List<Latest> data) {
//                list = data;
//                LogUtil.d(data.size() + "");
//                handler.sendEmptyMessage(1);
//            }
//        });
    }

    private void setRealView() {

        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerGridItemDecoration(getActivity()));

        adapter = new Type04Adapter(R.layout.adapter_type04);
        recyclerView.setAdapter(adapter);
        adapter.setDatas(list);
    }

    @ViewInject(R.id.bt_real)
    private Button real;
    @ViewInject(R.id.bt_pm)
    private Button pm;

    @Event({R.id.bt_real, R.id.bt_pm})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_real:
                real.setBackgroundResource(R.mipmap.bt_06_pre);
                pm.setBackgroundResource(R.mipmap.bt_07_nor);
                break;
            case R.id.bt_pm:
                real.setBackgroundResource(R.mipmap.bt_06_nor);
                pm.setBackgroundResource(R.mipmap.bt_07_pre);
                break;

        }
    }


}
