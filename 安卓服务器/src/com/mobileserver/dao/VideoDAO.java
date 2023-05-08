package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.Video;
import com.mobileserver.util.DB;

public class VideoDAO {

	public List<Video> QueryVideo(String title,int chapterId) {
		List<Video> videoList = new ArrayList<Video>();
		DB db = new DB();
		String sql = "select * from Video where 1=1";
		if (!title.equals(""))
			sql += " and title like '%" + title + "%'";
		if (chapterId != 0)
			sql += " and chapterId=" + chapterId;
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				Video video = new Video();
				video.setId(rs.getInt("id"));
				video.setTitle(rs.getString("title"));
				video.setChapterId(rs.getInt("chapterId"));
				video.setPath(rs.getString("path"));
				video.setAddTime(rs.getString("addTime"));
				videoList.add(video);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return videoList;
	}
	/* ������Ƶ��Ϣ���󣬽�����Ƶ��Ϣ�����ҵ�� */
	public String AddVideo(Video video) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в�������Ƶ��Ϣ */
			String sqlString = "insert into Video(title,chapterId,path,addTime) values (";
			sqlString += "'" + video.getTitle() + "',";
			sqlString += video.getChapterId() + ",";
			sqlString += "'" + video.getPath() + "',";
			sqlString += "'" + video.getAddTime() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "��Ƶ��Ϣ��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��Ƶ��Ϣ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ����Ƶ��Ϣ */
	public String DeleteVideo(int id) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Video where id=" + id;
			db.executeUpdate(sqlString);
			result = "��Ƶ��Ϣɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��Ƶ��Ϣɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݼ�¼��Ż�ȡ����Ƶ��Ϣ */
	public Video GetVideo(int id) {
		Video video = null;
		DB db = new DB();
		String sql = "select * from Video where id=" + id;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				video = new Video();
				video.setId(rs.getInt("id"));
				video.setTitle(rs.getString("title"));
				video.setChapterId(rs.getInt("chapterId"));
				video.setPath(rs.getString("path"));
				video.setAddTime(rs.getString("addTime"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return video;
	}
	/* ������Ƶ��Ϣ */
	public String UpdateVideo(Video video) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update Video set ";
			sql += "title='" + video.getTitle() + "',";
			sql += "chapterId=" + video.getChapterId() + ",";
			sql += "path='" + video.getPath() + "',";
			sql += "addTime='" + video.getAddTime() + "'";
			sql += " where id=" + video.getId();
			db.executeUpdate(sql);
			result = "��Ƶ��Ϣ���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��Ƶ��Ϣ����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
