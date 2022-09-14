package com.benben.carmanager.bean;

import com.benben.carmanager.R;

public class ShareParkBean {
    private int id;
    private String parkName;
    private String parkAddr;
    private String price;
    private String startTime;
    private String endTime;

    public ShareParkBean() {
    }

    public ShareParkBean(int id, String parkName, String parkAddr, String price, String startTime, String endTime) {
        this.id = id;
        this.parkName = parkName;
        this.parkAddr = parkAddr;
        this.price = price;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public ShareParkBean(String parkName, String parkAddr, String price, String startTime, String endTime) {
        this.parkName = parkName;
        this.parkAddr = parkAddr;
        this.price = price;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getParkName() {
        return parkName;
    }

    public void setParkName(String parkName) {
        this.parkName = parkName;
    }

    public String getParkAddr() {
        return parkAddr;
    }

    public void setParkAddr(String parkAddr) {
        this.parkAddr = parkAddr;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
