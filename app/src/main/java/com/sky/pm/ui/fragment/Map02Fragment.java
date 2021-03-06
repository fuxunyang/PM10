package com.sky.pm.ui.fragment;

import android.view.View;

import com.sky.pm.R;
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
        HttpDataUtils.DMS_T_DATA_LATESTGetAllListInfoByJson(new IDataResultImpl<List<Latest>>() {
            @Override
            public void onSuccessData(List<Latest> data) {
                list = data;
                handler.sendEmptyMessage(1);
            }
        });
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
                HttpDataUtils.DMS_T_DATA_LATESTGetIListInfoByJson(station,name,
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
            activity.tvInquiry.setBackground(getResources().getDrawable(R.mipmap.ic_seek02));
        } else {
            activity.layout02.setVisibility(View.VISIBLE);
            activity.layout03.setVisibility(View.VISIBLE);
            inquiry = true;
            activity.tvInquiry.setBackground(getResources().getDrawable(R.mipmap.ic_seek01));
        }
    }
}