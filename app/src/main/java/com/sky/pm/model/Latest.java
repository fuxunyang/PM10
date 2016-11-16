package com.sky.pm.model;

import com.sky.pm.api.JsonToResponse;

import org.xutils.http.annotation.HttpResponse;

import java.io.Serializable;

/**
 * Created by 李彬 on 2016/11/14.
 */
@HttpResponse(parser = JsonToResponse.class)
public class Latest implements Serializable {

    /**
     * Id : 1
     * StationId : 8BED614F-95B1-45CB-990C-5F070E2F0236
     * ParamId : 490BDC17-51D2-4A6B-9987-90367600E76F
     * DataValue : 153.4
     * DataTime : 2016-11-14T15:52:00
     * CreateTime : 2016-11-14T15:52:34
     * DataFlag : N
     * IAQI : 151.7
     * AQI : 151.7
     * AQILevel : 4
     * MaxValue : null
     * MinValue : null
     * Stationmn : jnwryyq0000082
     * AreaId : 2
     * Password : 123456
     * StationName : 横河煤矿
     * StationTypeId : HN-CK3000
     * Longitude : 116.794748
     * Latitude : 35.400546
     * Remark : null
     * RemoveFlag : null
     * ParentId : 1
     * ParentName : 山东省
     * Name : 济宁市
     */

    private String Id;
    private String StationId;
    private String ParamId;
    private String DataValue;
    private String DataTime;
    private String CreateTime;
    private String DataFlag;
    private double IAQI;
    private double AQI;
    private int AQILevel;
    private Object MaxValue;
    private Object MinValue;
    private String Stationmn;
    private int AreaId;
    private String Password;
    private String StationName;
    private String StationTypeId;
    private String Longitude;
    private String Latitude;
    private Object Remark;
    private Object RemoveFlag;
    private int ParentId;
    private String ParentName;
    private String Name;

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getStationId() {
        return StationId;
    }

    public void setStationId(String StationId) {
        this.StationId = StationId;
    }

    public String getParamId() {
        return ParamId;
    }

    public void setParamId(String ParamId) {
        this.ParamId = ParamId;
    }

    public String getDataValue() {
        return DataValue;
    }

    public void setDataValue(String DataValue) {
        this.DataValue = DataValue;
    }

    public String getDataTime() {
        return DataTime == null ? DataTime : DataTime.replace("T", " ");
    }

    public void setDataTime(String DataTime) {
        this.DataTime = DataTime;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public String getDataFlag() {
        return DataFlag;
    }

    public void setDataFlag(String DataFlag) {
        this.DataFlag = DataFlag;
    }

    public double getIAQI() {
        return IAQI;
    }

    public void setIAQI(double IAQI) {
        this.IAQI = IAQI;
    }

    public double getAQI() {
        return AQI;
    }

    public void setAQI(double AQI) {
        this.AQI = AQI;
    }

    public int getAQILevel() {
        return AQILevel;
    }

    public void setAQILevel(int AQILevel) {
        this.AQILevel = AQILevel;
    }

    public Object getMaxValue() {
        return MaxValue;
    }

    public void setMaxValue(Object MaxValue) {
        this.MaxValue = MaxValue;
    }

    public Object getMinValue() {
        return MinValue;
    }

    public void setMinValue(Object MinValue) {
        this.MinValue = MinValue;
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

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }
}
