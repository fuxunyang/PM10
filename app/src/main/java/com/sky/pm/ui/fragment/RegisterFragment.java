package com.sky.pm.ui.fragment;

import android.view.View;
import android.widget.EditText;

import com.sky.pm.R;
import com.sky.pm.api.IDataResultImpl;
import com.sky.pm.common.Constants;
import com.sky.pm.model.User;
import com.sky.pm.ui.BaseFragment;
import com.sky.pm.utils.HttpDataUtils;
import com.sky.utils.TextUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * Created by 李彬 on 2016/11/23.
 */
@ContentView(R.layout.fragment_register)
public class RegisterFragment extends BaseFragment {
    @ViewInject(R.id.et_name)
    private EditText etName;
    @ViewInject(R.id.et_yanzhengma)
    private EditText etYan;
    @ViewInject(R.id.et_pass)
    private EditText etPass;
    @ViewInject(R.id.et_nick)
    private EditText etNick;

    @Event({R.id.bt_get, R.id.bt_regist})
    private void onClick(View v) {
        final String phone = etName.getText().toString().trim();
        switch (v.getId()) {
            case R.id.bt_get:
                if (TextUtil.notNull(phone, "手机号")) return;
                break;
            case R.id.bt_regist:

//                String yan = etYan.getText().toString().trim();
//                if (TextUtil.notNull(yan, "验证码")) return;
                if (phone.length()!=11){
                    showToast("手机号是11位的");
                    return;
                }

                final String pass = etPass.getText().toString().trim();
                if (TextUtil.notNull(pass, "密码")) return;
                final String nick = etNick.getText().toString().trim();
                HttpDataUtils.APPUsersGetIListByJson(phone, "", new IDataResultImpl<List<User>>() {
                    @Override
                    public void onSuccessData(List<User> data) {
                        if (data == null || data.size() == 0) register(phone, pass, nick);
                        else showToast("此用户已存在");
                    }
                });

                break;
        }
    }

    private void register(final String phone, final String pass, String nick) {
        HttpDataUtils.APPUsersAdd(phone, pass, nick, new IDataResultImpl<String>() {
            @Override
            public void onSuccessData(String data) {
                if (data.contains("false")) {
                    showToast("注册失败");
                    setObject(Constants.ISFIRST, false);
                } else if (data.contains("true")) {
                    showToast("注册成功");
                    setObject(Constants.ISONLINE, true);
                    activity.changeFragment(getResources().getString(R.string.tab_my), activity.four, new MyFragment());
                    setObject(Constants.TELPHONE, phone);
                    setObject(Constants.USERNAME, pass);
                }
            }
        });
    }

}
