package com.sky.pm.ui;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;

import com.sky.pm.BuildConfig;
import com.sky.utils.ActivityLifecycle;
import com.sky.utils.ToastUtils;

import org.xutils.x;

/**
 * @author sky 1136096189
 * @ClassName: MyApplication
 * @date 2015年3月26日 下午4:01:00
 */
public class MyApplication extends Application {
    private static MyApplication instance;

    public static MyApplication getInstance() {
        return instance;
    }

    public String headIconPath = Environment.getExternalStorageDirectory().getPath() + "/banking/";

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);
        instance = this;
//        File headIconFile = new File(headIconPath);
//        if (!headIconFile.exists()) {
//            headIconFile.mkdirs();
//        }
//        MUFileUtil.makeDir(headIconPath);
        // 初始化自定义Activity管理器
        registerActivityLifecycleCallbacks(ActivityLifecycle.getInstance());

    }

    /**
     * 注销登录清除sp缓存
     */
    public void clearPref() {
    }

    /**
     * 获取版本号
     *
     * @return
     */
    public String getAppVersion() {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = getPackageManager();
            PackageInfo pi = pm.getPackageInfo(getPackageName(), 0);
            if (pi.versionName == null || pi.versionName.length() <= 0) {
                versionName = "1.0";
            } else {
                versionName = pi.versionName;
            }
        } catch (Exception e) {
            versionName = "1.0";
        }
        return versionName;
    }

    public void exit() {
        ActivityLifecycle.getInstance().popAllActivity();
    }

    public void showError(int code) {
        ToastUtils.showError(getApplicationContext(), code);
    }

    public void showToast(String text) {
        ToastUtils.showShort(getApplicationContext(), text);
    }

}
