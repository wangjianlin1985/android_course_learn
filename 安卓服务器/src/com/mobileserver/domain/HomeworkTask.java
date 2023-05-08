package com.mobileserver.domain;

public class HomeworkTask {
    /*记录编号*/
    private int homeworkId;
    public int getHomeworkId() {
        return homeworkId;
    }
    public void setHomeworkId(int homeworkId) {
        this.homeworkId = homeworkId;
    }

    /*老师*/
    private int teacherObj;
    public int getTeacherObj() {
        return teacherObj;
    }
    public void setTeacherObj(int teacherObj) {
        this.teacherObj = teacherObj;
    }

    /*作业标题*/
    private String title;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    /*作业内容*/
    private String content;
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    /*发布时间*/
    private String addTime;
    public String getAddTime() {
        return addTime;
    }
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

}