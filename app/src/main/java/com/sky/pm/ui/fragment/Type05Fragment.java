package com.sky.pm.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sky.pm.R;
import com.sky.pm.api.IDataResultImpl;
import com.sky.pm.model.Latest;
import com.sky.pm.ui.BaseFragment;
import com.sky.pm.ui.activity.MainActivity;
import com.sky.pm.ui.adapter.Type05Adapter;
import com.sky.pm.utils.HttpDataUtils;
import com.sky.pm.utils.itemdecoration.DividerGridItemDecoration;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by 李彬 on 2016/11/12.
 */
@ContentView(R.layout.fragment_type05)
public class Type05Fragment extends BaseFragment {
    @ViewInject(R.id.recycle)
    private RecyclerView recyclerView;
    private Type05Adapter adapter;
    private MainActivity activity;
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

        activity.toolBar.getTvRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map05Fragment fragment = new Map05Fragment();
                fragment.setBundle(Double.parseDouble(list.get(0).getLatitude()),
                        Double.parseDouble(list.get(0).getLongitude()));
                activity.changeFragment("设备管理", fragment);
                activity.setRight("地图");
            }
        });
        getData();
    }

    private void getData() {
        HttpDataUtils.NMS_T_CFG_SITE_INFOGetIListByJson(new IDataResultImpl<List<Latest>>() {
            @Override
            public void onSuccessData(List<Latest> data) {
                list = data;
                LogUtil.d(data.size() + "");
                handler.sendEmptyMessage(1);
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (MainActivity) activity;
    }

    private void setRealView() {
        recyclerView.addItemDecoration(new DividerGridItemDecoration(getActivity()));

        adapter = new Type05Adapter(R.layout.adapter_type05);
        recyclerView.setAdapter(adapter);
        adapter.setDatas(list);
    }

}
