package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Video {
    /*��¼���*/
    private int id;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    /*��Ƶ���ϱ���*/
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

    /*�ļ�·��*/
    private String path;
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }

    /*���ʱ��*/
    private String addTime;
    public String getAddTime() {
        return addTime;
    }
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

}