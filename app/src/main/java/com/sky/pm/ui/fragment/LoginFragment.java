package com.sky.pm.ui.fragment;

import android.view.View;
import android.widget.EditText;

import com.sky.pm.R;
import com.sky.pm.api.IDataResultImpl;
import com.sky.pm.common.Constants;
import com.sky.pm.ui.BaseFragment;
import com.sky.pm.utils.HttpDataUtils;
import com.sky.utils.TextUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by 李彬 on 2016/11/23.
 */
@ContentView(R.layout.fragment_login)
public class LoginFragment extends BaseFragment {
    @ViewInject(R.id.et_name)
    private EditText etName;
    @ViewInject(R.id.et_pass)
    private EditText etPass;

    @Event(R.id.bt_login)
    private void onClick(View v) {
        final String phone = etName.getText().toString().trim();
        if (TextUtil.notNull(phone, "手机号")) return;
        final String pass = etPass.getText().toString().trim();
        if (TextUtil.notNull(pass, "密码")) return;
        HttpDataUtils.APPUsersGetRecordCount(phone, pass, new IDataResultImpl<String>() {
            @Override
            public void onSuccessData(String data) {
                int num = 0;
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String strnum = (String) jsonObject.get("result");
                    num=Integer.parseInt(strnum);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (num <= 0) {
                    showToast("用户名或密码错误");
                    setObject(Constants.ISFIRST, false);
                } else {
                    setObject(Constants.TELPHONE, phone);
                    setObject(Constants.USERNAME, pass);
                    showToast("登陆成功");
                    setObject(Constants.ISONLINE, true);
                    activity.changeFragment(getResources().getString(R.string.tab_my), activity.four, new MyFragment());

                }
            }
        });
    }
}
