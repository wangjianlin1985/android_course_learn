package com.mobileserver.domain;

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
    private int homeworkTaskObj;
    public int getHomeworkTaskObj() {
        return homeworkTaskObj;
    }
    public void setHomeworkTaskObj(int homeworkTaskObj) {
        this.homeworkTaskObj = homeworkTaskObj;
    }

    /*�ύ��ѧ��*/
    private String studentObj;
    public String getStudentObj() {
        return studentObj;
    }
    public void setStudentObj(String studentObj) {
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