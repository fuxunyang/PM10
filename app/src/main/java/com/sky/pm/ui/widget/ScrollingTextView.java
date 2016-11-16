package com.sky.pm.ui.widget;

import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @author 彬 QQ 1136096189
 * @Description: TODO 滑动的textview
 * @date 2015/8/17 15:30
 */
public class ScrollingTextView extends TextView {

    public ScrollingTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setHorizontalFadingEdgeEnabled(true);//文字两边半隐藏效果
        setEllipsize(TextUtils.TruncateAt.MARQUEE);//在xml文件中用android:ellipsize="marquee"，上句话自动加载
        setMarqueeRepeatLimit(-1);//==android:marqueeRepeatLimit="marquee_forever"
        setSingleLine();
        // setFocusable(true);//==isfocused()
        // setFocusableInTouchMode(true);
    }

    public ScrollingTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollingTextView(Context context) {
        this(context, null);
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        if (focused) super.onFocusChanged(focused, direction, previouslyFocusedRect);
    }

    @Override
    public void onWindowFocusChanged(boolean focused) {
        if (focused) super.onWindowFocusChanged(focused);

    }

    @Override
    public boolean isFocused() {
        return true;
    }

}
