package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.Teacher;
import com.mobileserver.util.DB;

public class TeacherDAO {

	public List<Teacher> QueryTeacher(String name,String position) {
		List<Teacher> teacherList = new ArrayList<Teacher>();
		DB db = new DB();
		String sql = "select * from Teacher where 1=1";
		if (!name.equals(""))
			sql += " and name like '%" + name + "%'";
		if (!position.equals(""))
			sql += " and position like '%" + position + "%'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				Teacher teacher = new Teacher();
				teacher.setId(rs.getInt("id"));
				teacher.setName(rs.getString("name"));
				teacher.setPosition(rs.getString("position"));
				teacher.setPassword(rs.getString("password"));
				teacher.setIntroduction(rs.getString("introduction"));
				teacherList.add(teacher);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return teacherList;
	}
	/* �����ʦ��Ϣ���󣬽��н�ʦ��Ϣ�����ҵ�� */
	public String AddTeacher(Teacher teacher) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в����½�ʦ��Ϣ */
			String sqlString = "insert into Teacher(name,position,password,introduction) values (";
			sqlString += "'" + teacher.getName() + "',";
			sqlString += "'" + teacher.getPosition() + "',";
			sqlString += "'" + teacher.getPassword() + "',";
			sqlString += "'" + teacher.getIntroduction() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "��ʦ��Ϣ��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��ʦ��Ϣ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ����ʦ��Ϣ */
	public String DeleteTeacher(int id) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Teacher where id=" + id;
			db.executeUpdate(sqlString);
			result = "��ʦ��Ϣɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��ʦ��Ϣɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݼ�¼��Ż�ȡ����ʦ��Ϣ */
	public Teacher GetTeacher(int id) {
		Teacher teacher = null;
		DB db = new DB();
		String sql = "select * from Teacher where id=" + id;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				teacher = new Teacher();
				teacher.setId(rs.getInt("id"));
				teacher.setName(rs.getString("name"));
				teacher.setPosition(rs.getString("position"));
				teacher.setPassword(rs.getString("password"));
				teacher.setIntroduction(rs.getString("introduction"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return teacher;
	}
	/* ���½�ʦ��Ϣ */
	public String UpdateTeacher(Teacher teacher) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update Teacher set ";
			sql += "name='" + teacher.getName() + "',";
			sql += "position='" + teacher.getPosition() + "',";
			sql += "password='" + teacher.getPassword() + "',";
			sql += "introduction='" + teacher.getIntroduction() + "'";
			sql += " where id=" + teacher.getId();
			db.executeUpdate(sql);
			result = "��ʦ��Ϣ���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��ʦ��Ϣ����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
