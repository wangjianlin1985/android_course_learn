package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.HomeworkTask;
import com.mobileserver.util.DB;

public class HomeworkTaskDAO {

	public List<HomeworkTask> QueryHomeworkTask(int teacherObj,String title) {
		List<HomeworkTask> homeworkTaskList = new ArrayList<HomeworkTask>();
		DB db = new DB();
		String sql = "select * from HomeworkTask where 1=1";
		if (teacherObj != 0)
			sql += " and teacherObj=" + teacherObj;
		if (!title.equals(""))
			sql += " and title like '%" + title + "%'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				HomeworkTask homeworkTask = new HomeworkTask();
				homeworkTask.setHomeworkId(rs.getInt("homeworkId"));
				homeworkTask.setTeacherObj(rs.getInt("teacherObj"));
				homeworkTask.setTitle(rs.getString("title"));
				homeworkTask.setContent(rs.getString("content"));
				homeworkTask.setAddTime(rs.getString("addTime"));
				homeworkTaskList.add(homeworkTask);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return homeworkTaskList;
	}
	/* ������ҵ������󣬽�����ҵ��������ҵ�� */
	public String AddHomeworkTask(HomeworkTask homeworkTask) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в�������ҵ���� */
			String sqlString = "insert into HomeworkTask(teacherObj,title,content,addTime) values (";
			sqlString += homeworkTask.getTeacherObj() + ",";
			sqlString += "'" + homeworkTask.getTitle() + "',";
			sqlString += "'" + homeworkTask.getContent() + "',";
			sqlString += "'" + homeworkTask.getAddTime() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "��ҵ������ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��ҵ�������ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ����ҵ���� */
	public String DeleteHomeworkTask(int homeworkId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from HomeworkTask where homeworkId=" + homeworkId;
			db.executeUpdate(sqlString);
			result = "��ҵ����ɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��ҵ����ɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݼ�¼��Ż�ȡ����ҵ���� */
	public HomeworkTask GetHomeworkTask(int homeworkId) {
		HomeworkTask homeworkTask = null;
		DB db = new DB();
		String sql = "select * from HomeworkTask where homeworkId=" + homeworkId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				homeworkTask = new HomeworkTask();
				homeworkTask.setHomeworkId(rs.getInt("homeworkId"));
				homeworkTask.setTeacherObj(rs.getInt("teacherObj"));
				homeworkTask.setTitle(rs.getString("title"));
				homeworkTask.setContent(rs.getString("content"));
				homeworkTask.setAddTime(rs.getString("addTime"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return homeworkTask;
	}
	/* ������ҵ���� */
	public String UpdateHomeworkTask(HomeworkTask homeworkTask) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update HomeworkTask set ";
			sql += "teacherObj=" + homeworkTask.getTeacherObj() + ",";
			sql += "title='" + homeworkTask.getTitle() + "',";
			sql += "content='" + homeworkTask.getContent() + "',";
			sql += "addTime='" + homeworkTask.getAddTime() + "'";
			sql += " where homeworkId=" + homeworkTask.getHomeworkId();
			db.executeUpdate(sql);
			result = "��ҵ������³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��ҵ�������ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
