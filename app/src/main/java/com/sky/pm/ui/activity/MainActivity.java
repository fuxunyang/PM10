package com.sky.pm.ui.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.View;

import com.baidu.mapapi.SDKInitializer;
import com.sky.pm.R;
import com.sky.pm.ui.BaseActivity;
import com.sky.pm.ui.fragment.BusinessFragment;
import com.sky.pm.ui.fragment.MainFragment;
import com.sky.pm.ui.fragment.MyFragment;
import com.sky.pm.ui.fragment.WeatherFragment;
import com.sky.pm.ui.widget.TabTextView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    //    @ViewInject(R.id.frame_manager)
//    private FrameLayout frameLayout;
    @ViewInject(R.id.id_main)
    private TabTextView first;
    @ViewInject(R.id.id_two)
    private TabTextView two;
    @ViewInject(R.id.id_three)
    private TabTextView three;
    @ViewInject(R.id.id_four)
    private TabTextView four;

    private List<TabTextView> tabTextViews;
    private Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setToolbar("首页");
        setLeftButton(-1);
        toolBar.getToolbar().setVisibility(View.GONE);
        setFragment();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        if (!getUserOnlineState()) {
//            resetOtherTabs();
//            setMenu(false);
//            toolBar.getToolbar().setVisibility(View.GONE);
//            changeFragment(getResources().getString(R.string.tab_main), first, new MainFragment());
//        }
    }

    private void setFragment() {
        tabTextViews = new ArrayList<>();
        tabTextViews.add(first);
        tabTextViews.add(two);
        tabTextViews.add(three);
        tabTextViews.add(four);
        first.setIconAlpha(1.0f);
        changeFragment(new MainFragment());
    }


    @Event({R.id.id_main, R.id.id_two, R.id.id_three, R.id.id_four})
    private void mainOnClick(View v) {
        toolBar.getToolbar().setVisibility(View.VISIBLE);
        resetOtherTabs();
        setMenu(false);

        switch (v.getId()) {
            case R.id.id_main:
                toolBar.getToolbar().setVisibility(View.GONE);
                changeFragment(getResources().getString(R.string.tab_main), first, new MainFragment());
                break;
            case R.id.id_two:
                changeFragment(getResources().getString(R.string.tab_two), two, new WeatherFragment());
                break;
            case R.id.id_three:
                changeFragment(getResources().getString(R.string.tab_three), three, new BusinessFragment());
                break;
            case R.id.id_four:
                changeFragment(getResources().getString(R.string.tab_my), four, new MyFragment());
//                setMenu(true);
                break;
        }
    }

    private void setMenu(boolean flag) {
        if (mMenu == null) return;
        mMenu.getItem(0).setVisible(flag);
    }

    private void resetOtherTabs() {
        for (int i = 0; i < tabTextViews.size(); i++) {
            tabTextViews.get(i).setIconAlpha(0f);
        }
    }

    public void changeFragment(String text, TabTextView tab, Fragment fragment) {
        toolBar.setAppTitle(text);
        tab.setIconAlpha(1.0f);
        changeFragment(fragment);
    }

    public void changeFragment(String text, Fragment fragment) {
        toolBar.getToolbar().setVisibility(View.VISIBLE);
        toolBar.setAppTitle(text);
//        setLeftButton(R.mipmap.ic_back);
        changeFragment(fragment);
    }

    public void setRight(String right) {
        toolBar.setright(right);
    }

    public void changeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_manager, fragment).commit();
    }


    private long lastBack;

    //判断按键 菜单的显示与隐藏
    @Override
    public void onBackPressed() {
        long nowCurrent = System.currentTimeMillis();
        if (nowCurrent - lastBack > 3000) {
            showToast(getResources().getString(R.string.toast_exit));
            lastBack = nowCurrent;
        } else {
            super.onBackPressed();
        }
    }

}