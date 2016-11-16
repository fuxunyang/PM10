package com.sky.pm.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.sky.pm.R;
import com.sky.pm.model.RateDate;
import com.sky.pm.utils.ScreenUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class LineChartView extends View {

    //网格线画笔
    private Paint gridPaint;
    //坐标轴文字画笔
    private Paint textPaint;
    //利率线画笔
    private Paint ratePaint;
    //当日利率的文字画笔
    private Paint textWithBGPaint;
    //当日利率文字背景
    private Paint textBGPaint;

    //网格有色背景画笔
    private Paint gridBgPaint;
    //频幕宽度
    private int screenWidth;
    /**
     * 离上方和左侧的距离为每格宽度的1/3。
     */
    private int marginLeftAndTop;

    private Drawable textbackground;//文字的背景

    public List<RateDate> mDatas;
    private int screenH;


    public LineChartView(Context context) {
        this(context, null);
    }

    public LineChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        screenH = ScreenUtils.getScreenH(context);

        TypedArray style = context.obtainStyledAttributes(attrs, R.styleable.LineChartView);

        int count = style.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = style.getIndex(i);
            switch (attr) {
                case R.styleable.LineChartView_today_background:
                    textbackground = style.getDrawable(attr);
                    break;
            }
        }
        initViews();
    }

    private void initViews() {

        mDatas = new ArrayList<>();

        screenWidth = ScreenUtils.getScreenW(getContext());
        /**
         * 每格宽度为屏幕的1/7,高度为宽度的一半; 离上方和左侧的距离为每格宽度的一半。
         */
        everyWidth = screenWidth / 7;
        everyHeigt = everyWidth / 2;

        marginLeftAndTop = everyWidth / 3;

        gridPaint = new Paint();
        gridPaint.setColor(Color.parseColor("#d1d1d1"));
        gridPaint.setStrokeWidth(1);

        textPaint = new Paint();
        textPaint.setColor(Color.parseColor("#999999"));
        textPaint.setTextSize(18);
        textPaint.setAntiAlias(true);

        ratePaint = new Paint();
        ratePaint.setColor(Color.parseColor("#FFff0000"));
        ratePaint.setAntiAlias(true);
        ratePaint.setStrokeWidth(10);
        ratePaint.setStyle(Paint.Style.STROKE);


        LinearGradient shader = new LinearGradient(0, 0, everyWidth * 6, 0,
                new int[]{Color.YELLOW, Color.RED}, null, Shader.TileMode.CLAMP);
        ratePaint.setShader(shader);

        textWithBGPaint = new Paint();
        textWithBGPaint.setColor(Color.parseColor("#ffffff"));
        textWithBGPaint.setTextSize(22);
        textWithBGPaint.setAntiAlias(true);

        textBGPaint = new Paint();
        textBGPaint.setColor(Color.parseColor("#FFFD7201"));
        textBGPaint.setTextSize(18);
        textBGPaint.setAntiAlias(true);

        gridBgPaint = new Paint();
        gridBgPaint.setColor(Color.parseColor("#fffdf8"));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(getMeasuredWidth(), screenH / 3);
    }

    private int everyWidth;// 每格宽度
    private int everyHeigt;// 每格高度

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        setBackGroud(canvas);

        /**
         * 横线
         */
        for (int i = 0; i < 7; i++) {
            if (i != 6) {
                canvas.drawLine(marginLeftAndTop + everyWidth * 2 / 3,
                        marginLeftAndTop + i * everyHeigt, screenWidth,
                        marginLeftAndTop + i * everyHeigt, gridPaint);
            } else {
                canvas.drawLine(marginLeftAndTop, i * everyHeigt
                        + marginLeftAndTop, screenWidth, marginLeftAndTop + i
                        * everyHeigt, gridPaint);
            }

            /**
             * 竖线
             */
            canvas.drawLine(marginLeftAndTop + i * everyWidth,
                    marginLeftAndTop / 2, marginLeftAndTop + i * everyWidth,
                    marginLeftAndTop + 6 * everyHeigt, gridPaint);

            /**
             * 文字居中
             */
            if (mDatas != null && mDatas.size() == 7) {
                Rect textRect = new Rect();
                textPaint.getTextBounds(mDatas.get(i).getDate(), 0,
                        mDatas.get(i).getDate().length(), textRect);
                int textWidth = textRect.width();
                canvas.drawText(mDatas.get(i).getDate(), marginLeftAndTop + i
                        * everyWidth - textWidth / 2, 7 * everyHeigt - everyHeigt
                        / 3 + marginLeftAndTop, textPaint);
            }

        }


        if (mDatas.size() < 7 || mDatas == null) {

        } else {
            /**
             * 开始解决y轴上的值 首先得到七日化利率最大和最小值。
             *
             */
            float maxRate = mDatas.get(0).getRate();
            float minRate = mDatas.get(0).getRate();

            for (int i = 0; i < mDatas.size(); i++) {
                if (mDatas.get(i).getRate() > maxRate) {
                    maxRate = mDatas.get(i).getRate();
                }
                if (mDatas.get(i).getRate() < minRate) {
                    minRate = mDatas.get(i).getRate();
                }
            }

            //刻度，间距（y2-y1的值）
            float scale = 0.015f;
            //规定年率的范围在4个格之内，
            int number = (int) ((maxRate - minRate) / (scale * 4));
            if (number > 0) {
                scale = scale * (number + 1);
            }
            int num = (int) (minRate / scale);
            float minY = num * scale;
            /**
             * 每一个间距的值
             */
            float everyValue = scale;
            DecimalFormat df = new DecimalFormat("###.000");

            Path path = new Path();
            for (int i = 0; i < 7; i++) {
                /**
                 * 画文字Y轴上
                 */
                Rect textRect2 = new Rect();
                textPaint.getTextBounds(df.format(minY + i * scale) + "",
                        0, (df.format(minY + i * scale) + "").length(),
                        textRect2);
                int textHeight2 = textRect2.height();
                if (i != 6) {
                    canvas.drawText(df.format(minY + i * scale) + "",
                            marginLeftAndTop + everyHeigt / 3, marginLeftAndTop + (5 - i)
                                    * everyHeigt + textHeight2 / 2, textPaint);
                }


                float y1 = (marginLeftAndTop + 5 * everyHeigt - ((mDatas.get(i).getRate() - minY) / everyValue * everyHeigt));
                float x1 = marginLeftAndTop + (i) * everyWidth;
                if (i == 0) {
                    path.moveTo(x1, y1);

                } else {
                    path.lineTo(x1, y1);
                }
                canvas.drawPath(path, ratePaint);
                if (i == 6) {
                    float y2 = (marginLeftAndTop + 5 * everyHeigt - ((mDatas.get(i).getRate() - minY) / everyValue * everyHeigt));
                    /**
                     * 画文字背景图
                     */
                    Rect textRect = new Rect();
                    textWithBGPaint.getTextBounds(
                            df.format(mDatas.get(6).getRate()) + "", 0,
                            (df.format(mDatas.get(6).getRate()) + "").length(),
                            textRect);
                    int textHeight = textRect.height();
                    int textWidth = textRect.width();

                    Rect oval3 = new Rect(marginLeftAndTop + i * everyWidth
                            - textWidth / 2 - textWidth / 3, (int) y2
                            - textHeight * 2 - textHeight / 2, marginLeftAndTop + i * everyWidth
                            - textWidth / 2 + textWidth + textWidth / 3,
                            (int) (y2 - textHeight / 2));// 设置个新的长方形

                    //  canvas.drawRoundRect(oval3, 10, 10, textBGPaint);
                    textbackground.setBounds(oval3);//为文字设置背景
                    textbackground.draw(canvas);//画入画布中

                    /**
                     * 画文字。
                     */
                    canvas.drawText(df.format(mDatas.get(i).getRate()) + "",
                            marginLeftAndTop + i * everyWidth - textWidth / 2,
                            y2 - textHeight,
                            textWithBGPaint);


                    float y3 = (marginLeftAndTop + 5 * everyHeigt - ((mDatas.get(i).getRate() - minY) / everyValue * everyHeigt));
                    /**
                     * 画外层的小圆点。
                     */
                    canvas.drawCircle(marginLeftAndTop + i * everyWidth, y3, 12, textBGPaint);
                    /**
                     * 内层
                     */
                    canvas.drawCircle(marginLeftAndTop + i * everyWidth, y3, 6, textWithBGPaint);
                }


            }


        }

    }

    /**
     * 为填充数据预留
     *
     * @param mDatas
     */
    public void fillDateForRateDate(List<RateDate> mDatas) {
        this.mDatas = mDatas;
        invalidate();
    }


    public void setBackGroud(Canvas canvas) {
        /**
         * 画网格当中的淡色背景。
         */

        for (int i = 1; i < 7; i++) {

            Rect rect = new Rect();
            //
            rect.left = marginLeftAndTop + 1 * everyWidth;

            rect.right = marginLeftAndTop + 2 * everyWidth;

            rect.top = marginLeftAndTop / 2 + (i - 1) * everyHeigt;

            rect.bottom = marginLeftAndTop / 2 + (i) * everyHeigt;

            //第一列
            canvas.drawRect(rect, gridBgPaint);

            //第二列
            rect.left = marginLeftAndTop + 3 * everyWidth;
            rect.right = marginLeftAndTop + 4 * everyWidth;
            canvas.drawRect(rect, gridBgPaint);

            //第三列
            rect.left = marginLeftAndTop + 5 * everyWidth;
            rect.right = marginLeftAndTop + 6 * everyWidth;
            canvas.drawRect(rect, gridBgPaint);

        }
    }

}
