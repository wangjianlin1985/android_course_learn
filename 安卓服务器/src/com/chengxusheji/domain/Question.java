package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Question {
    /*��¼���*/
    private int id;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    /*���ʵ���ʦ*/
    private Teacher teacherId;
    public Teacher getTeacherId() {
        return teacherId;
    }
    public void setTeacherId(Teacher teacherId) {
        this.teacherId = teacherId;
    }

    /*������*/
    private String questioner;
    public String getQuestioner() {
        return questioner;
    }
    public void setQuestioner(String questioner) {
        this.questioner = questioner;
    }

    /*��������*/
    private String content;
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    /*�ظ�����*/
    private String reply;
    public String getReply() {
        return reply;
    }
    public void setReply(String reply) {
        this.reply = reply;
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