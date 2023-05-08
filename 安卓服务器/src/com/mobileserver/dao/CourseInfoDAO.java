package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.CourseInfo;
import com.mobileserver.util.DB;

public class CourseInfoDAO {

	public List<CourseInfo> QueryCourseInfo() {
		List<CourseInfo> courseInfoList = new ArrayList<CourseInfo>();
		DB db = new DB();
		String sql = "select * from CourseInfo where 1=1";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				CourseInfo courseInfo = new CourseInfo();
				courseInfo.setId(rs.getInt("id"));
				courseInfo.setJianjie(rs.getString("jianjie"));
				courseInfo.setDagan(rs.getString("dagan"));
				courseInfoList.add(courseInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return courseInfoList;
	}
	/* ����γ���Ϣ���󣬽��пγ���Ϣ�����ҵ�� */
	public String AddCourseInfo(CourseInfo courseInfo) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в����¿γ���Ϣ */
			String sqlString = "insert into CourseInfo(jianjie,dagan) values (";
			sqlString += "'" + courseInfo.getJianjie() + "',";
			sqlString += "'" + courseInfo.getDagan() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "�γ���Ϣ��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�γ���Ϣ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ���γ���Ϣ */
	public String DeleteCourseInfo(int id) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from CourseInfo where id=" + id;
			db.executeUpdate(sqlString);
			result = "�γ���Ϣɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�γ���Ϣɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݼ�¼��Ż�ȡ���γ���Ϣ */
	public CourseInfo GetCourseInfo(int id) {
		CourseInfo courseInfo = null;
		DB db = new DB();
		String sql = "select * from CourseInfo where id=" + id;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				courseInfo = new CourseInfo();
				courseInfo.setId(rs.getInt("id"));
				courseInfo.setJianjie(rs.getString("jianjie"));
				courseInfo.setDagan(rs.getString("dagan"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return courseInfo;
	}
	/* ���¿γ���Ϣ */
	public String UpdateCourseInfo(CourseInfo courseInfo) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update CourseInfo set ";
			sql += "jianjie='" + courseInfo.getJianjie() + "',";
			sql += "dagan='" + courseInfo.getDagan() + "'";
			sql += " where id=" + courseInfo.getId();
			db.executeUpdate(sql);
			result = "�γ���Ϣ���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�γ���Ϣ����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
