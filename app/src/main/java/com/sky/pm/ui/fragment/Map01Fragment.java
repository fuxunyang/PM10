package com.sky.pm.ui.fragment;

import android.view.View;

import com.sky.pm.R;
import com.sky.pm.api.IDataResultImpl;
import com.sky.pm.model.Latest;
import com.sky.pm.utils.HttpDataUtils;

import org.xutils.view.annotation.ContentView;

import java.util.List;


/**
 * Created by 李彬 on 2016/11/12.
 */
@ContentView(R.layout.fragment_map)
public class Map01Fragment extends BaseMapFragment {
    public void setRight() {
        activity.toolBar.getTvRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.changeFragment("厂区监测", new Type01Fragment());
                activity.setRight("地图");
            }
        });
    }

    public void getData() {
        HttpDataUtils.DMS_T_DATA_LATESTGetAllListByJson(new IDataResultImpl<List<Latest>>() {
            @Override
            public void onSuccessData(List<Latest> data) {
                list = data;
                handler.sendEmptyMessage(1);
            }
        });
    }

}