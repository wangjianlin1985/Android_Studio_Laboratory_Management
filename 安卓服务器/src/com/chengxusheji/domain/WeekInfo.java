package com.chengxusheji.domain;

import java.sql.Timestamp;
public class WeekInfo {
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