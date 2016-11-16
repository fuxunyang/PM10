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
    private MainActivity activity;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    adapter.setDatas(list);
                    break;
                case 222:
//                    lineChart.fillDateForRateDate(day);
                    break;
            }
        }
    };
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (MainActivity) activity;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        activity.toolBar.getTvRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.changeFragment("厂区监测",new MapFragment());
                activity.setRight("详情");
            }
        });
        setRealView();
        getData();
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

        adapter = new Type03Adapter(R.layout.adapter_type03);
        recyclerView.setAdapter(adapter);
    }


}
