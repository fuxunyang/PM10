package com.sky.pm.model;

import java.io.Serializable;

public class WeatherDataEntity implements Serializable {
    private String nightPictureUrl;
    private String weather;
    private String wind;
    private String temperature;
    private String dayPictureUrl;
    private String date;

    public void setNightPictureUrl(String nightPictureUrl) {
        this.nightPictureUrl = nightPictureUrl;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public void setDayPictureUrl(String dayPictureUrl) {
        this.dayPictureUrl = dayPictureUrl;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNightPictureUrl() {
        return nightPictureUrl;
    }

    public String getWeather() {
        return weather;
    }

    public String getWind() {
        return wind;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getDayPictureUrl() {
        return dayPictureUrl;
    }

    public String getDate() {
        return date;
    }
}
