package com.sky.pm.model;

/**
 * Created by 李彬 on 2016/11/19.
 */
public class NewsModel {

    /**
     * Id : 1
     * Title : 关于转发山东省煤炭工业局《关于组织编报2017年山东省煤矿企业安全培训需求的通知》的紧急通知
     * NewContent : 关于转发山东省煤炭工业局
     * CreateTime : 2016-11-07T09:18:00
     * Remark : null
     */

    private int Id;
    private String Title;
    private String NewContent;
    private String CreateTime;
    private Object Remark;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getNewContent() {
        return NewContent;
    }

    public void setNewContent(String NewContent) {
        this.NewContent = NewContent;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public Object getRemark() {
        return Remark;
    }

    public void setRemark(Object Remark) {
        this.Remark = Remark;
    }
}
