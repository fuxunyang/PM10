package com.sky.pm.model;

/**
 * Created by 李彬 on 2016/11/13.
 */
public class TypeModel {
    private String id;
    private String code;
    private String name;
    private String date;
    private String pm;
    private String area;//所属厂区

    public TypeModel(String id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }


    public TypeModel(String id, String name, String date, String pm) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.pm = pm;
    }

    public TypeModel() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPm() {
        return pm;
    }

    public void setPm(String pm) {
        this.pm = pm;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
