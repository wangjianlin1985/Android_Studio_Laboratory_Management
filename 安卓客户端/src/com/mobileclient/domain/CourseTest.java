package com.mobileclient.domain;

import java.io.Serializable;

public class CourseTest implements Serializable {
    /*ʵ��id*/
    private int testId;
    public int getTestId() {
        return testId;
    }
    public void setTestId(int testId) {
        this.testId = testId;
    }

    /*�����γ�*/
    private String courseObj;
    public String getCourseObj() {
        return courseObj;
    }
    public void setCourseObj(String courseObj) {
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
    private int weekObj;
    public int getWeekObj() {
        return weekObj;
    }
    public void setWeekObj(int weekObj) {
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
    private int labObj;
    public int getLabObj() {
        return labObj;
    }
    public void setLabObj(int labObj) {
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