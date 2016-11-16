package com.sky.pm.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.sky.api.IBase;
import com.sky.pm.R;
import com.sky.pm.common.Constants;
import com.sky.pm.ui.dialog.DialogManager;
import com.sky.utils.NetworkJudgment;
import com.sky.utils.SPUtils;
import com.sky.utils.ToastUtils;

import org.xutils.x;

/**
 * @author LiBin
 * @ClassName: BaseActivity
 * @Description: 基类activity
 * @date 2015年3月26日 下午4:01:00
 */
public class BaseActivity extends AppCompatActivity implements IBase, Toolbar.OnMenuItemClickListener {
    public BaseTitle toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        if (!hasInternetConnected())
            showToast(getResources().getString(R.string.toast_isinternet));
        toolBar = new BaseTitle(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void showToast(String text) {
        ToastUtils.showShort(this, text);
    }//初始化toast提示

    public void setToolbar() {
        toolBar.setToolbar();
    }

    public void setLeftButton(int left) {
        toolBar.setLeftButton(left);
    }

    public void setToolbar(String text) {
        toolBar.setToolbar(text);
    }

    public void setToolBarClick() {
        toolBar.getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leftOnClick();
            }
        });
    }

    public void leftOnClick() {

    }

    /**
     * 右侧menu的点击事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }


    /**
     * 检查是否登录,未登录跳转到登录页
     *
     * @return
     */
    public boolean checkLogin() {
        if (!getUserOnlineState()) {
            isLogin(4);
            return true;
        }
        return false;
    }

    public void isLogin(int id) {
    }

    @Override
    public void showLoading() {
        DialogManager.showDialog(this);
    }

    @Override
    public void hideLoading() {
        DialogManager.disDialog();
    }


    /**
     * 判断是否有网络连接,没有返回false
     */
    @Override
    public boolean hasInternetConnected() {
        return NetworkJudgment.isConnected(this);
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initData() {

    }

    @Override
    public String getUserName() {
        return getObject(Constants.USERNAME, "");
    }

    public String getUserId() {
        return getObject(Constants.USERID, "");
    }

    @Override
    public String getPhone() {
        return null;
    }

    public String getToken() {
        return getObject(Constants.TOKEN, "");
    }

    public String getTelphone() {
        return getObject(Constants.TELPHONE, "");
    }

    /**
     * 获取用户在线状态
     */
    @Override
    public boolean getUserOnlineState() {
        return getObject(Constants.ISONLINE, false);
    }

    /**
     * 设置用户在线状态
     */
    @Override
    public void setUserOnlineState(boolean isOnline) {
        SPUtils.put(this, Constants.ISONLINE, true);
    }

    public <T extends Object> T getObject(String text, T a) {
        return (T) SPUtils.get(this, text, a);
    }

    public <T extends Object> void setObject(String text, T a) {
        SPUtils.put(this, text, a);
    }
}