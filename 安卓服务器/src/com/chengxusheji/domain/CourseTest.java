package com.chengxusheji.domain;

import java.sql.Timestamp;
public class CourseTest {
    /*ʵ��id*/
    private int testId;
    public int getTestId() {
        return testId;
    }
    public void setTestId(int testId) {
        this.testId = testId;
    }

    /*�����γ�*/
    private Course courseObj;
    public Course getCourseObj() {
        return courseObj;
    }
    public void setCourseObj(Course courseObj) {
        this.courseObj = courseObj;
    }

    /*ʵ������*/
    private String testName;
    public String getTestName() {
        return testName;
    }
    public void setTestName(String testName) {
        this.testName = testName;
    }

    /*�Ͽ���*/
    private WeekInfo weekObj;
    public WeekInfo getWeekObj() {
        return weekObj;
    }
    public void setWeekObj(WeekInfo weekObj) {
        this.weekObj = weekObj;
    }

    /*ʵ��ʱ��*/
    private String labTime;
    public String getLabTime() {
        return labTime;
    }
    public void setLabTime(String labTime) {
        this.labTime = labTime;
    }

    /*�Ͽ�ʵ����*/
    private LabInfo labObj;
    public LabInfo getLabObj() {
        return labObj;
    }
    public void setLabObj(LabInfo labObj) {
        this.labObj = labObj;
    }

    /*ʵ�鱸ע*/
    private String testMemo;
    public String getTestMemo() {
        return testMemo;
    }
    public void setTestMemo(String testMemo) {
        this.testMemo = testMemo;
    }

}