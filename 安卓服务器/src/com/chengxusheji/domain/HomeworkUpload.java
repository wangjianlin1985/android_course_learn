package com.chengxusheji.domain;

import java.sql.Timestamp;
public class HomeworkUpload {
    /*记录编号*/
    private int uploadId;
    public int getUploadId() {
        return uploadId;
    }
    public void setUploadId(int uploadId) {
        this.uploadId = uploadId;
    }

    /*作业标题*/
    private HomeworkTask homeworkTaskObj;
    public HomeworkTask getHomeworkTaskObj() {
        return homeworkTaskObj;
    }
    public void setHomeworkTaskObj(HomeworkTask homeworkTaskObj) {
        this.homeworkTaskObj = homeworkTaskObj;
    }

    /*提交的学生*/
    private Student studentObj;
    public Student getStudentObj() {
        return studentObj;
    }
    public void setStudentObj(Student studentObj) {
        this.studentObj = studentObj;
    }

    /*作业文件*/
    private String homeworkFile;
    public String getHomeworkFile() {
        return homeworkFile;
    }
    public void setHomeworkFile(String homeworkFile) {
        this.homeworkFile = homeworkFile;
    }

    /*提交时间*/
    private String uploadTime;
    public String getUploadTime() {
        return uploadTime;
    }
    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    /*批改结果文件*/
    private String resultFile;
    public String getResultFile() {
        return resultFile;
    }
    public void setResultFile(String resultFile) {
        this.resultFile = resultFile;
    }

    /*批改时间*/
    private String pigaiTime;
    public String getPigaiTime() {
        return pigaiTime;
    }
    public void setPigaiTime(String pigaiTime) {
        this.pigaiTime = pigaiTime;
    }

    /*是否批改*/
    private int pigaiFlag;
    public int getPigaiFlag() {
        return pigaiFlag;
    }
    public void setPigaiFlag(int pigaiFlag) {
        this.pigaiFlag = pigaiFlag;
    }

    /*评语*/
    private String pingyu;
    public String getPingyu() {
        return pingyu;
    }
    public void setPingyu(String pingyu) {
        this.pingyu = pingyu;
    }

}