package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Course {
    /*�γ̱��*/
    private String courseNo;
    public String getCourseNo() {
        return courseNo;
    }
    public void setCourseNo(String courseNo) {
        this.courseNo = courseNo;
    }

    /*�����༶*/
    private ClassInfo classObj;
    public ClassInfo getClassObj() {
        return classObj;
    }
    public void setClassObj(ClassInfo classObj) {
        this.classObj = classObj;
    }

    /*�γ�����*/
    private String courseName;
    public String getCourseName() {
        return courseName;
    }
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    /*�γ���ѧʱ*/
    private int courseHours;
    public int getCourseHours() {
        return courseHours;
    }
    public void setCourseHours(int courseHours) {
        this.courseHours = courseHours;
    }

    /*�γ�ѧ��*/
    private float courseScore;
    public float getCourseScore() {
        return courseScore;
    }
    public void setCourseScore(float courseScore) {
        this.courseScore = courseScore;
    }

    /*�Ͽ���ʦ*/
    private Teacher teacherObj;
    public Teacher getTeacherObj() {
        return teacherObj;
    }
    public void setTeacherObj(Teacher teacherObj) {
        this.teacherObj = teacherObj;
    }

    /*�γ�����*/
    private String courseDesc;
    public String getCourseDesc() {
        return courseDesc;
    }
    public void setCourseDesc(String courseDesc) {
        this.courseDesc = courseDesc;
    }

}