package com.sky.pm.ui.fragment;

import android.os.Message;
import android.view.View;

import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.sky.pm.R;
import com.sky.pm.api.IDataResultImpl;
import com.sky.pm.model.Latest;
import com.sky.pm.utils.HttpDataUtils;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;

import java.util.List;

/**
 * Created by 李彬 on 2016/11/12.
 */
@ContentView(R.layout.fragment_map05)
public class Map05Fragment extends BaseMapFragment {
    // 初始化全局 bitmap 信息，不用时及时 recycle
    BitmapDescriptor bdG = BitmapDescriptorFactory
            .fromResource(R.drawable.pie11);

    @Override
    public void setRight() {
        activity.toolBar.getTvRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.changeFragment("设备管理", new Type05Fragment());
                activity.setRight("地图");
            }
        });
    }

    public void getData() {
        HttpDataUtils.NMS_T_CFG_SITE_INFOGetIListByJson(new IDataResultImpl<List<Latest>>() {
            @Override
            public void onSuccessData(List<Latest> data) {
                list = data;
                LogUtil.d(data.size() + "");
                handler.sendEmptyMessage(12);
            }
        });
    }

    @Override
    public void setHandler(Message msg) {
        super.setHandler(msg);
        switch (msg.what) {
            case 12:
                for (int i = 0; i < list.size(); i++) {
                    double lat = Double.parseDouble(list.get(i).getLatitude());
                    double lng = Double.parseDouble(list.get(i).getLongitude());
                    LatLng ll = new LatLng(lat, lng);

                    mBaiduMap.addOverlay(new MarkerOptions().position(ll).icon(bdG)
                            .zIndex(9).draggable(true));
                }

                break;
        }
    }
}