package com.sky.pm.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;

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
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.sky.pm.R;
import com.sky.pm.model.Latest;
import com.sky.pm.ui.BaseFragment;
import com.sky.pm.ui.activity.MainActivity;

import org.xutils.view.annotation.ContentView;
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
    protected MainActivity activity;

    // 初始化全局 bitmap 信息，不用时及时 recycle
    BitmapDescriptor bdA = BitmapDescriptorFactory
            .fromResource(R.drawable.pie5);
    BitmapDescriptor bdB = BitmapDescriptorFactory
            .fromResource(R.drawable.pie6);
    BitmapDescriptor bdC = BitmapDescriptorFactory
            .fromResource(R.drawable.pie7);
    BitmapDescriptor bdD = BitmapDescriptorFactory
            .fromResource(R.drawable.pie8);
    BitmapDescriptor bdE = BitmapDescriptorFactory
            .fromResource(R.drawable.pie9);
    BitmapDescriptor bdF = BitmapDescriptorFactory
            .fromResource(R.drawable.pie10);

    // 定位相关
    LocationClient mLocClient;
    protected MyLocationConfiguration.LocationMode mCurrentMode;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            setLocation();
            switch (msg.what) {
                case 1:
                    for (int i = 0; i < list.size(); i++) {
                        double value = Double.parseDouble(list.get(i).getDataValue());
                        double lat = Double.parseDouble(list.get(i).getLatitude());
                        double lng = Double.parseDouble(list.get(i).getLongitude());
                        LatLng ll = new LatLng(lat, lng);
                        if (value >= 0 && value <= 50) {
                            mBaiduMap.addOverlay(new MarkerOptions().position(ll).icon(bdA).zIndex(5));
                            setText(i, lat, lng, R.color.pie1);
                        } else if (value >= 51 && value <= 100) {
                            mBaiduMap.addOverlay(new MarkerOptions().position(ll).icon(bdB).zIndex(5));
                            setText(i, lat, lng, R.color.pie2);
                        } else if (value >= 101 && value <= 150) {
                            mBaiduMap.addOverlay(new MarkerOptions().position(ll).icon(bdC).zIndex(5));
                            setText(i, lat, lng, R.color.pie3);
                        } else if (value >= 151 && value <= 200) {
                            mBaiduMap.addOverlay(new MarkerOptions().position(ll).icon(bdD).zIndex(5));
                            setText(i, lat, lng, R.color.pie4);
                        } else if (value >= 201 && value <= 400) {
                            mBaiduMap.addOverlay(new MarkerOptions().position(ll).icon(bdE).zIndex(5));
                            // 添加文字
                            setText(i, lat, lng, R.color.pie4);
                        } else if (value >= 401) {
                            mBaiduMap.addOverlay(new MarkerOptions().position(ll).icon(bdF).zIndex(5));
                            // 添加文字
                            setText(i, lat, lng, R.color.pie4);
                        }
                    }
                    break;
            }
            setHandler(msg);
        }

        private void setText(int i, double lat, double lng, int color) {
            // 添加文字
            LatLng llText = new LatLng(lat - 0.001, lng);
            OverlayOptions name = new TextOptions().bgColor(0xAAFFFF00)
                    .fontSize(36).fontColor(getResources().getColor(color)).text(list.get(i).getStationName())
                    .position(llText);
            mBaiduMap.addOverlay(name);

            LatLng llValue = new LatLng(lat + 0.005, lng + 0.01);
            OverlayOptions dateValue = new TextOptions().bgColor(0xAAFFFF00)
                    .fontSize(36).fontColor(getResources().getColor(color)).text(list.get(i).getDataValue())
                    .position(llValue);
            mBaiduMap.addOverlay(dateValue);
        }
    };

    public void setHandler(Message msg) {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (MainActivity) activity;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBaiduMap = mMapView.getMap();
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(14.0f);
        mBaiduMap.setMapStatus(msu);
//        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);

        getData();
        setRight();
    }

    public abstract void setRight();

    public abstract void getData();

    public void setLocation() {
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
                        .direction(100).latitude(Double.parseDouble(list.get(0).getLatitude()))
                        .longitude(Double.parseDouble(list.get(0).getLongitude())).build();
                mBaiduMap.setMyLocationData(locData);
                mLocClient.stop();
//                if (isFirstLoc) {
//                    isFirstLoc = false;
//                    LatLng ll = new LatLng(location.getLatitude(),
//                            location.getLongitude());
//                    MapStatus.Builder builder = new MapStatus.Builder();
//                    builder.target(ll).zoom(18.0f);
//                    mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
//                }
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
        // 回收 bitmap 资源
        bdA.recycle();
        bdB.recycle();
        bdC.recycle();
        bdD.recycle();
    }

}