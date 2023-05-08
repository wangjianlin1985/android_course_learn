package com.mobileclient.domain;

import java.io.Serializable;

public class CourseInfo implements Serializable {
    /*记录编号*/
    private int id;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    /*课程简介*/
    private String jianjie;
    public String getJianjie() {
        return jianjie;
    }
    public void setJianjie(String jianjie) {
        this.jianjie = jianjie;
    }

    /*课程大纲*/
    private String dagan;
    public String getDagan() {
        return dagan;
    }
    public void setDagan(String dagan) {
        this.dagan = dagan;
    }

}