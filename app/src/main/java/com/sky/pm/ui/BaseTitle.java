package com.sky.pm.ui;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.sky.pm.R;

/**
 * Created by 李彬 on 2016/9/21.
 */

public class BaseTitle {
    private AppCompatActivity activity;
    //toolbar
    private Toolbar toolbar;
    private TextView tvTitle;
    private TextView tvRight;

    public BaseTitle(AppCompatActivity activity) {
        this.activity = activity;
    }

    /**
     * 配合XML文件，设置toolbar
     * 在每个需要标题的XML中引用
     * <include layout="@layout/activity_title"/>
     */
    public void setToolbar() {
        try {
            ActivityInfo info = activity.getPackageManager().getActivityInfo(activity.getComponentName(), PackageManager.GET_META_DATA);
            String title = (String) info.nonLocalizedLabel;
            if (TextUtils.isEmpty(title))
                title = (String) info.loadLabel(activity.getPackageManager());
            if (TextUtils.isEmpty(title))
                title = activity.getResources().getString(info.labelRes);
            setToolbar(title + "");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setToolbar(String title) {
        toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        tvTitle = (TextView) toolbar.findViewById(R.id.tv_title);
        tvTitle.setText(title);
        tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTitleOnclick(v);
            }
        });
        toolbar.setTitle("");
        activity.setSupportActionBar(toolbar);

//        toolbar.setLogo(R.drawable.action_line_v);
        toolbar.setNavigationIcon(R.mipmap.ic_back);
        toolbar.setOnMenuItemClickListener((Toolbar.OnMenuItemClickListener) activity);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leftOnClick();
            }
        });
    }

    /**
     * title的点击事件
     *
     * @param v
     */
    public void setTitleOnclick(View v) {
    }

    /**
     * 左侧title
     *
     * @param title
     */
    public void setright(String title) {
        tvRight = (TextView) toolbar.findViewById(R.id.tv_right);
        tvRight.setText(title);
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leftTitleOnclick(v);
            }
        });
    }

    public TextView getTvRight() {
        return tvRight;
    }

    public void setTvRight(TextView tvRight) {
        this.tvRight = tvRight;
    }

    /**
     * 左侧title的点击事件
     *
     * @param v
     */
    public void leftTitleOnclick(View v) {
    }

    public TextView getTvTitle() {
        return tvTitle;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setAppTitle(String title) {
        tvTitle.setText(title);
    }

    /**
     * 更换左侧图标
     *
     * @param left 为－1时，隐藏图标
     */
    public void setLeftButton(int left) {
        if (left == -1) {
            toolbar.setLogo(null);
            toolbar.setNavigationIcon(null);
        } else {
//            toolbar.setLogo(R.drawable.action_line_v);
            toolbar.setNavigationIcon(left);
        }
    }

    /**
     * 左侧按钮的点击事件，默认关闭，如需重写，把继承的super删掉
     */
    public void leftOnClick() {
        activity.finish();
    }
}
