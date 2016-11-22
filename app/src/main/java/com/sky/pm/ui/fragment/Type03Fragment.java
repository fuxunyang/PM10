package com.sky.pm.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sky.adapter.RecyclerAdapter;
import com.sky.pm.R;
import com.sky.pm.api.IDataResultImpl;
import com.sky.pm.model.Latest;
import com.sky.pm.ui.BaseFragment;
import com.sky.pm.ui.adapter.Type03Adapter;
import com.sky.pm.ui.widget.LineChartView;
import com.sky.pm.utils.HttpDataUtils;
import com.sky.pm.utils.itemdecoration.DividerGridItemDecoration;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by 李彬 on 2016/11/12.
 */
@ContentView(R.layout.fragment_type03)
public class Type03Fragment extends BaseFragment {
    @ViewInject(R.id.lineChart)
    private LineChartView lineChart;

    @ViewInject(R.id.recycle)
    private RecyclerView recyclerView;
    private Type03Adapter adapter;
    private List<Latest> list;
    private List<Latest> day;
    private int local= 0;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    adapter.setDatas(list);
                    getDay(adapter.getDatas().get(0).getStationId());
                    break;
                case 222:
                    lineChart.setRateDates(day);
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
                if (list==null)return;
                Map03Fragment fragment = new Map03Fragment();
                fragment.setArguments(fragment.setBundle(Double.parseDouble(list.get(local).getLatitude()),
                        Double.parseDouble(list.get(local).getLongitude())));
                activity.changeFragment("AQI指数", fragment);
                activity.setRight("详情");
            }
        });
        setRealView();
        getData();
    }


    private void getData() {
        HttpDataUtils.DMS_T_DATA_LATESTGetIListByJson(new IDataResultImpl<List<Latest>>() {
            @Override
            public void onSuccessData(List<Latest> data) {
                list = data;
                handler.sendEmptyMessage(1);
            }
        });
    }

    private void getDay(String id) {
        HttpDataUtils.DMS_T_DATA_HOURGetListByPageByJson(id, new IDataResultImpl<List<Latest>>() {
            @Override
            public void onSuccessData(List<Latest> data) {
                if (data != null)
                    day = data;

                handler.sendEmptyMessage(222);
            }
        });
    }

    private void setRealView() {

        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerGridItemDecoration(getActivity()));

        adapter = new Type03Adapter(R.layout.adapter_type03);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                local= position;
                getDay(adapter.getDatas().get(position).getStationId());
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }


}
