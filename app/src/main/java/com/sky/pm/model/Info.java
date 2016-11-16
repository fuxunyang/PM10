package com.sky.pm.model;

/**
 * Created by 李彬 on 2016/11/15.
 */

public class Info {

    /**
     * Id : 21667982-56ED-42CA-A1C5-F5291CCE2D44
     * Stationmn : HC601000000002
     * AreaId : 2
     * Password : 123456
     * StationName : 阳城煤矿
     * StationTypeId : H6型
     * Longitude : 116.32
     * Latitude : 35.76777778
     * Remark : null
     * RemoveFlag : null
     * Name : 济宁市
     * ParentId : 1
     * ParentName : 山东省
     */

    private String Id;
    private String Stationmn;
    private int AreaId;
    private String Password;
    private String StationName;
    private String StationTypeId;
    private String Longitude;
    private String Latitude;
    private Object Remark;
    private Object RemoveFlag;
    private String Name;
    private int ParentId;
    private String ParentName;

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getStationmn() {
        return Stationmn;
    }

    public void setStationmn(String Stationmn) {
        this.Stationmn = Stationmn;
    }

    public int getAreaId() {
        return AreaId;
    }

    public void setAreaId(int AreaId) {
        this.AreaId = AreaId;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getStationName() {
        return StationName;
    }

    public void setStationName(String StationName) {
        this.StationName = StationName;
    }

    public String getStationTypeId() {
        return StationTypeId;
    }

    public void setStationTypeId(String StationTypeId) {
        this.StationTypeId = StationTypeId;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String Longitude) {
        this.Longitude = Longitude;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String Latitude) {
        this.Latitude = Latitude;
    }

    public Object getRemark() {
        return Remark;
    }

    public void setRemark(Object Remark) {
        this.Remark = Remark;
    }

    public Object getRemoveFlag() {
        return RemoveFlag;
    }

    public void setRemoveFlag(Object RemoveFlag) {
        this.RemoveFlag = RemoveFlag;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public int getParentId() {
        return ParentId;
    }

    public void setParentId(int ParentId) {
        this.ParentId = ParentId;
    }

    public String getParentName() {
        return ParentName;
    }

    public void setParentName(String ParentName) {
        this.ParentName = ParentName;
    }
}
