package com.sky.pm.ui.fragment;

import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.sky.pm.R;
import com.sky.pm.api.IDataResultImpl;
import com.sky.pm.model.Latest;
import com.sky.pm.utils.HttpDataUtils;

import java.util.List;

/**
 * Created by 李彬 on 2016/11/12.
 */
public class Map05Fragment extends BaseMapFragment {
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
                    setMark05(i, ll,R.drawable.pie11,R.color.black);
                }
                break;
        }
    }
    private void setMark05(int i, LatLng ll,int draw,int color) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_local, null);
        ImageView img = (ImageView) view.findViewById(R.id.img_local);
        TextView name = (TextView) view.findViewById(R.id.tv_name);
        TextView pm = (TextView) view.findViewById(R.id.tv_pm);
        img.setImageResource(draw);
        name.setTextColor(getResources().getColor(color));
        name.setText(list.get(i).getStationName());
        pm.setTextColor(getResources().getColor(color));
        pm.setText(list.get(i).getStationmn());
        mBaiduMap.addOverlay(new MarkerOptions().position(ll).icon(BitmapDescriptorFactory.fromView(view)).zIndex(5));
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
            activity.tvInquiry.setBackground(getResources().getDrawable(R.mipmap.ic_seek02));
            inquiry = false;
        } else {
            activity.tvInquiry.setBackground(getResources().getDrawable(R.mipmap.ic_seek01));
            activity.layout02.setVisibility(View.VISIBLE);
            activity.layout03.setVisibility(View.VISIBLE);
            inquiry = true;
        }
    }
}