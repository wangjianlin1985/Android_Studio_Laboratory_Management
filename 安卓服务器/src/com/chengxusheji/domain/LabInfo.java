package com.chengxusheji.domain;

import java.sql.Timestamp;
public class LabInfo {
    /*ʵ����id*/
    private int labId;
    public int getLabId() {
        return labId;
    }
    public void setLabId(int labId) {
        this.labId = labId;
    }

    /*ʵ��������*/
    private String labName;
    public String getLabName() {
        return labName;
    }
    public void setLabName(String labName) {
        this.labName = labName;
    }

    /*ʵ����ͼƬ*/
    private String labPhoto;
    public String getLabPhoto() {
        return labPhoto;
    }
    public void setLabPhoto(String labPhoto) {
        this.labPhoto = labPhoto;
    }

    /*ʵ�������*/
    private float labArea;
    public float getLabArea() {
        return labArea;
    }
    public void setLabArea(float labArea) {
        this.labArea = labArea;
    }

    /*��������*/
    private int personNum;
    public int getPersonNum() {
        return personNum;
    }
    public void setPersonNum(int personNum) {
        this.personNum = personNum;
    }

    /*ʵ���ҵ�ַ*/
    private String labAddress;
    public String getLabAddress() {
        return labAddress;
    }
    public void setLabAddress(String labAddress) {
        this.labAddress = labAddress;
    }

    /*ʵ����״̬*/
    private LatState labStateObj;
    public LatState getLabStateObj() {
        return labStateObj;
    }
    public void setLabStateObj(LatState labStateObj) {
        this.labStateObj = labStateObj;
    }

    /*ʵ���ҽ���*/
    private String labDesc;
    public String getLabDesc() {
        return labDesc;
    }
    public void setLabDesc(String labDesc) {
        this.labDesc = labDesc;
    }

}