package com.sky.pm.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.sky.pm.R;
import com.sky.pm.model.Latest;
import com.sky.pm.ui.BaseFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by 李彬 on 2016/11/12.
 */
@ContentView(R.layout.fragment_map)
public abstract class BaseMapFragment extends BaseFragment {
    /**
     * MapView 是地图主控件
     */
    @ViewInject(R.id.bmapView)
    protected MapView mMapView;
    protected BaiduMap mBaiduMap;
    protected List<Latest> list;

    // 定位相关
    LocationClient mLocClient;
    protected MyLocationConfiguration.LocationMode mCurrentMode;
    private double latitude, longitude;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    for (int i = 0; i < list.size(); i++) {
                        int value =list.get(i).getAQILevel();
                        double lat = Double.parseDouble(list.get(i).getLatitude());
                        double lng = Double.parseDouble(list.get(i).getLongitude());
                        LatLng ll = new LatLng(lat, lng);
                        try {
                            Double dataValue = Double.parseDouble(list.get(i).getDataValue());
                        } catch (NullPointerException e) {
                            setMark(i, ll, R.drawable.pie12, R.color.black);
                            continue;
                        }
                        if (value==1) {
                            setMark(i, ll, R.drawable.pie5, R.color.pie1);
                        } else if (value==2) {
                            setMark(i, ll, R.drawable.pie6, R.color.pie2);
                        } else if (value==3) {
                            setMark(i, ll, R.drawable.pie7, R.color.pie3);
                        } else if (value==4) {
                            setMark(i, ll, R.drawable.pie8, R.color.pie4);
                        } else if (value==5) {
                            setMark(i, ll, R.drawable.pie9, R.color.pie5);
                        } else if (value >= 6) {
                            setMark(i, ll, R.drawable.pie10, R.color.pie6);
                        }
                    }
                    break;
            }
            setHandler(msg);
        }

    };

    private void setMark(int i, LatLng ll, int draw, int color) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_local, null);
        ImageView img = (ImageView) view.findViewById(R.id.img_local);
        TextView name = (TextView) view.findViewById(R.id.tv_name);
        TextView pm = (TextView) view.findViewById(R.id.tv_pm);
        img.setImageResource(draw);
        name.setTextColor(getResources().getColor(color));
        name.setText(list.get(i).getStationName());
        pm.setTextColor(getResources().getColor(color));
        pm.setText(list.get(i).getDataValue());
        mBaiduMap.addOverlay(new MarkerOptions().position(ll).icon(BitmapDescriptorFactory.fromView(view)).zIndex(5));
    }


    public void setHandler(Message msg) {

    }

    public Bundle setBundle(double lat, double lng) {
        Bundle bundle = new Bundle();
        bundle.putDouble("lat", lat);
        bundle.putDouble("lng", lng);
        return bundle;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setSeek();
        mBaiduMap = mMapView.getMap();
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(14.0f);
        mBaiduMap.setMapStatus(msu);
        getData();
        setRight();
        Bundle arguments = getArguments();
        latitude = arguments.getDouble("lat", 0);
        longitude = arguments.getDouble("lng", 0);
        setLocation(latitude, longitude);
    }

    public abstract void setRight();

    public void setSeek() {

    }

    public abstract void getData();

    private boolean checked = true;
    @ViewInject(R.id.imbt_change)
    private ImageButton change;

    @ViewInject(R.id.imbt_back)
    private ImageButton back;

    @Event({R.id.imbt_change, R.id.imbt_back})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.imbt_change:
                if (checked) {
                    mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                    checked = false;
                    change.setBackgroundResource(R.mipmap.btn_local_02);

                } else {
                    mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                    checked = true;
                    change.setBackgroundResource(R.mipmap.btn_local_01);
                }
                break;
            case R.id.imbt_back:
                setLocation(latitude, longitude);
                MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(10.0f);
                mBaiduMap.setMapStatus(msu);
//                showToast("当前缩放："+mBaiduMap.getMapStatus().zoom);
                break;
        }
    }

    public void setLocation(final double lat, final double lng) {
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        mCurrentMode = MyLocationConfiguration.LocationMode.FOLLOWING;
        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
                .fromResource(R.drawable.ic_location);
        mBaiduMap
                .setMyLocationConfigeration(new MyLocationConfiguration(
                        mCurrentMode, true, mCurrentMarker));
        // 定位初始化
        mLocClient = new LocationClient(getActivity());
        mLocClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation location) {
                // map view 销毁后不在处理新接收的位置
                if (location == null || mMapView == null) {
                    return;
                }
                MyLocationData locData = new MyLocationData.Builder()
                        .accuracy(location.getRadius())
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(100).latitude(lat)
                        .longitude(lng).build();
                mBaiduMap.setMyLocationData(locData);
                mLocClient.stop();
            }
        });
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
    }

    @Override
    public void onPause() {
        // MapView的生命周期与Activity同步，当activity挂起时需调用MapView.onPause()
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
        // MapView的生命周期与Activity同步，当activity恢复时需调用MapView.onResume()
        mMapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        // MapView的生命周期与Activity同步，当activity销毁时需调用MapView.destroy()
        mMapView.onDestroy();
        // 退出时销毁定位
//        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView = null;
        super.onDestroy();
    }

}