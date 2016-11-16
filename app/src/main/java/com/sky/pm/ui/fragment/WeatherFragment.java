package com.sky.pm.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sky.pm.R;
import com.sky.pm.api.IDataResultImpl;
import com.sky.pm.model.WeatherEntity;
import com.sky.pm.ui.BaseFragment;
import com.sky.pm.ui.adapter.Weather01Adapter;
import com.sky.pm.ui.adapter.WeatherAdapter;
import com.sky.pm.ui.widget.MyRecycleView;
import com.sky.pm.utils.HttpDataUtils;
import com.sky.pm.utils.itemdecoration.DividerGridItemDecoration;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by 李彬 on 2016/11/9.
 */
@ContentView(R.layout.fragment_weather)
public class WeatherFragment extends BaseFragment {

    @ViewInject(R.id.tv_city)
    private TextView city;
    @ViewInject(R.id.tv_temp)
    private TextView temp;
    @ViewInject(R.id.tv_txtd)
    private TextView txtd;
    @ViewInject(R.id.tv_wind)
    private TextView wind;
    @ViewInject(R.id.tv_wuranchengdu)
    private TextView wuran;

    @ViewInject(R.id.layout_linjin)
    private RelativeLayout linjin;

    @ViewInject(R.id.recycle)
    private MyRecycleView recycle;
    @ViewInject(R.id.recycle1)
    private MyRecycleView recycle1;
    private WeatherAdapter adapter;
    private Weather01Adapter adapter01;

    private WeatherEntity weather;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    city.setText(weather.getBasic().getCity());
                    temp.setText(weather.getNow().getTmp()+"℃");
                    txtd.setText(weather.getDaily_forecast().get(0).getCond().getTxt_d() + "\n"
                            + weather.getDaily_forecast().get(0).getTmp().getMin() + "℃~"
                            + weather.getDaily_forecast().get(0).getTmp().getMax()+"℃");
                    wind.setText(weather.getDaily_forecast().get(0).getWind().getSc());
                    wuran.setText(weather.getAqi().getCity().getQlty());
                    if (weather.getHourly_forecast().size() == 0 || weather.getHourly_forecast() == null)
                        linjin.setVisibility(View.GONE);
                    else{
                        adapter.setDatas(weather.getHourly_forecast());
                    }
                    adapter01.setDatas(weather.getDaily_forecast());

                    break;
                case 2:
                    break;

            }
        }
    };

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        getWeather();
    }

    private void initView() {
        recycle.setHasFixedSize(true);
        recycle.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recycle.addItemDecoration(new DividerGridItemDecoration(getActivity()));

        adapter = new WeatherAdapter(R.layout.adapter_linjin);
        recycle.setAdapter(adapter);

        recycle1.setHasFixedSize(true);
        recycle1.addItemDecoration(new DividerGridItemDecoration(getActivity()));

        adapter01 = new Weather01Adapter(R.layout.adapter_yizhou);
        recycle1.setAdapter(adapter01);

    }

    private void getWeather() {
        showLoading();
        HttpDataUtils.getWeather("济宁", new IDataResultImpl<WeatherEntity>() {
            @Override
            public void onSuccessData(WeatherEntity data) {
                hideLoading();
                if (data == null) {
                    showToast(getString(R.string.error_03));
                    return;
                }
                weather = data;
                handler.sendEmptyMessage(1);

            }
        });
    }

    /**
     * "date" : "周六 01月23日 (实时：-13℃)"
     *
     * @param date
     * @return
     */
    private String cutWeek(String date) {
        return date.substring(0, 2);
    }

    private String cutTemp(String date) {
        int start = date.lastIndexOf("：");
        int last = date.lastIndexOf("℃");
        return date.substring(start + 1, last + 1);
    }

}
