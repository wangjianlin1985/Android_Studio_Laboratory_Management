package com.mobileclient.domain;

import java.io.Serializable;

public class CourseTest implements Serializable {
    /*实验id*/
    private int testId;
    public int getTestId() {
        return testId;
    }
    public void setTestId(int testId) {
        this.testId = testId;
    }

    /*所属课程*/
    private String courseObj;
    public String getCourseObj() {
        return courseObj;
    }
    public void setCourseObj(String courseObj) {
        this.courseObj = courseObj;
    }

    /*实验名称*/
    private String testName;
    public String getTestName() {
        return testName;
    }
    public void setTestName(String testName) {
        this.testName = testName;
    }

    /*上课周*/
    private int weekObj;
    public int getWeekObj() {
        return weekObj;
    }
    public void setWeekObj(int weekObj) {
        this.weekObj = weekObj;
    }

    /*实验时间*/
    private String labTime;
    public String getLabTime() {
        return labTime;
    }
    public void setLabTime(String labTime) {
        this.labTime = labTime;
    }

    /*上课实验室*/
    private int labObj;
    public int getLabObj() {
        return labObj;
    }
    public void setLabObj(int labObj) {
        this.labObj = labObj;
    }

    /*实验备注*/
    private String testMemo;
    public String getTestMemo() {
        return testMemo;
    }
    public void setTestMemo(String testMemo) {
        this.testMemo = testMemo;
    }

}