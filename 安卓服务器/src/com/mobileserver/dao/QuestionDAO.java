package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.Question;
import com.mobileserver.util.DB;

public class QuestionDAO {

	public List<Question> QueryQuestion(int teacherId,String questioner) {
		List<Question> questionList = new ArrayList<Question>();
		DB db = new DB();
		String sql = "select * from Question where 1=1";
		if (teacherId != 0)
			sql += " and teacherId=" + teacherId;
		if (!questioner.equals(""))
			sql += " and questioner like '%" + questioner + "%'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				Question question = new Question();
				question.setId(rs.getInt("id"));
				question.setTeacherId(rs.getInt("teacherId"));
				question.setQuestioner(rs.getString("questioner"));
				question.setContent(rs.getString("content"));
				question.setReply(rs.getString("reply"));
				question.setAddTime(rs.getString("addTime"));
				questionList.add(question);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return questionList;
	}
	/* ���������ʴ���󣬽��������ʴ�����ҵ�� */
	public String AddQuestion(Question question) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в����������ʴ� */
			String sqlString = "insert into Question(teacherId,questioner,content,reply,addTime) values (";
			sqlString += question.getTeacherId() + ",";
			sqlString += "'" + question.getQuestioner() + "',";
			sqlString += "'" + question.getContent() + "',";
			sqlString += "'" + question.getReply() + "',";
			sqlString += "'" + question.getAddTime() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "�����ʴ���ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�����ʴ����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ�������ʴ� */
	public String DeleteQuestion(int id) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Question where id=" + id;
			db.executeUpdate(sqlString);
			result = "�����ʴ�ɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�����ʴ�ɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݼ�¼��Ż�ȡ�������ʴ� */
	public Question GetQuestion(int id) {
		Question question = null;
		DB db = new DB();
		String sql = "select * from Question where id=" + id;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				question = new Question();
				question.setId(rs.getInt("id"));
				question.setTeacherId(rs.getInt("teacherId"));
				question.setQuestioner(rs.getString("questioner"));
				question.setContent(rs.getString("content"));
				question.setReply(rs.getString("reply"));
				question.setAddTime(rs.getString("addTime"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return question;
	}
	/* ���������ʴ� */
	public String UpdateQuestion(Question question) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update Question set ";
			sql += "teacherId=" + question.getTeacherId() + ",";
			sql += "questioner='" + question.getQuestioner() + "',";
			sql += "content='" + question.getContent() + "',";
			sql += "reply='" + question.getReply() + "',";
			sql += "addTime='" + question.getAddTime() + "'";
			sql += " where id=" + question.getId();
			db.executeUpdate(sql);
			result = "�����ʴ���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�����ʴ����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
