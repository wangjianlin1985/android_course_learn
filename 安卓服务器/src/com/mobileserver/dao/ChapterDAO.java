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
	/* 传入章信息对象，进行章信息的添加业务 */
	public String AddChapter(Chapter chapter) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新章信息 */
			String sqlString = "insert into Chapter(title,addTime) values (";
			sqlString += "'" + chapter.getTitle() + "',";
			sqlString += "'" + chapter.getAddTime() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "章信息添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "章信息添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除章信息 */
	public String DeleteChapter(int id) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Chapter where id=" + id;
			db.executeUpdate(sqlString);
			result = "章信息删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "章信息删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据记录编号获取到章信息 */
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
	/* 更新章信息 */
	public String UpdateChapter(Chapter chapter) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update Chapter set ";
			sql += "title='" + chapter.getTitle() + "',";
			sql += "addTime='" + chapter.getAddTime() + "'";
			sql += " where id=" + chapter.getId();
			db.executeUpdate(sql);
			result = "章信息更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "章信息更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
