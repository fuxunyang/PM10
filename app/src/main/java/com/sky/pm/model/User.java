package com.sky.pm.model;

import java.io.Serializable;

/**
 * Created by 机械革命 on 2016/8/14.
 */

public class User implements Serializable {
//    "ID": 1,
//            "TrueName": "村委会",
//            "UserID": "1",
//            "UserName": "1",
//            "Pwd": "1",
//            "AreaID": 460,
//            "PHNo": "1234567890",
//            "ZhenID": 17,
//            "ZhenName": "东二营",
//            "ParentId": 17,
//            "Code": "JXC433",
//            "Name": "西晋贡坨村",
//            "AreaLevel": "村",
//            "Status": 1,
//            "ParentName": "东二营"
    private String ID;
    private String TrueName;
    private String UserID;
    private String UserName;
    private String Pwd;
    private String AreaID;
    private String PHNo;
    private String ZhenID;
    private String ZhenName;
    private String ParentId;
    private String Code;
    private String Name;
    private String AreaLevel;
    private String Status;
    private String ParentName;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTrueName() {
        return TrueName;
    }

    public void setTrueName(String TrueName) {
        this.TrueName = TrueName;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getPwd() {
        return Pwd;
    }

    public void setPwd(String Pwd) {
        this.Pwd = Pwd;
    }

    public String getAreaID() {
        return AreaID;
    }

    public void setAreaID(String AreaID) {
        this.AreaID = AreaID;
    }

    public String getPHNo() {
        return PHNo;
    }

    public void setPHNo(String PHNo) {
        this.PHNo = PHNo;
    }

    public String getZhenID() {
        return ZhenID;
    }

    public void setZhenID(String ZhenID) {
        this.ZhenID = ZhenID;
    }

    public String getZhenName() {
        return ZhenName;
    }

    public void setZhenName(String ZhenName) {
        this.ZhenName = ZhenName;
    }

    public String getParentId() {
        return ParentId;
    }

    public void setParentId(String ParentId) {
        this.ParentId = ParentId;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getAreaLevel() {
        return AreaLevel;
    }

    public void setAreaLevel(String AreaLevel) {
        this.AreaLevel = AreaLevel;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getParentName() {
        return ParentName;
    }

    public void setParentName(String ParentName) {
        this.ParentName = ParentName;
    }
}
