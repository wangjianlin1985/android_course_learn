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
	/* 传入教师信息对象，进行教师信息的添加业务 */
	public String AddTeacher(Teacher teacher) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新教师信息 */
			String sqlString = "insert into Teacher(name,position,password,introduction) values (";
			sqlString += "'" + teacher.getName() + "',";
			sqlString += "'" + teacher.getPosition() + "',";
			sqlString += "'" + teacher.getPassword() + "',";
			sqlString += "'" + teacher.getIntroduction() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "教师信息添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "教师信息添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除教师信息 */
	public String DeleteTeacher(int id) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Teacher where id=" + id;
			db.executeUpdate(sqlString);
			result = "教师信息删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "教师信息删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据记录编号获取到教师信息 */
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
	/* 更新教师信息 */
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
			result = "教师信息更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "教师信息更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
