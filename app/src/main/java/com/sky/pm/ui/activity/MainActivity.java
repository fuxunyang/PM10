package com.sky.pm.ui.activity;


import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.sky.pm.BuildConfig;
import com.sky.pm.R;
import com.sky.pm.api.IDataResultImpl;
import com.sky.pm.ui.BaseActivity;
import com.sky.pm.ui.fragment.BusinessFragment;
import com.sky.pm.ui.fragment.LoginFragment;
import com.sky.pm.ui.fragment.MainFragment;
import com.sky.pm.ui.fragment.MyFragment;
import com.sky.pm.ui.fragment.RegisterFragment;
import com.sky.pm.ui.fragment.WeatherFragment;
import com.sky.pm.ui.widget.TabTextView;
import com.sky.pm.utils.HttpDataUtils;
import com.sky.pm.utils.HttpUtilsBase;

import org.xutils.ex.HttpException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    //    @ViewInject(R.id.frame_manager)
//    private FrameLayout frameLayout;
    @ViewInject(R.id.id_main)
    public TabTextView first;
    @ViewInject(R.id.id_two)
    private TabTextView two;
    @ViewInject(R.id.id_three)
    private TabTextView three;
    @ViewInject(R.id.id_four)
    public TabTextView four;

    @ViewInject(R.id.layout_seek)
    public LinearLayout layoutSeek;
    @ViewInject(R.id.layout_01)
    public LinearLayout layout01;
    @ViewInject(R.id.layout_02)
    public LinearLayout layout02;
    @ViewInject(R.id.layout_03)
    public LinearLayout layout03;
    @ViewInject(R.id.bt_01)
    public Button bt01;
    @ViewInject(R.id.bt_02)
    public Button bt02;
    @ViewInject(R.id.bt_seek)
    public Button btSeek;
    @ViewInject(R.id.tv_01)
    public EditText tv01;
    @ViewInject(R.id.tv_02)
    public EditText tv02;
    @ViewInject(R.id.tv_inquiry)
    public TextView tvInquiry;


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
        checkUpdate();

    }

    public void setInquiryGone() {
        tvInquiry.setVisibility(View.GONE);
    }

    public void setLayoutGone() {
        tv01.setText("");
        tv02.setText("");
        tvInquiry.setBackground(getResources().getDrawable(R.mipmap.ic_seek02));
        tvInquiry.setVisibility(View.GONE);
        layout01.setVisibility(View.GONE);
        layout02.setVisibility(View.GONE);
        layout03.setVisibility(View.GONE);
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
                setRight("");

                break;
            case R.id.id_three:
                changeFragment(getResources().getString(R.string.tab_three), three, new BusinessFragment());
                setRight("");
                break;
            case R.id.id_four:
                setRight("");
                if (getUserOnlineState())
                    changeFragment(getResources().getString(R.string.tab_my), four, new MyFragment());
                else createDialog();
                break;
        }
    }

    private void createDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_login, null);// 得到加载view
        final Dialog dialog = new AlertDialog.Builder(this).create();
        view.findViewById(R.id.img_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.bt_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment("用户登录", new LoginFragment());
                dialog.dismiss();

            }
        });
        view.findViewById(R.id.bt_regist).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment("用户注册", new RegisterFragment());
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setContentView(view);// 设置布局
    }


    private void setMenu(boolean flag) {
        if (mMenu == null) return;
        mMenu.getItem(0).setVisible(flag);
    }

    public void resetOtherTabs() {
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

    public void checkUpdate() {
        HttpDataUtils.checkUpdate(new IDataResultImpl<Integer>() {
            @Override
            public void onSuccessData(Integer data) {
                PackageManager packageManager = getPackageManager();
                try {
                    PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
                    int code = packageInfo.versionCode;
                    if (data != null && code < data) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                                    PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                            } else {
                                downLoadApp();
                            }
                        } else {
                            downLoadApp();
                        }
                    }

                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            downLoadApp();
            showToast("允许使用");

        }

    }

    //获取app
    private void downLoadApp() {
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("正在下载");
        progressDialog.setMax(100);
//        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgress(0);
        progressDialog.show();

        final HttpUtilsBase.RequestHandler downLoad = HttpDataUtils.downLoad(
                Environment.getExternalStorageDirectory().getPath() + File.separator,
                new IDataResultImpl<File>() {
                    @Override
                    public void onStart() {
                        progressDialog.show();
                    }

                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {

                        int i = (int) (current * 1.0f / total * 100);
                        progressDialog.setProgress(i);
//                LogUtils.d("percent="+i);
//                    progressDialog.incrementProgressBy(i);
                    }

                    @Override
                    public void onSuccessData(File data) {
                        progressDialog.dismiss();
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        //判断是否是AndroidN以及更高的版本
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            Uri contentUri = FileProvider.getUriForFile(MainActivity.this,
                                    BuildConfig.APPLICATION_ID + ".fileProvider", data);
                            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
                        } else {
                            intent.setDataAndType(Uri.fromFile(data), "application/vnd.android.package-archive");
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        }
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(HttpException exception, int code) {
                        progressDialog.dismiss();
                        showToast("下载失败");
                    }

                    @Override
                    public void onFinish() {
                    }

                    @Override
                    public void onCancel() {
                        progressDialog.dismiss();
                    }
                });

        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                downLoad.cancel();
            }
        });
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