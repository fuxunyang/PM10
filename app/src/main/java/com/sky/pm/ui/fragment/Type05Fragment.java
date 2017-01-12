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
import com.sky.pm.ui.adapter.Type05Adapter;
import com.sky.pm.utils.HttpDataUtils;
import com.sky.pm.utils.itemdecoration.DividerGridItemDecoration;

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
    private List<Latest> list;
    private int local= 0;

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
                if (list==null)return;
                Map05Fragment fragment = new Map05Fragment();
                fragment.setArguments(fragment.setBundle(Double.parseDouble(list.get(local).getLatitude()),
                        Double.parseDouble(list.get(local).getLongitude())));
                activity.changeFragment("设备管理", fragment);
                activity.setRight("地图");
            }
        });
        setRealView();
        setSeek();
        getData();
    }

    private void getData() {
        HttpDataUtils.NMS_T_CFG_SITE_INFOGetIListByJson(new IDataResultImpl<List<Latest>>() {
            @Override
            public void onSuccessData(List<Latest> data) {
                list = data;
                handler.sendEmptyMessage(1);
            }
        });
    }
    private boolean inquiry = false;

    public void setSeek() {
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
                HttpDataUtils.NMS_T_CFG_SITE_INFOGetIListByJson(
                        station,name,
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

    public void setInquiry() {
        if (inquiry) {
            activity.layout02.setVisibility(View.GONE);
            activity.layout03.setVisibility(View.GONE);
            inquiry = false;
            activity.tvInquiry.setBackground(getResources().getDrawable(R.mipmap.ic_seek02));
        } else {
            activity.layout02.setVisibility(View.VISIBLE);
            activity.layout03.setVisibility(View.VISIBLE);
            inquiry = true;
            activity.tvInquiry.setBackground(getResources().getDrawable(R.mipmap.ic_seek01));
        }
    }
    private void setRealView() {
        recyclerView.addItemDecoration(new DividerGridItemDecoration(getActivity()));

        adapter = new Type05Adapter(R.layout.adapter_type05);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                local= position;
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

}
