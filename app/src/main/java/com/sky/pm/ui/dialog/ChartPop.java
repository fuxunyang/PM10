package com.sky.pm.ui.dialog;

import android.view.View;

import com.sky.pm.R;
import com.sky.pm.model.Latest;
import com.sky.pm.ui.widget.LineChartView;

/**
 * Created by 李彬 on 2016/11/16.
 */

public class ChartPop extends BasePop<Latest> {

    private LineChartView lineChart;

    public ChartPop(View contentView) {
        super(contentView);
    }

    public ChartPop(View contentView, int width, int height) {
        super(contentView, width, height);
    }

    public ChartPop(View contentView, int width, int height, boolean focusable) {
        super(contentView, width, height, focusable);
    }

    @Override
    protected void initView() {
        super.initView();
        lineChart = (LineChartView) view.findViewById(R.id.lineChart);
    }

    @Override
    protected void initDatas() {
        lineChart.setRateDates(popDatas);

    }
}
