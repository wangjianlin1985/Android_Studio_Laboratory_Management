package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Device {
    /*�豸id*/
    private int deviceId;
    public int getDeviceId() {
        return deviceId;
    }
    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    /*�豸����*/
    private String deviceName;
    public String getDeviceName() {
        return deviceName;
    }
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    /*����ʵ����*/
    private LabInfo labObj;
    public LabInfo getLabObj() {
        return labObj;
    }
    public void setLabObj(LabInfo labObj) {
        this.labObj = labObj;
    }

    /*�豸ͼƬ*/
    private String devicePhoto;
    public String getDevicePhoto() {
        return devicePhoto;
    }
    public void setDevicePhoto(String devicePhoto) {
        this.devicePhoto = devicePhoto;
    }

    /*�豸�۸�*/
    private float devicePrice;
    public float getDevicePrice() {
        return devicePrice;
    }
    public void setDevicePrice(float devicePrice) {
        this.devicePrice = devicePrice;
    }

    /*�豸����*/
    private int deviceCount;
    public int getDeviceCount() {
        return deviceCount;
    }
    public void setDeviceCount(int deviceCount) {
        this.deviceCount = deviceCount;
    }

    /*�豸����*/
    private String deviceDesc;
    public String getDeviceDesc() {
        return deviceDesc;
    }
    public void setDeviceDesc(String deviceDesc) {
        this.deviceDesc = deviceDesc;
    }

}