package com.sky.pm.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by 李彬 on 2016/11/23.
 */

public class Pm extends BmobObject {
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
