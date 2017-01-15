package com.sky.pm.model;

public class Update {
//    "ID": 2,
//            "version": "1",
//            "update_date": "2016-01-01T00:00:00"
    private String ID;
    private Integer version;
    private String update_date;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(String update_date) {
        this.update_date = update_date;
    }
}
