package com.mobileserver.domain;

public class HomeworkTask {
    /*��¼���*/
    private int homeworkId;
    public int getHomeworkId() {
        return homeworkId;
    }
    public void setHomeworkId(int homeworkId) {
        this.homeworkId = homeworkId;
    }

    /*��ʦ*/
    private int teacherObj;
    public int getTeacherObj() {
        return teacherObj;
    }
    public void setTeacherObj(int teacherObj) {
        this.teacherObj = teacherObj;
    }

    /*��ҵ����*/
    private String title;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    /*��ҵ����*/
    private String content;
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    /*����ʱ��*/
    private String addTime;
    public String getAddTime() {
        return addTime;
    }
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

}