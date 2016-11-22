package com.sky.pm.ui.fragment;

import android.view.View;

import com.sky.pm.api.IDataResultImpl;
import com.sky.pm.model.Latest;
import com.sky.pm.utils.HttpDataUtils;

import java.util.List;

/**
 * Created by 李彬 on 2016/11/12.
 */
public class Map02Fragment extends BaseMapFragment {

    @Override
    public void setRight() {
        activity.toolBar.getTvRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.changeFragment("站点监测", new Type02Fragment());
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