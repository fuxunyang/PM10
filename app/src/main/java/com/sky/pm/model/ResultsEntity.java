package com.sky.pm.model;

import java.io.Serializable;
import java.util.List;

public class ResultsEntity implements Serializable {
    private String currentCity;
    private String pm25;
    private List<WeatherDataEntity> weather_data;

    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public void setWeather_data(List<WeatherDataEntity> weather_data) {
        this.weather_data = weather_data;
    }

    public String getCurrentCity() {
        return currentCity;
    }

    public String getPm25() {
        return pm25;
    }

    public List<WeatherDataEntity> getWeather_data() {
        return weather_data;
    }
}
