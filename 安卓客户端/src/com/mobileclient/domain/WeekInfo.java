package com.mobileclient.domain;

import java.io.Serializable;

public class WeekInfo implements Serializable {
    /*����ϢId*/
    private int weekId;
    public int getWeekId() {
        return weekId;
    }
    public void setWeekId(int weekId) {
        this.weekId = weekId;
    }

    /*����Ϣ����*/
    private String weekName;
    public String getWeekName() {
        return weekName;
    }
    public void setWeekName(String weekName) {
        this.weekName = weekName;
    }

}