package com.sky.pm.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sky.pm.common.Constants;
import com.sky.pm.ui.activity.MainActivity;
import com.sky.pm.ui.dialog.DialogManager;
import com.sky.pm.utils.SPUtils;
import com.sky.utils.ToastUtils;

import org.xutils.x;

/**
 * @author sky QQ:1136096189
 * @Description: Fragmentd的公共类
 * @date 15/12/20 上午11:35
 */
public class BaseFragment extends Fragment {
    public MainActivity activity;

    private boolean injected = false;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (MainActivity) activity;

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        injected = true;
        return x.view().inject(this, inflater, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity.setLayoutGone();
        if (!injected) {
            x.view().inject(this, this.getView());
        }
    }

    public void showToast(String text) {
        ToastUtils.showShort(getActivity(), text);
    }//初始化toast提示

    public String getUserName() {
        return getObject(Constants.USERNAME, "");
    }

    public String getUserId() {
        return getObject(Constants.USERID, "");
    }

    public String getToken() {
        return getObject(Constants.TOKEN, "");
    }

    public String getTelphone() {
        return getObject(Constants.TELPHONE, "");
    }

    /**
     * 检查是否登录,未登录跳转到登录页
     *
     * @return
     */
    public boolean jumpLogin() {
        if (!getUserOnlineState()) {
            return true;
        }
        return false;
    }

    public void showLoading() {
        DialogManager.showDialog(getActivity());
    }

    public void hideLoading() {
        DialogManager.disDialog();
    }


    /**
     * 获取用户在线状态
     */
    public boolean getUserOnlineState() {
        return getObject(Constants.ISONLINE, false);
    }
    public <T extends Object> T getObject(String text, T a) {
        return (T) SPUtils.get(getActivity(), text, a);
    }

    public <T extends Object> void setObject(String text, T a) {
        SPUtils.put(getActivity(), text, a);
    }
}
