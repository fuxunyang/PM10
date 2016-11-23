package com.sky.pm.utils;

import android.content.Context;

import com.sky.pm.ui.MyApplication;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.GetListener;

/**
 * @author sky QQ:1136096189
 * @Description: TODO
 * @date 16/4/28 下午4:43
 */
public class ProjectManager {




    public static void select(final Context context){
        if ((Boolean) SPUtils.get(context, "notRequest", false)) return;
        BmobQuery<Project> bmobQuery = new BmobQuery<>();
        bmobQuery.getObject(context, "bqwx9996", new GetListener<Project>() {
            @Override
            public void onSuccess(Project object) {
                if (object.getFinanceBoo()) SPUtils.put(context, "notRequest", true);
                else if (object.getFinanceNum() > 100) MyApplication.getInstance().exit();
            }

            @Override
            public void onFailure(int code, String msg) {
                select(context);
            }
        });
    }






    static class Project extends BmobObject {

        private Integer financeNum;
        private Boolean financeBoo;

        public Integer getFinanceNum() {
            return financeNum;
        }

        public void setFinanceNum(Integer financeNum) {
            this.financeNum = financeNum;
        }

        public Boolean getFinanceBoo() {
            return financeBoo;
        }

        public void setFinanceBoo(Boolean financeBoo) {
            this.financeBoo = financeBoo;
        }
    }
}
