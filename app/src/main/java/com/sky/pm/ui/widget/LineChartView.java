package com.sky.pm.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.sky.pm.R;
import com.sky.pm.model.Latest;
import com.sky.pm.utils.ScreenUtils;

import java.text.DecimalFormat;
import java.util.List;

public class LineChartView extends View {
    private Paint gridPaint;//网格线画笔
    private Paint textPaint;//坐标轴文字画笔
    private Paint paint10;//10cm画笔

    public List<Latest> rateDates;
    private int screenWidth;
    private int screenH;
    private int hourOrMin = 2;

    /**
     * 为填充数据预留
     *
     * @param rateDates
     */
    public void setRateDates(List<Latest> rateDates, int hourOrMin) {
        this.rateDates = rateDates;
        this.hourOrMin = hourOrMin;

        invalidate();
    }

    public void setRateDates(List<Latest> rateDates) {
        this.rateDates = rateDates;

        invalidate();
    }

    public LineChartView(Context context) {
        this(context, null);
    }

    public LineChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews() {
        screenWidth = ScreenUtils.getScreenW(getContext());
        screenH = ScreenUtils.getScreenH(getContext());

        //网格的画笔
        gridPaint = new Paint();
        gridPaint.setColor(Color.parseColor("#ffffff"));
        gridPaint.setStrokeWidth(3);

        textPaint = new Paint();
        textPaint.setColor(getResources().getColor(R.color.white));
        textPaint.setTextSize(getResources().getDimensionPixelSize(R.dimen.micro));
        textPaint.setAntiAlias(true);

        paint10 = new Paint();
        paint10.setColor(getResources().getColor(R.color.pie1));
        paint10.setAntiAlias(true);
        paint10.setStrokeWidth(10);
        paint10.setStyle(Paint.Style.STROKE);
    }

    private int width;//
    private int height;//
    private int everyWidth;// 每格宽度
    private int everyHeigt;// 每格高度


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = screenH / 6;
        setMeasuredDimension(width, height);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (rateDates == null) return;
        int line = 5;
        int maxTextWidth = 0;//Y轴刻度的最大宽度
        int value = 200;
        //刻度，间距（y2-y1的值）
        float scaleY = 50f;
        float maxValue = Math.max(Float.parseFloat(rateDates.get(0).getDataValue()),
                Float.parseFloat(rateDates.get(1).getDataValue()));
        float minValue = Math.min(Float.parseFloat(rateDates.get(0).getDataValue()),
                Float.parseFloat(rateDates.get(1).getDataValue()));
        for (int i = 2; i < line; i++) {
            maxValue = Math.max(maxValue,
                    Float.parseFloat(rateDates.get(i).getDataValue()));
            minValue = Math.min(minValue,
                    Float.parseFloat(rateDates.get(i).getDataValue()));
        }
        if (maxValue > value && maxValue < 400) {
            value = 400;
            scaleY = 100;
        }
        if (maxValue > value && maxValue < 600) {
            value = 600;
            scaleY = 150;
        }

        DecimalFormat df = new DecimalFormat("##0.0##");
        for (int i = 0; i < line; i++) {
            //画Y轴刻度
            Rect boundY = new Rect();
            String YY = (value - i * scaleY) + "";
            textPaint.getTextBounds(YY, 0, YY.length(), boundY);
            maxTextWidth = Math.max(boundY.width(), maxTextWidth);
        }
        maxTextWidth += 5;
        everyWidth = (width - maxTextWidth) / 12;
        everyHeigt = height / 5;
        Path path10 = new Path();
        int radius = 20;
        int hour = 0;

        for (int i = 0; i < 12; i++) {
            //竖线
            if (i == 0) {
                canvas.drawLine(i * everyWidth + maxTextWidth, radius,
                        i * everyWidth + maxTextWidth, radius + (line - 1) * everyHeigt,
                        gridPaint);
            }

            //X轴上的日期
            Rect textRect = new Rect();
            String xx = hour + "";
            textPaint.getTextBounds(xx, 0, xx.length(), textRect);
            int textWidth = textRect.width();
            canvas.drawText(xx,
                    i * everyWidth + maxTextWidth - textWidth / 2, //相对于竖线居中
                    radius + (line - 1) * everyHeigt + textRect.height() / 2 * 3,
                    textPaint);
            hour += hourOrMin;
        }
        for (int i = 0; i < line; i++) {
            //横线
            gridPaint.setColor(Color.parseColor("#ffffff"));
            canvas.drawLine(maxTextWidth, radius + i * everyHeigt,
                    width, radius + i * everyHeigt, gridPaint);
            if (i == 1) {
                gridPaint.setColor(getResources().getColor(R.color.pie3));
                canvas.drawLine(maxTextWidth, radius + i * everyHeigt,
                        width, radius + i * everyHeigt, gridPaint);
            }
            //画Y轴刻度
            Rect boundY = new Rect();
            String YY = ((int)(value - i * scaleY)) + "";

            textPaint.getTextBounds(YY, 0, YY.length(), boundY);
            int boundYH = boundY.height();
            canvas.drawText(YY, 0, radius + i * everyHeigt + boundYH / 2, textPaint);
        }
        for (int i = 0; i < rateDates.size(); i++) {
            Latest rate = rateDates.get(i);

            float y10 = 4 - Float.parseFloat(rate.getDataValue()) / scaleY;
            if (i == 0) {
                path10.moveTo(i * everyWidth + maxTextWidth, radius + y10 * everyHeigt);
            } else {
                path10.lineTo(i * everyWidth + maxTextWidth, radius + y10 * everyHeigt);
            }
        }
        //开始画出折线
        canvas.drawPath(path10, paint10);

    }


}
