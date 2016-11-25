package com.sky.pm.ui.fragment;

import android.view.View;

import com.sky.pm.api.IDataResultImpl;
import com.sky.pm.model.Latest;
import com.sky.pm.utils.HttpDataUtils;

import java.util.List;

/**
 * Created by 李彬 on 2016/11/12.
 */
public class Map03Fragment extends BaseMapFragment {
    public void setRight() {
        activity.toolBar.getTvRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.changeFragment("AQI指数", new Type03Fragment());
                activity.setRight("地图");
            }
        });
    }

    public void getData() {
        HttpDataUtils.DMS_T_DATA_LATESTGetAllListInfoByJson(new IDataResultImpl<List<Latest>>() {
            @Override
            public void onSuccessData(List<Latest> data) {
                list = data;
                handler.sendEmptyMessage(1);
            }
        });
    }
}
