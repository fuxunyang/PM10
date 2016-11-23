package com.sky.pm.ui.fragment;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.sky.pm.R;
import com.sky.pm.api.IDataResultImpl;
import com.sky.pm.common.Constants;
import com.sky.pm.model.User;
import com.sky.pm.ui.BaseFragment;
import com.sky.pm.utils.HttpDataUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created on 2015/11/28 0027.
 * Author wangpengpeng
 * Email   1678173987@qq.com
 * Description
 */
@ContentView(R.layout.fragment_my)
public class MyFragment extends BaseFragment {

    @ViewInject(R.id.img_man)
    private ImageView imgMan;
    @ViewInject(R.id.img_sure)
    private ImageView imgSure;
    @ViewInject(R.id.img_woman)
    private ImageView imgWoman;
    @ViewInject(R.id.img_sure1)
    private ImageView imgSure1;

    @ViewInject(R.id.et_nick)
    private EditText etNick;
    @ViewInject(R.id.et_phone)
    private EditText etPhone;
    @ViewInject(R.id.et_pass)
    private EditText etPass;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        HttpDataUtils.APPUsersGetIListByJson(getTelphone(), getUserName(), new IDataResultImpl<List<User>>() {
            @Override
            public void onSuccessData(List<User> data) {
                if (data == null) return;
                etNick.setText(data.get(0).getNickName());
                etPhone.setText(data.get(0).getPhone());
                etPass.setText(data.get(0).getPassword());

            }
        });
    }

    @Event(R.id.bt_zhuxiao)
    private void onClick(View v) {
        setObject(Constants.ISONLINE, false);
        activity.resetOtherTabs();
        activity.toolBar.getToolbar().setVisibility(View.GONE);
        activity.changeFragment(getResources().getString(R.string.tab_main), activity.first, new MainFragment());
    }
}