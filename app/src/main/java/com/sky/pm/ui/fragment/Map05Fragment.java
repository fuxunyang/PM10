package com.sky.pm.ui.fragment;

import android.os.Message;
import android.view.View;

import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.sky.pm.R;
import com.sky.pm.api.IDataResultImpl;
import com.sky.pm.model.Latest;
import com.sky.pm.utils.HttpDataUtils;

import org.xutils.common.util.LogUtil;

import java.util.List;

/**
 * Created by 李彬 on 2016/11/12.
 */
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
                activity.setRight("详情");
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
                    setText(i, lat, lng);

                }
                break;
        }
    }

    public void setText(int i, double lat, double lng) {
        // 添加文字
        LatLng llText = new LatLng(lat - 0.001, lng);
        OverlayOptions name = new TextOptions()
                .fontSize(36).fontColor(0xFF000000).text(list.get(i).getStationName())
                .position(llText);
        mBaiduMap.addOverlay(name);

        LatLng llValue = new LatLng(lat + 0.005, lng + 0.01);
        OverlayOptions dateValue = new TextOptions()
                .fontSize(36).fontColor(0xff000000).text(list.get(i).getStationmn())
                .position(llValue);
        mBaiduMap.addOverlay(dateValue);
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
                                setLocation(Double.parseDouble(data.get(0).getLatitude()),
                                        Double.parseDouble(data.get(0).getLongitude()));
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
        } else {
            activity.layout02.setVisibility(View.VISIBLE);
            activity.layout03.setVisibility(View.VISIBLE);
            inquiry = true;
        }
    }
}