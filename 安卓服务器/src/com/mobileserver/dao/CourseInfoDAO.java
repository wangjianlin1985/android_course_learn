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
	/* 传入课程信息对象，进行课程信息的添加业务 */
	public String AddCourseInfo(CourseInfo courseInfo) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新课程信息 */
			String sqlString = "insert into CourseInfo(jianjie,dagan) values (";
			sqlString += "'" + courseInfo.getJianjie() + "',";
			sqlString += "'" + courseInfo.getDagan() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "课程信息添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "课程信息添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除课程信息 */
	public String DeleteCourseInfo(int id) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from CourseInfo where id=" + id;
			db.executeUpdate(sqlString);
			result = "课程信息删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "课程信息删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据记录编号获取到课程信息 */
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
	/* 更新课程信息 */
	public String UpdateCourseInfo(CourseInfo courseInfo) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update CourseInfo set ";
			sql += "jianjie='" + courseInfo.getJianjie() + "',";
			sql += "dagan='" + courseInfo.getDagan() + "'";
			sql += " where id=" + courseInfo.getId();
			db.executeUpdate(sql);
			result = "课程信息更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "课程信息更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
