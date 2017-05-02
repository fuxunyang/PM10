package com.sky.pm.ui.fragment;


import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sky.pm.R;
import com.sky.pm.ui.BaseFragment;
import com.sky.pm.ui.adapter.MainAdapter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.Arrays;
import java.util.List;

@ContentView(R.layout.fragment_main)
public class MainFragment extends BaseFragment {

    @ViewInject(R.id.recycle)
    private RecyclerView recyclerView;
    private MainAdapter adapter;
    //瀑布流布局
    private GridLayoutManager layoutManager;
    private Integer[] imgIds = {R.mipmap.btn_01, R.mipmap.btn_02, R.mipmap.btn_03,
            R.mipmap.btn_04, R.mipmap.btn_05, R.mipmap.btn_06};
//    private String[] texts = {"我的银行卡", "我关注的标", "我的借款", "投资记录",
//            "安全中心", "关于我们", "最新公告", "常见问题", "更多设置"};
    private List<Integer> myModels;



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    //

    private void initView() {
        myModels= Arrays.asList(imgIds);
        recyclerView.setHasFixedSize(true);
        //瀑布流布局
        layoutManager = new GridLayoutManager(getActivity(),2);
//        layoutManager = new StaggeredGridLayoutManager(2, GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(this,null,0,0));
//        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        adapter = new MainAdapter(R.layout.adapter_uri);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new MainAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == 0) {//厂区监测
                    activity.changeFragment("厂区监测",new Type01Fragment());
                    activity.setRight("地图");
                } else if (position == 1) {//站点监测
                    activity.changeFragment("站点监测",new Type02Fragment());
                    activity.setRight("地图");
                } else if (position == 2) {//AQI指数
                    activity.changeFragment("AQI指数",new Type03Fragment());
                    activity.setRight("地图");
                } else if (position == 3) {//模范网格
                    activity.changeFragment("模范网络",new Type04Fragment());
                    activity.setRight("");
                } else if (position == 4) {//设备管理
                    activity.changeFragment("设备管理",new Type05Fragment());
                    activity.setRight("地图");
                } else if (position == 5) {//历史数据
                    activity.changeFragment("历史数据",new Type06Fragment());
                    activity.setRight("");
                }
            }
        });
        adapter.setDatas(myModels);

    }

}
