package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Exercise {
    /*��¼���*/
    private int id;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    /*ϰ������*/
    private String title;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    /*������*/
    private Chapter chapterId;
    public Chapter getChapterId() {
        return chapterId;
    }
    public void setChapterId(Chapter chapterId) {
        this.chapterId = chapterId;
    }

    /*��ϰ����*/
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