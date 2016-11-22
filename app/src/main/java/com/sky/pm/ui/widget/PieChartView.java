package com.sky.pm.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.sky.pm.utils.ScreenUtils;

import java.util.List;

/**
 * @author sky QQ:1136096189
 * @Description: 总资产饼图
 * @date 15/11/30 下午6:18
 */
public class PieChartView extends View {
    private int screenW, screenH;

    private Paint piePaint;//饼图画笔
    private Paint inPiePaint;//内圆
    private Paint linePaint;//间隔线
    private int lineSweep=0;
    private Paint valuePain;

    private int pieCenterX;//圆心X
    private int pieCenterY;//圆心Y
    private int pieRadius;
    private RectF pieOval;//圆所在矩形

    //总资产各项的画笔颜色
//    private int[] mPieColors = new int[]{Color.parseColor("#E993DC"), Color.parseColor("#d9e766"), Color.parseColor("#6bcce4"), Color.parseColor("#fab255"), Color.parseColor("#a193e9")};
    private List<PieItem> mPieItems;//饼图所需要的信息
    private float totalValue;//总资产

    private boolean once = true;//保证onmeasure只执行一次

    public PieChartView(Context context) {
        this(context, null);
    }

    public PieChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PieChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        //init screen
        screenW = ScreenUtils.getScreenW(context);
        screenH = ScreenUtils.getScreenH(context);

        piePaint = new Paint();
        piePaint.setAntiAlias(true);
        piePaint.setStyle(Paint.Style.FILL);

        inPiePaint = new Paint();
        inPiePaint.setAntiAlias(true);
        inPiePaint.setStyle(Paint.Style.FILL);
        inPiePaint.setColor(Color.WHITE);


        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setStrokeWidth(2);
//        linePaint.setStrokeWidth(ScreenUtils.dp2px(context, 1));
        linePaint.setColor(Color.WHITE);

    }


    public void setPieItems(List<PieItem> pieItems) {
        this.mPieItems = pieItems;

        totalValue = 0;
        for (PieItem item : mPieItems) {
            totalValue += item.getItemValue();
        }
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = screenH/3;
        if (once && width > height) {
            pieCenterX = width / 2;
            pieCenterY = height / 2;
            pieRadius = (height - 20) / 2;
            pieOval = new RectF();
            pieOval.left = pieCenterX - pieRadius;
            pieOval.top = pieCenterY - pieRadius;
            pieOval.right = pieCenterX + pieRadius;
            pieOval.bottom = pieCenterY + pieRadius;
        }
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mPieItems == null) {
            return;
        }
        if (totalValue == 0 || mPieItems.size() == 0) {
            piePaint.setColor(Color.parseColor("#f3f3f3"));
            canvas.drawCircle(pieCenterX, pieCenterY, pieRadius, piePaint);
            canvas.drawCircle(pieCenterX, pieCenterY, pieRadius / 3, inPiePaint);
            return;
        }

        float start = 0f;
        int count = mPieItems.size();
        int totalSweep = 360-count*lineSweep;
        for (int i = 0; i <count ; i++) {
            //draw pie
            piePaint.setColor(mPieItems.get(i).getColor());
            float sweep = mPieItems.get(i).getItemValue() / totalValue * totalSweep;//计算所占角度

            canvas.drawArc(pieOval, start, sweep, true, piePaint);
            start += sweep;
//            double value = mPieItems.get(i).getItemValue() / totalValue*100;//计算百分比


//            canvas.drawArc(pieOval, start, lineSweep, true, linePaint);
//            start += lineSweep;

        }

//        inPiePaint.setColor(Color.WHITE);
//        canvas.drawCircle(pieCenterX, pieCenterY, pieRadius / 3, inPiePaint);


    }

    public static class PieItem {
        private String itemType;//名称
        private float itemValue;//数据
        private int color;//颜色

        public PieItem(String itemType, float itemValue, int color) {
            this.itemType = itemType;
            this.itemValue = itemValue;
            this.color = color;
        }

        public String getItemType() {
            return itemType;
        }

        public void setItemType(String itemType) {
            this.itemType = itemType;
        }

        public float getItemValue() {
            return itemValue;
        }

        public void setItemValue(float itemValue) {
            this.itemValue = itemValue;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }
    }
}
