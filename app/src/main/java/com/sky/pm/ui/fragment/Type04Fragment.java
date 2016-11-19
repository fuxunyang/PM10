package com.sky.pm.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.sky.adapter.RecyclerAdapter;
import com.sky.pm.R;
import com.sky.pm.api.IDataResultImpl;
import com.sky.pm.model.Latest;
import com.sky.pm.ui.BaseFragment;
import com.sky.pm.ui.adapter.Type04Adapter;
import com.sky.pm.ui.widget.LineChartView;
import com.sky.pm.utils.HttpDataUtils;
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
    @ViewInject(R.id.lineChart)
    private LineChartView lineChart;
    @ViewInject(R.id.bt_one)
    private Button btOne;
    @ViewInject(R.id.bt_day)
    private Button btDay;

    @ViewInject(R.id.recycle)
    private RecyclerView recyclerView;
    private Type04Adapter adapter;
    private List<Latest> list;
    private List<Latest> one;
    private List<Latest> day;
    private int hourOrMin = 2;


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    adapter.setDatas(list);
                    hourOrMin = 2;
                    setBtBg(btDay, btOne);

                    id = adapter.getDatas().get(0).getStationId();
                    getDay(id);
                    break;
                case 111:
                    lineChart.setRateDates(one, hourOrMin);
                    break;
                case 222:
                    lineChart.setRateDates(day, hourOrMin);
                    break;
            }
        }
    };

    private void setBtBg(Button one, Button two) {
        DrawableCompat.setTint(one.getBackground(), getResources().getColor(R.color.yellow));
        DrawableCompat.setTint(two.getBackground(), getResources().getColor(R.color.gray01));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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

    private void getOne(String id) {
        HttpDataUtils.DMS_T_DATA_MINUTEGetListByPageByJson(id, new IDataResultImpl<List<Latest>>() {
            @Override
            public void onSuccessData(List<Latest> data) {
                one = data;
                handler.sendEmptyMessage(111);
            }
        });
    }

    private void getDay(String id) {
        HttpDataUtils.DMS_T_DATA_HOURGetListByPageByJson(id, new IDataResultImpl<List<Latest>>() {
            @Override
            public void onSuccessData(List<Latest> data) {
                day = data;
                handler.sendEmptyMessage(222);
            }
        });
    }

    String id;

    private void setRealView() {

        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerGridItemDecoration(getActivity()));

        adapter = new Type04Adapter(R.layout.adapter_type04);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                setBtBg(btDay, btOne);
                id = adapter.getDatas().get(position).getStationId();
                getDay(id);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    @Event({R.id.bt_day, R.id.bt_one})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_day:
                hourOrMin = 2;
                getDay(id);
                setBtBg(btDay, btOne);

                break;
            case R.id.bt_one:
                getOne(id);
                hourOrMin = 5;
                setBtBg(btOne, btDay);
                break;
        }
    }

}
