package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Video {
    /*记录编号*/
    private int id;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    /*视频资料标题*/
    private String title;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    /*所属章*/
    private Chapter chapterId;
    public Chapter getChapterId() {
        return chapterId;
    }
    public void setChapterId(Chapter chapterId) {
        this.chapterId = chapterId;
    }

    /*文件路径*/
    private String path;
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }

    /*添加时间*/
    private String addTime;
    public String getAddTime() {
        return addTime;
    }
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

}