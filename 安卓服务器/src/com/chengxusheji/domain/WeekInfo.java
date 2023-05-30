package com.chengxusheji.domain;

import java.sql.Timestamp;
public class WeekInfo {
    /*周信息Id*/
    private int weekId;
    public int getWeekId() {
        return weekId;
    }
    public void setWeekId(int weekId) {
        this.weekId = weekId;
    }

    /*周信息名称*/
    private String weekName;
    public String getWeekName() {
        return weekName;
    }
    public void setWeekName(String weekName) {
        this.weekName = weekName;
    }

}