package com.sky.pm.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sky.adapter.RecyclerAdapter;
import com.sky.pm.R;
import com.sky.pm.api.IDataResultImpl;
import com.sky.pm.model.Latest;
import com.sky.pm.model.PieModel;
import com.sky.pm.ui.BaseFragment;
import com.sky.pm.ui.adapter.Type01Adapter;
import com.sky.pm.ui.adapter.TypeRealAdapter;
import com.sky.pm.ui.widget.PieChartView;
import com.sky.pm.utils.HttpDataUtils;
import com.sky.pm.utils.itemdecoration.DividerGridItemDecoration;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 李彬 on 2016/11/12.
 */
@ContentView(R.layout.fragment_type)
public class Type01Fragment extends BaseFragment {
    @ViewInject(R.id.layout_pie)
    private LinearLayout layoutPie;
    @ViewInject(R.id.layout_real)
    private LinearLayout layoutReal;
    @ViewInject(R.id.pie_chart)
    private PieChartView pieChartView;


    @ViewInject(R.id.bt_pie)
    private Button btPie;
    @ViewInject(R.id.bt_real)
    private Button btReal;

    @ViewInject(R.id.tv_total)
    private TextView tvTotal;


    @ViewInject(R.id.recycle)
    private RecyclerView recyclerView;
    @ViewInject(R.id.recycle1)
    private RecyclerView recyclerView1;
    private Type01Adapter adapter;
    private TypeRealAdapter realAdapter;
    //瀑布流布局
    private GridLayoutManager layoutManager;
    //    private Integer[] drawableIds = {R.color.pie1, R.color.pie3, R.color.pie2, R.color.pie4, R.color.pie5, R.color.pie6};
    private Integer[] drawableIds = {R.drawable.pie_bg, R.drawable.pie3_bg, R.drawable.pie2_bg,
            R.drawable.pie4_bg, R.drawable.pie5_bg, R.drawable.pie6_bg};
    private String[] texts = {"优[00-50]", "轻度污染[101-150]", "良[51-100]", "中度污染[151-200]", "重度污染[201-300]", "严重污染[>=301]"};
    private List<PieModel> pies;
    private List<Latest> list;
    private int you = 0;
    private int liang = 0;
    private int qing = 0;
    private int zhong = 0;
    private int zhongdu = 0;
    private int yanzhong = 0;
    private int local = 0;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    for (int i = 0; i < list.size(); i++) {
                        int value = list.get(i).getAQILevel();
                        if (value == 1) {
                            you++;
                        } else if (value == 2) {
                            liang++;
                        } else if (value == 3) {
                            qing++;
                        } else if (value == 4) {
                            zhong++;
                        } else if (value == 5) {
                            zhongdu++;
                        } else if (value >= 6) {
                            yanzhong++;
                        }
                    }
                    setPie();
                    //全市共建PM10监测站点xx个， 其中监测数值为优良的XX个， 监测数值为良的XX个， 监测数值为轻度污染的XX个， 监测数值为中度污染的XX个
                    tvTotal.setText("全市共建PM10监测站点" + list.size() + "个，" +
                            "其中监测数值为优良的" + you + "个，" +
                            "监测数值为良的" + liang + "个，" +
                            "监测数值为轻度污染的" + qing + "个，" +
                            "监测数值为中度污染的" + zhong + "个" +
                            "监测数值为重度污染的" + zhongdu + "个" +
                            "监测数值为严重污染的" + yanzhong + "个" +
                            "");

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
                Map01Fragment fragment = new Map01Fragment();
                fragment.setArguments(fragment.setBundle(Double.parseDouble(list.get(local).getLatitude()),
                        Double.parseDouble(list.get(local).getLongitude())));
                activity.changeFragment("厂区监测", fragment);
                activity.setRight("详情");
            }
        });
        initView();
        getData();
    }

    private void getData() {
        HttpDataUtils.DMS_T_DATA_LATESTGetAllListInfoByJson(new IDataResultImpl<List<Latest>>() {
            @Override
            public void onSuccessData(List<Latest> data) {
                list = data;
                LogUtil.d(data.size() + "");
                handler.sendEmptyMessage(1);
            }
        });
    }

    @Event({R.id.bt_pie, R.id.bt_real})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_pie:
                layoutPie.setVisibility(View.VISIBLE);
                layoutReal.setVisibility(View.GONE);
                btPie.setBackgroundResource(R.mipmap.ic_pie_pre);
                btReal.setBackgroundResource(R.mipmap.ic_real_nor);
                setPie();
                initView();
                break;
            case R.id.bt_real:
                getData();
                layoutPie.setVisibility(View.GONE);
                layoutReal.setVisibility(View.VISIBLE);
                btPie.setBackgroundResource(R.mipmap.ic_pie_nor);
                btReal.setBackgroundResource(R.mipmap.ic_real_pre);
                setRealView();
                break;
        }
    }

    private void setPie() {
        List items = new ArrayList<>();
        items.add(new PieChartView.PieItem("", you, getActivity().getResources().getColor(R.color.pie1)));
        items.add(new PieChartView.PieItem("", liang, getActivity().getResources().getColor(R.color.pie2)));
        items.add(new PieChartView.PieItem("", qing, getActivity().getResources().getColor(R.color.pie3)));
        items.add(new PieChartView.PieItem("", zhong, getActivity().getResources().getColor(R.color.pie4)));
        items.add(new PieChartView.PieItem("", zhongdu, getActivity().getResources().getColor(R.color.pie5)));
        items.add(new PieChartView.PieItem("", yanzhong, getActivity().getResources().getColor(R.color.pie6)));
        pieChartView.setPieItems(items);
    }


    private void initView() {
        pies = new ArrayList<>();
        for (int i = 0; i < drawableIds.length; i++) {
            pies.add(new PieModel(texts[i], drawableIds[i]));
        }
        recyclerView.setHasFixedSize(true);
        //瀑布流布局
        layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new Type01Adapter(R.layout.adapter_pie);
        recyclerView.setAdapter(adapter);
        adapter.setDatas(pies);

    }

    private void setRealView() {
        recyclerView1.setHasFixedSize(true);
        recyclerView1.addItemDecoration(new DividerGridItemDecoration(getActivity()));

        realAdapter = new TypeRealAdapter(R.layout.adapter_real);
        recyclerView1.setAdapter(realAdapter);
        realAdapter.setDatas(list);
        realAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                local = position;
            }
        });
    }
}
