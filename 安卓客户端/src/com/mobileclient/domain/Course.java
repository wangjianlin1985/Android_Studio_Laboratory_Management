package com.mobileclient.domain;

import java.io.Serializable;

public class Course implements Serializable {
    /*课程编号*/
    private String courseNo;
    public String getCourseNo() {
        return courseNo;
    }
    public void setCourseNo(String courseNo) {
        this.courseNo = courseNo;
    }

    /*所属班级*/
    private String classObj;
    public String getClassObj() {
        return classObj;
    }
    public void setClassObj(String classObj) {
        this.classObj = classObj;
    }

    /*课程名称*/
    private String courseName;
    public String getCourseName() {
        return courseName;
    }
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    /*课程总学时*/
    private int courseHours;
    public int getCourseHours() {
        return courseHours;
    }
    public void setCourseHours(int courseHours) {
        this.courseHours = courseHours;
    }

    /*课程学分*/
    private float courseScore;
    public float getCourseScore() {
        return courseScore;
    }
    public void setCourseScore(float courseScore) {
        this.courseScore = courseScore;
    }

    /*上课老师*/
    private String teacherObj;
    public String getTeacherObj() {
        return teacherObj;
    }
    public void setTeacherObj(String teacherObj) {
        this.teacherObj = teacherObj;
    }

    /*课程描述*/
    private String courseDesc;
    public String getCourseDesc() {
        return courseDesc;
    }
    public void setCourseDesc(String courseDesc) {
        this.courseDesc = courseDesc;
    }

}