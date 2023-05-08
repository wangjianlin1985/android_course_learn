package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.HomeworkUpload;
import com.mobileserver.util.DB;

public class HomeworkUploadDAO {

	public List<HomeworkUpload> QueryHomeworkUpload(int homeworkTaskObj,String studentObj) {
		List<HomeworkUpload> homeworkUploadList = new ArrayList<HomeworkUpload>();
		DB db = new DB();
		String sql = "select * from HomeworkUpload where 1=1";
		if (homeworkTaskObj != 0)
			sql += " and homeworkTaskObj=" + homeworkTaskObj;
		if (!studentObj.equals(""))
			sql += " and studentObj = '" + studentObj + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				HomeworkUpload homeworkUpload = new HomeworkUpload();
				homeworkUpload.setUploadId(rs.getInt("uploadId"));
				homeworkUpload.setHomeworkTaskObj(rs.getInt("homeworkTaskObj"));
				homeworkUpload.setStudentObj(rs.getString("studentObj"));
				homeworkUpload.setHomeworkFile(rs.getString("homeworkFile"));
				homeworkUpload.setUploadTime(rs.getString("uploadTime"));
				homeworkUpload.setResultFile(rs.getString("resultFile"));
				homeworkUpload.setPigaiTime(rs.getString("pigaiTime"));
				homeworkUpload.setPigaiFlag(rs.getInt("pigaiFlag"));
				homeworkUpload.setPingyu(rs.getString("pingyu"));
				homeworkUploadList.add(homeworkUpload);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return homeworkUploadList;
	}
	/* 传入上传的作业对象，进行上传的作业的添加业务 */
	public String AddHomeworkUpload(HomeworkUpload homeworkUpload) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新上传的作业 */
			String sqlString = "insert into HomeworkUpload(homeworkTaskObj,studentObj,homeworkFile,uploadTime,resultFile,pigaiTime,pigaiFlag,pingyu) values (";
			sqlString += homeworkUpload.getHomeworkTaskObj() + ",";
			sqlString += "'" + homeworkUpload.getStudentObj() + "',";
			sqlString += "'" + homeworkUpload.getHomeworkFile() + "',";
			sqlString += "'" + homeworkUpload.getUploadTime() + "',";
			sqlString += "'" + homeworkUpload.getResultFile() + "',";
			sqlString += "'" + homeworkUpload.getPigaiTime() + "',";
			sqlString += homeworkUpload.getPigaiFlag() + ",";
			sqlString += "'" + homeworkUpload.getPingyu() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "上传的作业添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "上传的作业添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除上传的作业 */
	public String DeleteHomeworkUpload(int uploadId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from HomeworkUpload where uploadId=" + uploadId;
			db.executeUpdate(sqlString);
			result = "上传的作业删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "上传的作业删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据记录编号获取到上传的作业 */
	public HomeworkUpload GetHomeworkUpload(int uploadId) {
		HomeworkUpload homeworkUpload = null;
		DB db = new DB();
		String sql = "select * from HomeworkUpload where uploadId=" + uploadId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				homeworkUpload = new HomeworkUpload();
				homeworkUpload.setUploadId(rs.getInt("uploadId"));
				homeworkUpload.setHomeworkTaskObj(rs.getInt("homeworkTaskObj"));
				homeworkUpload.setStudentObj(rs.getString("studentObj"));
				homeworkUpload.setHomeworkFile(rs.getString("homeworkFile"));
				homeworkUpload.setUploadTime(rs.getString("uploadTime"));
				homeworkUpload.setResultFile(rs.getString("resultFile"));
				homeworkUpload.setPigaiTime(rs.getString("pigaiTime"));
				homeworkUpload.setPigaiFlag(rs.getInt("pigaiFlag"));
				homeworkUpload.setPingyu(rs.getString("pingyu"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return homeworkUpload;
	}
	/* 更新上传的作业 */
	public String UpdateHomeworkUpload(HomeworkUpload homeworkUpload) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update HomeworkUpload set ";
			sql += "homeworkTaskObj=" + homeworkUpload.getHomeworkTaskObj() + ",";
			sql += "studentObj='" + homeworkUpload.getStudentObj() + "',";
			sql += "homeworkFile='" + homeworkUpload.getHomeworkFile() + "',";
			sql += "uploadTime='" + homeworkUpload.getUploadTime() + "',";
			sql += "resultFile='" + homeworkUpload.getResultFile() + "',";
			sql += "pigaiTime='" + homeworkUpload.getPigaiTime() + "',";
			sql += "pigaiFlag=" + homeworkUpload.getPigaiFlag() + ",";
			sql += "pingyu='" + homeworkUpload.getPingyu() + "'";
			sql += " where uploadId=" + homeworkUpload.getUploadId();
			db.executeUpdate(sql);
			result = "上传的作业更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "上传的作业更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
