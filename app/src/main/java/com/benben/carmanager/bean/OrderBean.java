package com.benben.carmanager.bean;

public class OrderBean {
    private int id;
    private String parkName;
    private String parkAddr;
    private String lat;
    private String lng;
    private String carNumber;
    private String startTime;
    private String endTime;

    private String price;
    private String status;

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public OrderBean() {
    }

    public OrderBean(String parkName, String parkAddr, String lat, String lng, String carNumber, String startTime, String endTime) {
        this.parkName = parkName;
        this.parkAddr = parkAddr;
        this.lat = lat;
        this.lng = lng;
        this.carNumber = carNumber;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public OrderBean(int id, String parkName, String parkAddr, String lat, String lng, String carNumber, String startTime, String endTime) {
        this.id = id;
        this.parkName = parkName;
        this.parkAddr = parkAddr;
        this.lat = lat;
        this.lng = lng;
        this.carNumber = carNumber;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public OrderBean(int id, String parkName, String parkAddr, String lat, String lng, String carNumber, String startTime, String endTime,String status) {
        this.id = id;
        this.parkName = parkName;
        this.parkAddr = parkAddr;
        this.lat = lat;
        this.lng = lng;
        this.carNumber = carNumber;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
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

    @Override
    public String toString() {
        return "OrderBean{" +
                "id=" + id +
                ", parkName='" + parkName + '\'' +
                ", parkAddr='" + parkAddr + '\'' +
                ", lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                ", carNumber='" + carNumber + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
