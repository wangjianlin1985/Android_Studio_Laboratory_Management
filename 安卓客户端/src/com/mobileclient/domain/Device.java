package com.mobileclient.domain;

import java.io.Serializable;

public class Device implements Serializable {
    /*设备id*/
    private int deviceId;
    public int getDeviceId() {
        return deviceId;
    }
    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    /*设备名称*/
    private String deviceName;
    public String getDeviceName() {
        return deviceName;
    }
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    /*所属实验室*/
    private int labObj;
    public int getLabObj() {
        return labObj;
    }
    public void setLabObj(int labObj) {
        this.labObj = labObj;
    }

    /*设备图片*/
    private String devicePhoto;
    public String getDevicePhoto() {
        return devicePhoto;
    }
    public void setDevicePhoto(String devicePhoto) {
        this.devicePhoto = devicePhoto;
    }

    /*设备价格*/
    private float devicePrice;
    public float getDevicePrice() {
        return devicePrice;
    }
    public void setDevicePrice(float devicePrice) {
        this.devicePrice = devicePrice;
    }

    /*设备数量*/
    private int deviceCount;
    public int getDeviceCount() {
        return deviceCount;
    }
    public void setDeviceCount(int deviceCount) {
        this.deviceCount = deviceCount;
    }

    /*设备描述*/
    private String deviceDesc;
    public String getDeviceDesc() {
        return deviceDesc;
    }
    public void setDeviceDesc(String deviceDesc) {
        this.deviceDesc = deviceDesc;
    }

}