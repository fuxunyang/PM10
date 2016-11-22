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
import com.sky.pm.ui.adapter.Type02Adapter;
import com.sky.pm.utils.HttpDataUtils;
import com.sky.pm.utils.itemdecoration.DividerGridItemDecoration;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by 李彬 on 2016/11/12.
 */
@ContentView(R.layout.fragment_type02)
public class Type02Fragment extends BaseFragment {

    @ViewInject(R.id.recycle)
    private RecyclerView recyclerView;
    private Type02Adapter adapter;
    private List<Latest> list;
    private int local = 0;

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

        activity.toolBar.getTvRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list == null) return;
                Map02Fragment fragment = new Map02Fragment();
                fragment.setArguments(fragment.setBundle(Double.parseDouble(list.get(local).getLatitude()),
                        Double.parseDouble(list.get(local).getLongitude())));
                activity.changeFragment("站点监测", fragment);
                activity.setRight("详情");
            }
        });
        setRealView();
        getData();
        setSeek();
    }
    private boolean inquiry = false;

    private void setSeek() {
        activity.tvInquiry.setVisibility(View.VISIBLE);
        activity.tvInquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInquiry();
            }
        });
        activity.btSeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String station = activity.tv01.getText().toString().trim();
                String name = activity.tv02.getText().toString().trim();
                HttpDataUtils.DMS_T_DATA_LATESTGetIListInfoByJson(station,name,
//                        list.get(1).getStationmn(), list.get(1).getStationName(),
                        new IDataResultImpl<List<Latest>>() {
                    @Override
                    public void onSuccessData(List<Latest> data) {
                        list = data;
                        handler.sendEmptyMessage(1);
                    }
                });

            }
        });
    }

    private void setInquiry() {
        if (inquiry) {
            activity.layout02.setVisibility(View.GONE);
            activity.layout03.setVisibility(View.GONE);
            inquiry = false;
        } else {
            activity.layout02.setVisibility(View.VISIBLE);
            activity.layout03.setVisibility(View.VISIBLE);
            inquiry = true;
        }
    }

    private void getData() {
        HttpDataUtils.DMS_T_DATA_LATESTGetIListInfoByJson(new IDataResultImpl<List<Latest>>() {
            @Override
            public void onSuccessData(List<Latest> data) {
                list = data;
                handler.sendEmptyMessage(1);
            }
        });
    }
    private void setRealView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerGridItemDecoration(getActivity()));

        adapter = new Type02Adapter(R.layout.adapter_type02);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                local = position;
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }
}
