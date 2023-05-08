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
	/* 传入视频信息对象，进行视频信息的添加业务 */
	public String AddVideo(Video video) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新视频信息 */
			String sqlString = "insert into Video(title,chapterId,path,addTime) values (";
			sqlString += "'" + video.getTitle() + "',";
			sqlString += video.getChapterId() + ",";
			sqlString += "'" + video.getPath() + "',";
			sqlString += "'" + video.getAddTime() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "视频信息添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "视频信息添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除视频信息 */
	public String DeleteVideo(int id) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Video where id=" + id;
			db.executeUpdate(sqlString);
			result = "视频信息删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "视频信息删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据记录编号获取到视频信息 */
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
	/* 更新视频信息 */
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
			result = "视频信息更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "视频信息更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
