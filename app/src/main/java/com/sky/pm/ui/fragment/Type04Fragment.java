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
import com.sky.pm.ui.adapter.Type04Adapter;
import com.sky.pm.ui.widget.LineChartView;
import com.sky.pm.utils.HttpDataUtils;
import com.sky.pm.utils.itemdecoration.DividerGridItemDecoration;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by 李彬 on 2016/11/12.
 */
@ContentView(R.layout.fragment_type04)
public class Type04Fragment extends BaseFragment {
    @ViewInject(R.id.lineChart)
    private LineChartView lineChart;

    @ViewInject(R.id.recycle)
    private RecyclerView recyclerView;
    private Type04Adapter adapter;
    private List<Latest> list;
    private List<Latest> one;
    private List<Latest> day;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    adapter.setDatas(list);
                    break;
                case 111:
//                    lineChart.fillDateForRateDate(one);
                    break;
                case 222:
//                    lineChart.fillDateForRateDate(day);
                    break;
            }
        }
    };

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRealView();
        getData();
        getOne();
        getDay();
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

    private void getOne() {
        HttpDataUtils.DMS_T_DATA_MINUTEGetListByPageByJson(new IDataResultImpl<List<Latest>>() {
            @Override
            public void onSuccessData(List<Latest> data) {
                one = data;
                handler.sendEmptyMessage(111);
            }
        });
    }

    private void getDay() {
        HttpDataUtils.DMS_T_DATA_HOURGetListByPageByJson(new IDataResultImpl<List<Latest>>() {
            @Override
            public void onSuccessData(List<Latest> data) {
                day = data;
                handler.sendEmptyMessage(222);
            }
        });
    }

    private void setRealView() {

        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerGridItemDecoration(getActivity()));

        adapter = new Type04Adapter(R.layout.adapter_type04);
        recyclerView.setAdapter(adapter);
    }

}
