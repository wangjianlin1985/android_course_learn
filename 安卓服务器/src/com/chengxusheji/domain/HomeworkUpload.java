package com.chengxusheji.domain;

import java.sql.Timestamp;
public class HomeworkUpload {
    /*��¼���*/
    private int uploadId;
    public int getUploadId() {
        return uploadId;
    }
    public void setUploadId(int uploadId) {
        this.uploadId = uploadId;
    }

    /*��ҵ����*/
    private HomeworkTask homeworkTaskObj;
    public HomeworkTask getHomeworkTaskObj() {
        return homeworkTaskObj;
    }
    public void setHomeworkTaskObj(HomeworkTask homeworkTaskObj) {
        this.homeworkTaskObj = homeworkTaskObj;
    }

    /*�ύ��ѧ��*/
    private Student studentObj;
    public Student getStudentObj() {
        return studentObj;
    }
    public void setStudentObj(Student studentObj) {
        this.studentObj = studentObj;
    }

    /*��ҵ�ļ�*/
    private String homeworkFile;
    public String getHomeworkFile() {
        return homeworkFile;
    }
    public void setHomeworkFile(String homeworkFile) {
        this.homeworkFile = homeworkFile;
    }

    /*�ύʱ��*/
    private String uploadTime;
    public String getUploadTime() {
        return uploadTime;
    }
    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    /*���Ľ���ļ�*/
    private String resultFile;
    public String getResultFile() {
        return resultFile;
    }
    public void setResultFile(String resultFile) {
        this.resultFile = resultFile;
    }

    /*����ʱ��*/
    private String pigaiTime;
    public String getPigaiTime() {
        return pigaiTime;
    }
    public void setPigaiTime(String pigaiTime) {
        this.pigaiTime = pigaiTime;
    }

    /*�Ƿ�����*/
    private int pigaiFlag;
    public int getPigaiFlag() {
        return pigaiFlag;
    }
    public void setPigaiFlag(int pigaiFlag) {
        this.pigaiFlag = pigaiFlag;
    }

    /*����*/
    private String pingyu;
    public String getPingyu() {
        return pingyu;
    }
    public void setPingyu(String pingyu) {
        this.pingyu = pingyu;
    }

}