package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.Kejian;
import com.mobileserver.util.DB;

public class KejianDAO {

	public List<Kejian> QueryKejian(String title) {
		List<Kejian> kejianList = new ArrayList<Kejian>();
		DB db = new DB();
		String sql = "select * from Kejian where 1=1";
		if (!title.equals(""))
			sql += " and title like '%" + title + "%'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				Kejian kejian = new Kejian();
				kejian.setId(rs.getInt("id"));
				kejian.setTitle(rs.getString("title"));
				kejian.setPath(rs.getString("path"));
				kejian.setAddTime(rs.getString("addTime"));
				kejianList.add(kejian);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return kejianList;
	}
	/* ����μ���Ϣ���󣬽��пμ���Ϣ�����ҵ�� */
	public String AddKejian(Kejian kejian) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в����¿μ���Ϣ */
			String sqlString = "insert into Kejian(title,path,addTime) values (";
			sqlString += "'" + kejian.getTitle() + "',";
			sqlString += "'" + kejian.getPath() + "',";
			sqlString += "'" + kejian.getAddTime() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "�μ���Ϣ��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�μ���Ϣ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ���μ���Ϣ */
	public String DeleteKejian(int id) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Kejian where id=" + id;
			db.executeUpdate(sqlString);
			result = "�μ���Ϣɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�μ���Ϣɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݼ�¼��Ż�ȡ���μ���Ϣ */
	public Kejian GetKejian(int id) {
		Kejian kejian = null;
		DB db = new DB();
		String sql = "select * from Kejian where id=" + id;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				kejian = new Kejian();
				kejian.setId(rs.getInt("id"));
				kejian.setTitle(rs.getString("title"));
				kejian.setPath(rs.getString("path"));
				kejian.setAddTime(rs.getString("addTime"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return kejian;
	}
	/* ���¿μ���Ϣ */
	public String UpdateKejian(Kejian kejian) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update Kejian set ";
			sql += "title='" + kejian.getTitle() + "',";
			sql += "path='" + kejian.getPath() + "',";
			sql += "addTime='" + kejian.getAddTime() + "'";
			sql += " where id=" + kejian.getId();
			db.executeUpdate(sql);
			result = "�μ���Ϣ���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�μ���Ϣ����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
