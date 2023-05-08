package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.Chapter;
import com.mobileserver.util.DB;

public class ChapterDAO {

	public List<Chapter> QueryChapter() {
		List<Chapter> chapterList = new ArrayList<Chapter>();
		DB db = new DB();
		String sql = "select * from Chapter where 1=1";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				Chapter chapter = new Chapter();
				chapter.setId(rs.getInt("id"));
				chapter.setTitle(rs.getString("title"));
				chapter.setAddTime(rs.getString("addTime"));
				chapterList.add(chapter);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return chapterList;
	}
	/* ��������Ϣ���󣬽�������Ϣ�����ҵ�� */
	public String AddChapter(Chapter chapter) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в���������Ϣ */
			String sqlString = "insert into Chapter(title,addTime) values (";
			sqlString += "'" + chapter.getTitle() + "',";
			sqlString += "'" + chapter.getAddTime() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "����Ϣ��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "����Ϣ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ������Ϣ */
	public String DeleteChapter(int id) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Chapter where id=" + id;
			db.executeUpdate(sqlString);
			result = "����Ϣɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "����Ϣɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݼ�¼��Ż�ȡ������Ϣ */
	public Chapter GetChapter(int id) {
		Chapter chapter = null;
		DB db = new DB();
		String sql = "select * from Chapter where id=" + id;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				chapter = new Chapter();
				chapter.setId(rs.getInt("id"));
				chapter.setTitle(rs.getString("title"));
				chapter.setAddTime(rs.getString("addTime"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return chapter;
	}
	/* ��������Ϣ */
	public String UpdateChapter(Chapter chapter) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update Chapter set ";
			sql += "title='" + chapter.getTitle() + "',";
			sql += "addTime='" + chapter.getAddTime() + "'";
			sql += " where id=" + chapter.getId();
			db.executeUpdate(sql);
			result = "����Ϣ���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "����Ϣ����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
