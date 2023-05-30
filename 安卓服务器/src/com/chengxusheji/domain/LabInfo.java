package com.chengxusheji.domain;

import java.sql.Timestamp;
public class LabInfo {
    /*实验室id*/
    private int labId;
    public int getLabId() {
        return labId;
    }
    public void setLabId(int labId) {
        this.labId = labId;
    }

    /*实验室名称*/
    private String labName;
    public String getLabName() {
        return labName;
    }
    public void setLabName(String labName) {
        this.labName = labName;
    }

    /*实验室图片*/
    private String labPhoto;
    public String getLabPhoto() {
        return labPhoto;
    }
    public void setLabPhoto(String labPhoto) {
        this.labPhoto = labPhoto;
    }

    /*实验室面积*/
    private float labArea;
    public float getLabArea() {
        return labArea;
    }
    public void setLabArea(float labArea) {
        this.labArea = labArea;
    }

    /*容纳人数*/
    private int personNum;
    public int getPersonNum() {
        return personNum;
    }
    public void setPersonNum(int personNum) {
        this.personNum = personNum;
    }

    /*实验室地址*/
    private String labAddress;
    public String getLabAddress() {
        return labAddress;
    }
    public void setLabAddress(String labAddress) {
        this.labAddress = labAddress;
    }

    /*实验室状态*/
    private LatState labStateObj;
    public LatState getLabStateObj() {
        return labStateObj;
    }
    public void setLabStateObj(LatState labStateObj) {
        this.labStateObj = labStateObj;
    }

    /*实验室介绍*/
    private String labDesc;
    public String getLabDesc() {
        return labDesc;
    }
    public void setLabDesc(String labDesc) {
        this.labDesc = labDesc;
    }

}