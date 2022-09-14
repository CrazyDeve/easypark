package com.benben.carmanager.bean;

import java.io.Serializable;

public class CarPortBean implements Serializable {
    private String title;
    private String addr;
    private double lat;
    private double lng;
    private String cityName;
    private String adName;

    public CarPortBean(String title, String addr, double lat, double lng, String cityName, String adName) {
        this.title = title;
        this.addr = addr;
        this.lat = lat;
        this.lng = lng;
        this.cityName = cityName;
        this.adName = adName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAdName() {
        return adName;
    }

    public void setAdName(String adName) {
        this.adName = adName;
    }
}
