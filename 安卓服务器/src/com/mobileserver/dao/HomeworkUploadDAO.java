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
	/* �����ϴ�����ҵ���󣬽����ϴ�����ҵ�����ҵ�� */
	public String AddHomeworkUpload(HomeworkUpload homeworkUpload) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в������ϴ�����ҵ */
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
			result = "�ϴ�����ҵ��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�ϴ�����ҵ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ���ϴ�����ҵ */
	public String DeleteHomeworkUpload(int uploadId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from HomeworkUpload where uploadId=" + uploadId;
			db.executeUpdate(sqlString);
			result = "�ϴ�����ҵɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�ϴ�����ҵɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݼ�¼��Ż�ȡ���ϴ�����ҵ */
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
	/* �����ϴ�����ҵ */
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
			result = "�ϴ�����ҵ���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�ϴ�����ҵ����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
