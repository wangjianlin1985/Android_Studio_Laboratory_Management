package com.chengxusheji.domain;

import java.sql.Timestamp;
public class CourseTest {
    /*实验id*/
    private int testId;
    public int getTestId() {
        return testId;
    }
    public void setTestId(int testId) {
        this.testId = testId;
    }

    /*所属课程*/
    private Course courseObj;
    public Course getCourseObj() {
        return courseObj;
    }
    public void setCourseObj(Course courseObj) {
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
    private WeekInfo weekObj;
    public WeekInfo getWeekObj() {
        return weekObj;
    }
    public void setWeekObj(WeekInfo weekObj) {
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
    private LabInfo labObj;
    public LabInfo getLabObj() {
        return labObj;
    }
    public void setLabObj(LabInfo labObj) {
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