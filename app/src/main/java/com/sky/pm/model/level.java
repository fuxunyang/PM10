package com.sky.pm.model;

import java.io.Serializable;

/**
 * Created by 李彬 on 2016/11/22.
 */

public class Level implements Serializable {

    /**
     * Id : 1
     * AQIName : 1级_优
     * AQILow : 0
     * AQIHigh : 50
     */

    private String Id;
    private String AQIName;
    private String AQILow;
    private String AQIHigh;

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getAQIName() {
        return AQIName;
    }

    public void setAQIName(String AQIName) {
        this.AQIName = AQIName;
    }

    public String getAQILow() {
        return AQILow;
    }

    public void setAQILow(String AQILow) {
        this.AQILow = AQILow;
    }

    public String getAQIHigh() {
        return AQIHigh;
    }

    public void setAQIHigh(String AQIHigh) {
        this.AQIHigh = AQIHigh;
    }
}
